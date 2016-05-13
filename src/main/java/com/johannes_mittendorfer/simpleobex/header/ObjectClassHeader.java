package com.johannes_mittendorfer.simpleobex.header;

import com.johannes_mittendorfer.simpleobex.header.templates.ByteHeader;

public class ObjectClassHeader extends ByteHeader {

    public ObjectClassHeader(byte[] data){
        super((byte) 0x51, data);
    }

    @Override
    public int getLength() {
        return 0;
    }

    @Override
    public String toString() {
        return null;
    }
}
