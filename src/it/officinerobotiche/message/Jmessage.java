/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.officinerobotiche.message;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author Master
 */
public abstract class Jmessage {

    protected DateFormat df = new SimpleDateFormat("HH:mm:ss.SSS");

    public abstract int getAck();

    public abstract String getName();

    public abstract long getTimestamp();

    public abstract boolean isCmdAT();

    public abstract byte[] getData();
}
