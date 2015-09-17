package com.johannes_mittendorfer.simpleobex.header;

import com.johannes_mittendorfer.simpleobex.header.templates.ByteHeader;

import java.util.Arrays;

public class EndOfBodyHeader extends ByteHeader {

    public EndOfBodyHeader(byte[] data){
        super((byte) 0x49, data);
    }

    public static EndOfBodyHeader parse(byte[] data){
        byte[] payload = Arrays.copyOfRange(data, 3, data.length);
        return new EndOfBodyHeader(payload);
    }

    @Override
    public String toString() {
        return "End of body: " + bytesToHex(value);
    }
}
