package com.johannes_mittendorfer.simpleobex.header;

import com.johannes_mittendorfer.simpleobex.header.templates.StringHeader;

public class HTTPHeader extends StringHeader {

    public HTTPHeader(String text){
        super((byte) 0x47, text);
    }

    @Override
    public String toString() {
        return null;
    }
}
