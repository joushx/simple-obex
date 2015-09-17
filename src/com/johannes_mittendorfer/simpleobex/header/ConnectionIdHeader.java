package com.johannes_mittendorfer.simpleobex.header;

import com.johannes_mittendorfer.simpleobex.header.templates.OBEXHeader;

public class ConnectionIdHeader extends OBEXHeader<Integer> {

    public ConnectionIdHeader(int id){
        super((byte) 0xcb, id);

        bytes.add((byte) (id >> 3*8));
        bytes.add((byte) (id >> 2*8));
        bytes.add((byte) (id >> 8));
        bytes.add((byte) id);
    }

    public static ConnectionIdHeader parse(byte[] data){

        if(data.length != 5){
            throw new IllegalArgumentException("data length must be 4");
        }

        return new ConnectionIdHeader(data[1] << 3*8 | data[2] << 2*8 | data[3] << 8 | data[4]);
    }

    public long getId() {
        return value;
    }

    public String toString(){
        return "Connection-Id: " + value;
    }

    @Override
    public int getLength() {
        return 5;
    }
}
