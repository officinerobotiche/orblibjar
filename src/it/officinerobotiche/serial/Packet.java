/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.officinerobotiche.serial;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Packet for encode and decode packet received from uNAV 
 * @author Raffaello Bonghi
 */
public class Packet {
    /**
     * Time to receive or create Packet
     */
    private final long timestamp;
    /**
     * Dateformat to time
     */
    protected DateFormat df = new SimpleDateFormat("HH:mm:ss.SSS");
    /**
     * Type of packet, syncronous or asyncronous
     */
    private boolean sync;
    /**
     * Data bytes to decode
     */
    private ArrayList<Byte> dataStructure;
    /**
     * Dimension of packet
     */
    private int length = 0;
    /**
     * Checksum packet
     */
    private byte checksumR = 0;
    
    /**
     * Initialize packet with type of packet
     * @param sync type of packet: syncronus or asyncornus packet
     * @param currentTimeMillis time to create packet
     */
    public Packet(boolean sync, long currentTimeMillis) {
        this.sync = sync;
        this.timestamp = currentTimeMillis;
        dataStructure = new ArrayList<Byte>();
    }
    
    /**
     * Initialize packet with type of packet and set current time.
     * @param sync type of packet: syncronus or asyncornus packet
     */
    public Packet(boolean sync) {
        this.sync = sync;
        this.timestamp = System.currentTimeMillis();
        dataStructure = new ArrayList<Byte>();
    }
    
    /**
     * Data creation packet
     * @return time to creation packet 
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Read type of message
     * @return if true packet is syncronous, else asyncronus
     */
    public boolean isSync() {
        return sync;
    }

    /**
     * Set type of message
     * @param sync type of message
     */
    public void setSync(boolean sync) {
        this.sync = sync;
    }

    /**
     * Dimension of packet
     * @return Dimension of packet
     */
    public int getLengthPkg() {
        return length;
    }

    /**
     * Initialize dimension data array
     * @param lengthPkg length of packet
     */
    public void setLengthPkg(int lengthPkg) {
        this.length = lengthPkg;
    }

    /**
     * Add byte on data array
     * @param bytePkg byte to add
     * @return state of data packet saved, if true the message is totally copied
     */
    public boolean addByte(byte bytePkg) {
        if (dataStructure.size() < length) {
            //Sum all bytes for checksum
            checksumR += bytePkg;
            dataStructure.add(bytePkg);
        } else if (dataStructure.size() == length) {
            // verify packet and compare evaluate checksum with checksum received
            return (checksumR == bytePkg);
        }
        return false;
    }
    
    public void addMessage(ArrayList<Byte> message) {
        dataStructure.addAll(message);
        length = dataStructure.size();
    }

    /**
     * Array with all data byte data.
     * @return Data array structure
     */
    public byte[] getDataStructure() {
        byte[] data = new byte[dataStructure.size()];
        int i = 0;
        for(Byte i_byte: dataStructure) {
            data[i++] = i_byte;
        }
        return data;
    }

    @Override
    public String toString() {
        return "Packet{" + 
                "Time Stamp=" + df.format(new Date(timestamp)) + ", " + 
                "sync=" + sync + ", " + 
                "DataStructure=" + dataStructure.toString() + ", " + 
                "length=" + dataStructure.size() + '}';
    }
    
}
