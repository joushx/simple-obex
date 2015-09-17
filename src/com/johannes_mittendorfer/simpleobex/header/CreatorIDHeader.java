package com.johannes_mittendorfer.simpleobex.header;

import com.johannes_mittendorfer.simpleobex.header.templates.ByteHeader;

public class CreatorIDHeader extends ByteHeader {

    public CreatorIDHeader(byte[] id){
        super((byte) 0xCF, id);
    }

    @Override
    public int getLength() {
        return 5;
    }

    @Override
    public String toString() {
        return null;
    }
}
