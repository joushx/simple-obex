package com.johannes_mittendorfer.simpleobex.header;

import com.johannes_mittendorfer.simpleobex.header.templates.UnicodeHeader;

import java.util.Arrays;

public class DescriptionHeader extends UnicodeHeader {

    public DescriptionHeader(String description){
        super((byte) 0x05, description);
    }

    public static DescriptionHeader parse(byte[] data){

        if(data.length < 3){
            throw new IllegalArgumentException("Data length must be >= 3");
        }

        String desc = new String(Arrays.copyOfRange(data, 3, data.length - 1));
        return new DescriptionHeader(desc);
    }

    @Override
    public String toString() {
        return "Description: " + value;
    }
}
