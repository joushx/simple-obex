package com.johannes_mittendorfer.simpleobex.header.templates;

public abstract class ByteHeader extends OBEXHeader<byte[]> {

    public ByteHeader(byte id, byte[] data){
        super(id, data);

        int length = data.length + 3;

        bytes.add((byte) (length >> 8));
        bytes.add((byte) length);

        for(byte b: data){
            bytes.add(b);
        }
    }

    @Override
    public int getLength() {
        return 1 + 2 + value.length;
    }

    protected String bytesToHex(byte[] bytes) {
        char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
