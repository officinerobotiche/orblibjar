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
public enum Service implements Jmessage {

    SERVICE(0);

    private final static int BUFF_SERVICE = 20;
    private List listeners = new ArrayList();

    public enum NameService {

        RESET('*'),
        DATE_CODE('d'),
        NAME_BOARD('n'),
        VERSION('v'),
        AUTHOR('a');

        private final char name;
        private static final Map<Character, NameService> lookup = new HashMap<>();

        private NameService(char name) {
            this.name = name;
        }

        public char getName() {
            return name;
        }

        public byte getByte() {
            return (byte) name;
        }

        static {
            for (NameService s : EnumSet.allOf(NameService.class)) {
                lookup.put(s.getName(), s);
            }
        }

        public static NameService get(byte Value) {
            //the reverse lookup by simply getting 
            //the value from the lookup HsahMap. 
            return lookup.get(Value);
        }
    };

    private Information information = null;
    public final char type = TypeMessage.DEFAULT.getName();
    private final byte number;
    private byte[] data = null;
    /**/
    private String name;

    private Service(int number) {
        this.number = (byte) number;
    }

    @Override
    public Service set(byte[] data) {
        this.data = data;
        this.information = Information.DATA;
        return this;
    }

    @Override
    public Service set(Information info) {
        this.information = info;
        return this;
    }

    public Service set(NameService name) {
        this.information = Information.REQUEST;
        this.data = new byte[BUFF_SERVICE + 1];
        this.data[0] = name.getByte();
        return this;
    }

    private String decodeService(NameService name_service, String board) {
        String name = "";
        switch (name_service) {
            case AUTHOR:
                name += NameService.AUTHOR.toString();
                break;
            case DATE_CODE:
                name += NameService.DATE_CODE.toString();
                break;
            case NAME_BOARD:
                name += NameService.NAME_BOARD.toString();
                break;
            case VERSION:
                name += NameService.VERSION.toString();
                break;
        }
        return name + ": " + board;
    }

    @Override
    public byte getNumber() {
        return number;
    }

    @Override
    public Information getInformation() {
        return information;
    }

    @Override
    public Service get(byte Value) {
        return SERVICE;
    }

    @Override
    public void addMessageListener(MessageClassListener listener) {
        this.listeners.add(listener);
    }

    @Override
    public void removeMessageListener(MessageClassListener listener) {
        this.listeners.remove(listener);
    }

}