package com.johannes_mittendorfer.simpleobex.header;

import com.johannes_mittendorfer.simpleobex.header.templates.ByteHeader;

public class SessionsParametersHeader extends ByteHeader {

    public SessionsParametersHeader(byte[] data){
        super((byte) 0x52, data);
    }

    @Override
    public String toString() {
        return null;
    }
}
