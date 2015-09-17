package com.johannes_mittendorfer.simpleobex.header;

import com.johannes_mittendorfer.simpleobex.header.templates.UnicodeHeader;

public class DescriptionHeader extends UnicodeHeader {

    public DescriptionHeader(String description){
        super((byte) 0x05, description);
    }

    @Override
    public String toString() {
        return null;
    }
}
