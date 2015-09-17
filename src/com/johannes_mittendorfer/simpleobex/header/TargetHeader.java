package com.johannes_mittendorfer.simpleobex.header;

import com.johannes_mittendorfer.simpleobex.header.templates.ByteHeader;

public class TargetHeader extends ByteHeader {

    public TargetHeader(byte[] target){
        super((byte) 0x46, target);
    }

    @Override
    public String toString() {
        return "Target: " + bytesToHex(value);
    }
}
