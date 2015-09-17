package com.johannes_mittendorfer.simpleobex.header;

import com.johannes_mittendorfer.simpleobex.header.templates.ByteHeader;

public class ApplicationParametersHeader extends ByteHeader {

    public ApplicationParametersHeader(byte[] data){
        super((byte) 0x4c, data);
    }

    @Override
    public String toString() {
        return null;
    }
}
