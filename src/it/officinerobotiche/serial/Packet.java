/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.officinerobotiche.serial;

/**
 * Packet for encode and decode packet received from uNAV 
 * @author Raffaello Bonghi
 */
public class Packet {

    /**
     * Type of packet, syncronous or asyncronous
     */
    private boolean sync;
    /**
     * Data bytes to decode
     */
    private byte[] DataStructure;
    /**
     * Temporaney dimension of packet
     */
    private int length = 0;
    /**
     * Checksum packet
     */
    private byte checksumR = 0;

    /**
     * Initialize packet with type of packet
     * @param sync 
     */
    public Packet(boolean sync) {
        this.sync = sync;
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
        return DataStructure.length;
    }

    /**
     * Initialize dimension data array
     * @param lengthPkg length of packet
     */
    public void setLengthPkg(int lengthPkg) {
        DataStructure = new byte[lengthPkg];
    }

    /**
     * Add byte on data array
     * @param bytePkg byte to add
     * @return state of data packet saved, if true the message is totally copied
     */
    public boolean addByte(byte bytePkg) {
        if (length < DataStructure.length) {
            checksumR += bytePkg;                   //Sum all bytes for checksum
            DataStructure[length++] = bytePkg;
        } else if (length == DataStructure.length) {
            // verify packet and compare evaluate checksum with checksum received
            return (checksumR == bytePkg);
        }
        return false;
    }

    /**
     * Array with all data byte data.
     * @return Data array structure
     */
    public byte[] getDataStructure() {
        return DataStructure;
    }

    @Override
    public String toString() {
        return "Packet{" + 
                "sync=" + sync + ", " + 
                "DataStructure=" + DataStructure + ", " + 
                "length=" + DataStructure.length + '}';
    }
    
    
}
