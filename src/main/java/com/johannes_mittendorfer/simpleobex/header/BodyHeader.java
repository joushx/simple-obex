package com.johannes_mittendorfer.simpleobex.header;

import com.johannes_mittendorfer.simpleobex.header.templates.ByteHeader;

public class BodyHeader extends ByteHeader {

    public BodyHeader(byte[] data){
        super((byte) 0x48, data);
    }

    @Override
    public String toString() {
        return "Body: " + bytesToHex(value);
    }
}
