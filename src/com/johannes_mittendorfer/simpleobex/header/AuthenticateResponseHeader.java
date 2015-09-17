package com.johannes_mittendorfer.simpleobex.header;

import com.johannes_mittendorfer.simpleobex.header.templates.ByteHeader;

public class AuthenticateResponseHeader extends ByteHeader {

    public AuthenticateResponseHeader(byte[] data){
        super((byte) 0x4E, data);
    }

    @Override
    public String toString() {
        return null;
    }
}
