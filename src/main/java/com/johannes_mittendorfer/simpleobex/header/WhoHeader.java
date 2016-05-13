package com.johannes_mittendorfer.simpleobex.header;

import com.johannes_mittendorfer.simpleobex.header.templates.ByteHeader;

import java.util.Arrays;

public class WhoHeader extends ByteHeader {

    public WhoHeader(byte[] target){
        super((byte) 0x4a, target);
    }

    public static WhoHeader parse(byte[] data){
        byte[] target = Arrays.copyOfRange(data, 3, data.length);
        return new WhoHeader(target);
    }

    @Override
    public String toString() {
        return "Who: " + bytesToHex(value);
    }
}
