package com.johannes_mittendorfer.simpleobex.header;

import com.johannes_mittendorfer.simpleobex.header.templates.ByteHeader;

import java.util.Arrays;

public class TargetHeader extends ByteHeader {

    public TargetHeader(byte[] target){
        super((byte) 0x46, target);
    }

    public static TargetHeader parse(byte[] data){
        byte[] target = Arrays.copyOfRange(data, 3, data.length);
        return new TargetHeader(target);
    }

    @Override
    public String toString() {
        return "Target: " + bytesToHex(value);
    }
}
