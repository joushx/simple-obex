package com.johannes_mittendorfer.simpleobex.header.templates;

import java.util.ArrayList;
import java.util.List;

public abstract class OBEXHeader<T> {
    protected final ArrayList<Byte> bytes;
    protected final T value;

    protected OBEXHeader(byte id, T value){
        bytes = new ArrayList<Byte>();
        bytes.add(id);
        this.value = value;

        if(value == null){
            throw new IllegalArgumentException("value cannot be null");
        }
    }

    /**
     * Returns the value of the header (the id, the length,...)
     * @return The value
     */
    public T getValue(){
        return value;
    }

    /**
     * Returns length of header including id + length
     * @return Length of header
     */
    public abstract int getLength();

    /**
     * Returns the bytes of the final package
     * @return Byte array of package
     */
    public byte[] getBytes(){
        return toByteArray(bytes);
    }

    public abstract String toString();

    private byte[] toByteArray(List<Byte> in) {
        final int n = in.size();
        byte ret[] = new byte[n];
        for (int i = 0; i < n; i++) {
            ret[i] = in.get(i);
        }
        return ret;
    }
}
