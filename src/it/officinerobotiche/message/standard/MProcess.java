/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.officinerobotiche.message.standard;

import it.officinerobotiche.message.Jmessage;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Raffaello
 */
public enum MProcess implements Jmessage {

    TIME(1),
    PRIORITY(2),
    FREQUENCY(3);

    private List listeners = new ArrayList();
    private static final Map<Byte, MProcess> lookup = new HashMap<>();
    private Information information = null;
    public final char type = TypeMessage.DEFAULT.getName();
    private final byte number;
    private byte[] data = null;
    
    private MProcess(int number) {
        this.number = (byte) number;
    }
    
    @Override
    public synchronized MProcess set(byte[] data) {
        this.data = data;
        this.information = Information.DATA;
        return this;
    }
    
    @Override
    public MProcess set(Information info) {
        this.information = info;
        return this;
    }
    
    public MProcess set() {
        this.information = Information.REQUEST;
        return this;
    }

    @Override
    public byte getNumber() {
        return number;
    }

    @Override
    public Information getInformation() {
        return information;
    }

    static {
        for (MProcess s : EnumSet.allOf(MProcess.class)) {
            lookup.put(s.getNumber(), s);
        }
    }

    @Override
    public MProcess get(byte Value) {
        //the reverse lookup by simply getting 
        //the value from the lookup HsahMap. 
        return lookup.get(Value);
    }
    
    @Override
    public synchronized void addMessageListener(MessageClassListener listener) {
        this.listeners.add(listener);
    }

    @Override
    public synchronized void removeMessageListener(MessageClassListener listener) {
        this.listeners.remove(listener);
    }

}
