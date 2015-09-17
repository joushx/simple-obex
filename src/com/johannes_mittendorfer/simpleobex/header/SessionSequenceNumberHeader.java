package com.johannes_mittendorfer.simpleobex.header;

import com.johannes_mittendorfer.simpleobex.header.templates.OBEXHeader;

public class SessionSequenceNumberHeader extends OBEXHeader<Integer> {

    public SessionSequenceNumberHeader(int number){
        super((byte) 0x93, number);

        bytes.add((byte) number);
    }

    @Override
    public int getLength() {
        return 2;
    }

    @Override
    public String toString() {
        return "Seq. number: " + value;
    }
}
