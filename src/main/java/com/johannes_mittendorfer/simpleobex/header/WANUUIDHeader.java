package com.johannes_mittendorfer.simpleobex.header;

import com.johannes_mittendorfer.simpleobex.header.templates.ByteHeader;

public class WANUUIDHeader extends ByteHeader {

    public WANUUIDHeader(byte[] uuid){
        super((byte) 0x50, uuid);
    }

    @Override
    public int getLength() {
        return 17;
    }

    @Override
    public String toString() {
        return null;
    }
}
