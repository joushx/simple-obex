package com.johannes_mittendorfer.simpleobex.header;

import com.johannes_mittendorfer.simpleobex.header.templates.ByteHeader;

public class AuthenticateChallengeHeader extends ByteHeader {

    public AuthenticateChallengeHeader(byte[] data){
        super((byte) 0x4d, data);
    }

    @Override
    public String toString() {
        return null;
    }
}
