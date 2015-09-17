package com.johannes_mittendorfer.simpleobex.header;

import com.johannes_mittendorfer.simpleobex.header.templates.ByteHeader;

public class WhoHeader extends ByteHeader {

    public WhoHeader(byte[] target){
        super((byte) 0x4a, target);
    }

    @Override
    public String toString() {
        return "Who: " + bytesToHex(value);
    }
}
