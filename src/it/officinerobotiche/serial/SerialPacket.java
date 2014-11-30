/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.officinerobotiche.serial;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.EventListenerList;

/**
 *
 * @author Raffaello
 */
public class SerialPacket implements SerialPortEventListener {

    /**
     * Serial port object to communicate with board
     */
    private SerialPort serialPort;
    /**
     * Header for syncronus packet
     */
    private static final byte HEADER_SYNC = (byte) '#';
    /**
     * Header for asyncronus packet
     */
    private static final byte HEADER_ASYNC = (byte) '@';
    /**
     * Dimension of header
     */
    private final static int LNG_HEADER = 2;
    /**
     * Milliseconds to block while waiting for port open
     */
    private static final int TIME_OUT = 2000;
    /**
     * Default bits per second for COM port.
     */
    private static final int DATA_RATE = 115200;
    /**
     * Stream to receive packet in serial
     */
    private InputStream in;
    /**
     * Stream to send packet in serial
     */
    private OutputStream out;
    /**
     * Docoder packet status, use for function readByte. We have tree state: 0.
     * Ready to decode header; 1. Save length; 2. Save data packet and checksum.
     */
    private int statusDec = 0;
    /**
     * Packet to decode
     */
    private Packet packet;

    private Barrier block = new Barrier();

    protected EventListenerList listenerList = new EventListenerList();

    public SerialPacket(String portName) {
        try {
            CommPortIdentifier portId = CommPortIdentifier.getPortIdentifier(portName);
            // open serial port, and use class name for the appName.
            serialPort = (SerialPort) portId.open(this.getClass().getName(),
                    TIME_OUT);

            // set port parameters
            serialPort.setSerialPortParams(DATA_RATE,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);

            // open the streams
            in = serialPort.getInputStream();
            out = serialPort.getOutputStream();

            // add event listeners
            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    /**
     * Find all serial port avaible.
     *
     * @return A HashSet containing the CommPortIdentifier for all serial ports
     * that are not currently being used.
     */
    public static HashSet<CommPortIdentifier> availableSerialPorts() {
        HashSet<CommPortIdentifier> h = new HashSet<>();
        Enumeration thePorts = CommPortIdentifier.getPortIdentifiers();
        while (thePorts.hasMoreElements()) {
            CommPortIdentifier com = (CommPortIdentifier) thePorts.nextElement();
            switch (com.getPortType()) {
                case CommPortIdentifier.PORT_SERIAL:
                    try {
                        CommPort thePort = com.open("CommUtil", 50);
                        thePort.close();
                        h.add(com);
                        System.out.println("AVAIBLE Port on: " + com.getName());
                    } catch (PortInUseException e) {
                        System.out.println("Port, " + com.getName() + ", is in use.");
                    } catch (Exception e) {
                        System.err.println("Failed to open port " + com.getName());
                    }
            }
        }
        return h;
    }

    /**
     * Event to decode byte received from serial. We have two fases for decode
     * packet. The first one, receive all byte and create by function readByte a
     * new Packet. Finally if data is correctly decoded, start a function
     * decodePkg, for parsing all messages in Packet.
     *
     * @param oEvent type of event received from serial
     */
    @Override
    public synchronized void serialEvent(SerialPortEvent oEvent) {
        if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            try {
                int data;
                while ((data = in.read()) > -1) {
                    //Buffering Packet
                    if (readByte((byte) data)) {
                        //Reset Decoder
                        statusDec = 0;
                        //Decode Messages
                        if (packet.isSync()) {
                            block.release();
                        } else {
                            firePacketEvent(new PacketEvent(packet));
                        }
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(SerialPacket.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * This should be called when you stop using the port. This will prevent
     * port locking on platforms like Linux.
     */
    public synchronized void close() {
        if (serialPort != null) {
            serialPort.removeEventListener();
            serialPort.close();
        }
    }

    /**
     * Decode a single byte and append in packet for decoding.
     *
     * We have tree state for decode packet: 1. If first byte is an header,
     * create a new packet and save type of packet; 2. Save the length of
     * message; 3. Save data in Packet and finally verify with checksum
     * received.
     *
     * @param bytePkg byte to decode
     * @return status of decoding
     */
    private boolean readByte(byte bytePkg) {
        switch (statusDec) {
            //Header of Packet
            case 0:
                if ((bytePkg == HEADER_SYNC) || (bytePkg == HEADER_ASYNC)) {
                    packet = new Packet(bytePkg == HEADER_SYNC);
                    statusDec++;
                } else {
                    // Start error
                    throw new UnsupportedOperationException("Packet error");
                }
                break;

            //Length of packet
            case 1:
                packet.setLengthPkg((int) bytePkg);
                statusDec++;
                break;

            //Add data byte and return true if packet is 
            case 2:
                return packet.addByte(bytePkg);

            default:
                //Status error
                throw new UnsupportedOperationException("Packet error");
        }
        return false;
    }

    public void addPacketEventListener(PacketListener listener) {
        listenerList.add(PacketListener.class, listener);
    }

    public void removePacketEventListener(PacketListener listener) {
        listenerList.remove(PacketListener.class, listener);
    }

    private void firePacketEvent(PacketEvent evt) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i + 2) {
            if (listeners[i] == PacketListener.class) {
                ((PacketListener) listeners[i + 1]).asyncPacketEvent(evt);
            }
        }
    }

    /**
     * Send packet on serial port.
     *
     * @param packet Packet to send
     * @return
     */
    protected Packet sendPacket(Packet packet) throws InterruptedException {
        if (packet.isSync()) {
            try {
                out.write(convertPacket(packet));
                block.block();
                return this.packet;
            } catch (IOException ex) {
                Logger.getLogger(SerialPacket.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Build a packet to send.
     *
     * @param data
     * @return
     */
    private static byte[] convertPacket(Packet packet) {
        //Dimension of packet
        byte[] dataSend = new byte[LNG_HEADER + packet.getLengthPkg() + 1];
        //Add Header
        dataSend[0] = packet.isSync() ? HEADER_SYNC : HEADER_ASYNC;
        //Add length of packet
        dataSend[1] = (byte) packet.getLengthPkg();
        //Copy all data bytes in array to send
        System.arraycopy(packet.getDataStructure(), 0, dataSend, LNG_HEADER, packet.getLengthPkg());
        //Evaluate checkSum
        dataSend[LNG_HEADER + packet.getLengthPkg()] = (byte) checkSum(packet.getDataStructure());
        return dataSend;
    }

    /**
     * Evaluate Checksum to send message.
     *
     * @param datapkg Array with all byte to evalute checksum
     * @return value of checksum
     */
    private static byte checkSum(byte[] datapkg) {
        byte temp = 0;
        for (Byte datapkg_i : datapkg) {
            // Sum all byte
            temp += datapkg_i;
        }
        return temp;
    }
}
