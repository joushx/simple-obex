package com.johannes_mittendorfer.simpleobex.header.templates;

public abstract class StringHeader extends OBEXHeader<String> {

    protected StringHeader(byte id, String mimeType){
        super(id, mimeType);

        int length = mimeType.getBytes().length + 3 + 1;
        bytes.add((byte) (length >> 8));
        bytes.add((byte) length);
        for(byte b: mimeType.getBytes()){
            bytes.add(b);
        }
        bytes.add((byte) 0x00);
    }

    @Override
    public int getLength() {
        return 1 + 2 + value.length()+1;
    }
}
