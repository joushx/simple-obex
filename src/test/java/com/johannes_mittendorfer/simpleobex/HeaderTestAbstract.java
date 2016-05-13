package com.johannes_mittendorfer.simpleobex;

import org.junit.Test;

public abstract class HeaderTestAbstract {

    @Test
    public abstract void testGenerate() throws Exception;

    @Test
    public abstract void testGetLength() throws Exception;

    @Test
    public abstract void testToString() throws Exception;

    @Test
    public abstract void testParse() throws Exception;
}
