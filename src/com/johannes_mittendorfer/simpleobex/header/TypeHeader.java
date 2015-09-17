package com.johannes_mittendorfer.simpleobex.header;

import com.johannes_mittendorfer.simpleobex.header.templates.StringHeader;

import java.util.Arrays;

public class TypeHeader extends StringHeader {

    public TypeHeader(String mimeType){
        super((byte) 0x42, mimeType);
    }

    public static TypeHeader parse(byte[] data){

        if(data.length < 3){
            throw new IllegalArgumentException("Data length must be >= 3");
        }

        String type = new String(Arrays.copyOfRange(data, 3, data.length-1));
        return new TypeHeader(type);
    }

    @Override
    public String toString() {
        return "Type: " + value;
    }
}
