package com.johannes_mittendorfer.simpleobex.header;

import com.johannes_mittendorfer.simpleobex.header.templates.OBEXHeader;

public class CountHeader extends OBEXHeader<Integer> {

    public CountHeader(int count){
        super((byte) 0xc0, count);

        bytes.add((byte) (count << 3*8));
        bytes.add((byte) (count << 2*8));
        bytes.add((byte) (count << 8));
        bytes.add((byte) count);
    }

    public static CountHeader parse(byte[] data){
        int count = data[1] << 3*8 | data[2] << 2*8 | data[3] << 8 | data[4];
        return new CountHeader(count);
    }

    @Override
    public int getLength() {
        return 5;
    }

    @Override
    public String toString() {
        return "Count: " + value;
    }
}
