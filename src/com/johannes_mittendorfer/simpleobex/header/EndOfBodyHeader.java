package com.johannes_mittendorfer.simpleobex.header;

import com.johannes_mittendorfer.simpleobex.header.templates.ByteHeader;

public class EndOfBodyHeader extends ByteHeader {

    public EndOfBodyHeader(byte[] data){
        super((byte) 0x49, data);
    }

    @Override
    public String toString() {
        return "Body: " + bytesToHex(value);
    }
}
