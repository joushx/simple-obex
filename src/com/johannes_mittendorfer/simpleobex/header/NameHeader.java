package com.johannes_mittendorfer.simpleobex.header;

import com.johannes_mittendorfer.simpleobex.header.templates.UnicodeHeader;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class NameHeader extends UnicodeHeader {

    public NameHeader(String name){
        super((byte) 0x01, name);
    }

    public static NameHeader parse(byte[] data) throws UnsupportedEncodingException {
        String name = new String(Arrays.copyOfRange(data, 3, data.length-1-1), "UTF-16");
        return new NameHeader(name);
    }

    @Override
    public String toString() {
        return "Name: " + value;
    }
}
