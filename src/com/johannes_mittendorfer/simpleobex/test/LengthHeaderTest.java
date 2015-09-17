package com.johannes_mittendorfer.simpleobex.test;

import com.johannes_mittendorfer.simpleobex.header.LengthHeader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LengthHeaderTest extends HeaderTest {

    private LengthHeader h;

    @Before
    public void init(){
        h = new LengthHeader(514506);
    }

    @Test
    public void testToString() throws Exception {
        Assert.assertEquals("Length: 514506", h.toString());
    }

    @Override
    public void testParse() {
        Assert.fail();
    }

    @Test
    public void testGenerate() throws Exception {
        byte[] expected = TestUtil.hexStringToByteArray("c30007d9ca");
        byte[] actual = h.getBytes();

        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void testGetLength(){
        Assert.assertEquals(5, h.getLength());
    }
}