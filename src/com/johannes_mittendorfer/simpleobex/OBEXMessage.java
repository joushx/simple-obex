package com.johannes_mittendorfer.simpleobex;

import com.johannes_mittendorfer.simpleobex.header.ConnectionIdHeader;
import com.johannes_mittendorfer.simpleobex.header.templates.OBEXHeader;
import com.johannes_mittendorfer.simpleobex.header.NameHeader;
import com.johannes_mittendorfer.simpleobex.header.TypeHeader;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OBEXMessage {

    private ArrayList<OBEXHeader> headers = new ArrayList<OBEXHeader>();
    private byte _opcode;
    private boolean _final;
    private byte _version;
    private byte _flags;
    private int _maxLength;
    private boolean connect;

    private int getLength(){
        int length = 1 + 2;

        if(connect){
            length += 1+1+2;
        }

        for(OBEXHeader h: getHeaders()){
            length += h.getLength();
        }

        return length;
    }

    public static OBEXMessage parse(byte[] data){

        OBEXMessage msg = new OBEXMessage((byte) (data[0] & 0x7f), ((data[0] & 0xFF) >> 7) == 1);

        int length = data[1] << 8 | data[2];

        byte[] bytes;
        int h_length;

        int i = 3;
        while(i < length-1){
            byte id = data[i];

            switch (id & 0xFF){
                case 0xcb:
                    bytes = Arrays.copyOfRange(data, i, i+5);
                    i += 5;
                    msg.addHeader(ConnectionIdHeader.parse(bytes));
                    break;
                case 0x01:
                    h_length = data[i+1] << 8 | data[i+2];
                    bytes = Arrays.copyOfRange(data, i, i+h_length);
                    i += h_length;
                    try {
                        msg.addHeader(NameHeader.parse(bytes));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    break;
                case 0x42:
                    h_length = data[i+1] << 8 | data[i+2];
                    bytes = Arrays.copyOfRange(data, i, i+h_length);
                    i += h_length;
                    msg.addHeader(TypeHeader.parse(bytes));
                    break;

            }


        }

        return msg;
    }

    /**
     * Creates a OBEX message
     * @param opcode The opcode of the message (e.g. GET, PUT, ...)
     * @param isFinal If true the packet is marked as final packet
     */
    public OBEXMessage(byte opcode, boolean isFinal){
        this._opcode = opcode;
        this._final = isFinal;
    }

    /**
     * Creates a OBEX message
     * @param opcode The opcode of the message (e.g. GET, PUT, ...)
     * @param isFinal If true the packet is marked as final packet
     */
    public OBEXMessage(Opcode opcode, boolean isFinal) {
        this((byte)opcode.getNumVal(), isFinal);
    }

    /**
     * Creates a OBEX connect message
     * @param version The version of the OBEX protocol (should be 0x10)
     * @param flags The flags of the connect message
     * @param maxLength Maximum length of OBEX packets in this session
     */
    public OBEXMessage(byte version, byte flags, int maxLength){
        this._version = version;
        this._flags = flags;
        this._final = true;
        this._opcode = 0x00;
        this._maxLength = maxLength;
        connect = true;
    }

    public byte[] getBytes(){
        ArrayList<Byte> bytes = new ArrayList<Byte>();

        bytes.add((byte) (_opcode | ((_final ? 1 : 0) << 7)));

        int length = getLength();
        bytes.add((byte) (length << 8));
        bytes.add((byte) length);

        if(connect){
            bytes.add(_version);
            bytes.add(_flags);
            bytes.add((byte) (_maxLength >> 8));
            bytes.add((byte) _maxLength);
        }

        for(OBEXHeader h: getHeaders()){
            for(byte b: h.getBytes()) {
                bytes.add(b);
            }
        }

        return toByteArray(bytes);
    }

    public void addHeader(OBEXHeader header){
        this.headers.add(header);
    }

    public OBEXHeader[] getHeaders(){
        return headers.toArray(new OBEXHeader[headers.size()]);
    }

    public byte getOpcode() {
        return _opcode;
    }

    public boolean isFinal() {
        return _final;
    }

    private byte[] toByteArray(List<Byte> in) {
        final int n = in.size();
        byte ret[] = new byte[n];
        for (int i = 0; i < n; i++) {
            ret[i] = in.get(i);
        }
        return ret;
    }

    public String toString(){
        String result = "";
        result += "Operation: " + _opcode + "\nLength: ";

        if(connect){
            result += "\nFlags: " + _flags + "\nVersion: " + _version + "\nMax. length: " + _maxLength;
        }

        for(OBEXHeader h: getHeaders()){
            result += "\n" + h.toString();
        }

        return result;
    }
}
