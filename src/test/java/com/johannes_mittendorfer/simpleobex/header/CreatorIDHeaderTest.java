package com.johannes_mittendorfer.simpleobex.header;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CreatorIDHeaderTest {

    CreatorIDHeader h;

    @Before
    public void init(){
        h = new CreatorIDHeader(new byte[]{0x1, 0x2,  0x3, 0x4, 0x5});
    }

    @Test
    public void testGetLength() throws Exception {
        assertEquals(8, h.getLength());
    }

    @Test
    public void testToString() throws Exception {
        assertEquals("Creator ID: 0102030405", h.toString());
    }
}