package com.johannes_mittendorfer.simpleobex;

import com.johannes_mittendorfer.simpleobex.header.*;
import com.johannes_mittendorfer.simpleobex.header.templates.OBEXHeader;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class is the base to create custom OBEX messages. It provides methods to add headers and parse messages.
 */
public class OBEXMessage {

    private final ArrayList<OBEXHeader> headers = new ArrayList<OBEXHeader>();
    private final byte _opcode;
    private final boolean _final;
    private byte _version;
    private byte _flags;
    private int _maxLength;
    private boolean connect;

    /**
     * Returns the length of the whole message including all headers in bytes.
     *
     * @return the length in bytes
     */
    private int getLength(){
        int length = 1 + 2;

        // check if it is a connect message
        // and adjust length if so
        if(connect){
            length += 1+1+2;
        }

        // add the length of the headers
        for(OBEXHeader h: getHeaders()){
            length += h.getLength();
        }

        return length;
    }

    /**
     * Parses a OBEX message and returns a {@link com.johannes_mittendorfer.simpleobex.OBEXMessage} object.
     *
     * @param data the bytes of the message
     * @return a {@link com.johannes_mittendorfer.simpleobex.OBEXMessage} object
     */
    public static OBEXMessage parse(byte[] data) throws UnsupportedEncodingException {

        // create variable for position
        int i = 0;

        // lookup opcode in enum
        Opcode operation = null;
        for(Opcode op: Opcode.values()){
            if((data[i] & 0x7f) == op.getNumVal()){
                operation = op;
            }
        }

        // read final flag
        boolean finalFlag = ((data[i] & 0xFF) >> 7) == 1;

        i++;

        int length = 0;

        // create instance
        OBEXMessage msg;
        if(operation == Opcode.CONNECT){

            // read packet length
            length = data[i] << 8 | data[i+1];
            i+=2;

            // read protocol version
            int version = data[i];
            i++;

            // read connection flags
            int flags = data[i];
            i++;

            // read maximum length
            int maxLength = (data[i] & 0xFF) << 8 | data[i+1] & 0xFF;
            i+=2;

            // create message instance
            msg = new OBEXMessage((byte)version, (byte)flags, maxLength);
        }
        else {
            // read message length
            length = data[i] << 8 | data[i+1];
            i+=2;

            // create message instance
            msg = new OBEXMessage(operation, finalFlag);
        }

        byte[] bytes;
        int h_length;

        // parse headers
        while(i < length-1){
            byte id = data[i];

            try {

                h_length = data[i + 1] << 8 | data[i + 2];
                bytes = Arrays.copyOfRange(data, i, i + h_length);

                switch (id & 0xFF) {
                    case 0xcb:
                        bytes = Arrays.copyOfRange(data, i, i + 5);
                        i += 5;
                        msg.addHeader(ConnectionIdHeader.parse(bytes));
                        break;
                    case 0x01:
                        i += h_length;
                        msg.addHeader(NameHeader.parse(bytes));
                        break;
                    case 0x42:
                        i += h_length;
                        msg.addHeader(TypeHeader.parse(bytes));
                        break;
                    case 0x44:
                        i += h_length;
                        msg.addHeader(TimeHeader.parse(bytes));
                        break;
                    case 0xC3:
                        i += h_length;
                        msg.addHeader(LengthHeader.parse(bytes));
                        break;
                    default:
                        throw new IllegalStateException("Header " + Integer.toHexString(id) + " not yet implemented");
                }
            }catch (ParseException e){
                e.printStackTrace();
            }


        }

        return msg;
    }

    /**
     * Creates a OBEX message with the opcode as a {@link com.johannes_mittendorfer.simpleobex.Opcode} value.
     * OBEX connect messages must use {@link #OBEXMessage(byte, byte, int)}.
     *
     * @param opcode The opcode of the message (e.g. GET, PUT, ...)
     * @param isFinal If true the packet is marked as final packet
     */
    public OBEXMessage(Opcode opcode, boolean isFinal) {
        this._opcode = (byte) opcode.getNumVal();
        this._final = isFinal;
    }

    /**
     * Creates a OBEX connect message.
     *
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

    /**
     * Returns the raw message bytes according to the current state.
     *
     * @return bytes of the message
     */
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

    /**
     * Adds a {@link com.johannes_mittendorfer.simpleobex.header.templates.OBEXHeader} to the message
     *
     * @param header the header to add
     */
    public void addHeader(OBEXHeader header){
        this.headers.add(header);
    }

    /**
     * Retrieves the headers of the message
     *
     * @return a array of {@link com.johannes_mittendorfer.simpleobex.header.templates.OBEXHeader} objects
     */
    public OBEXHeader[] getHeaders(){
        return headers.toArray(new OBEXHeader[headers.size()]);
    }

    /**
     * Return the opcode (GET, PUT,...) of the message
     *
     * @return the opcode
     */
    public byte getOpcode() {
        return _opcode;
    }

    /**
     * Checks if the final flag is set
     *
     * @return the final flag
     */
    public boolean isFinal() {
        return _final;
    }

    /**
     * Returns the version of the protocol as defined in a connect message
     * @return The version
     * @throws IllegalStateException if message is no connect message
     */
    public byte getVersion() throws IllegalStateException{

        if(!connect){
            throw new IllegalStateException("Version is only available in connect messages");
        }

        return _version;
    }

    /**
     * Returns the connection flags as defined in a connect message
     * @return The flags
     * @throws IllegalStateException if message is no connect message
     */
    public byte getFlags() throws IllegalStateException {

        if(!connect){
            throw new IllegalStateException("Flags are only available in connect messages");
        }

        return _flags;
    }

    /**
     * Returns the maximum packet length in a connection as defined in a connect message
     * @return The length
     * @throws IllegalStateException if message is no connect message
     */
    public int getMaxLength() throws IllegalStateException {

        if(!connect){
            throw new IllegalStateException("Maximum length is only available in connect messages");
        }

        return _maxLength;
    }

    @Override
    public String toString(){
        String result = "";

        // lookup opcode in enum
        Opcode operation = null;
        for(Opcode op: Opcode.values()){
            if(_opcode == op.getNumVal()){
                operation = op;
            }
        }

        result += "Operation: " + operation.toString() + "\nLength: " + getLength();

        if(connect){
            result += "\nFlags: " + Integer.toString(_flags,2) + "\nVersion: 0x" + Integer.toHexString(_version) + "\nMax. length: " + _maxLength;
        }

        for(OBEXHeader h: getHeaders()){
            result += "\n" + h.toString();
        }

        return result;
    }

    private byte[] toByteArray(List<Byte> in) {
        final int n = in.size();
        byte ret[] = new byte[n];
        for (int i = 0; i < n; i++) {
            ret[i] = in.get(i);
        }
        return ret;
    }
}
