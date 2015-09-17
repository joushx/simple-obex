package com.johannes_mittendorfer.simpleobex.header.templates;

import java.nio.charset.StandardCharsets;

public abstract class UnicodeHeader extends OBEXHeader<String> {

    protected UnicodeHeader(byte id, String text){
        super(id, text);

        byte[] bytes = text.getBytes(StandardCharsets.UTF_16BE);

        int length = bytes.length + 3 +2;

        this.bytes.add((byte) (length >> 8));
        this.bytes.add((byte) length);

        for(byte b: bytes){
            this.bytes.add(b);
        }

        this.bytes.add((byte) 0x00);
        this.bytes.add((byte) 0x00);
    }

    @Override
    public int getLength() {
        return 1 + 2 + value.length()*2+2;
    }
}
