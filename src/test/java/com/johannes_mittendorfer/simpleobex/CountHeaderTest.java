package com.johannes_mittendorfer.simpleobex;

import com.johannes_mittendorfer.simpleobex.header.CountHeader;
import org.junit.Assert;
import org.junit.Before;

public class CountHeaderTest extends HeaderTestAbstract {

    private CountHeader h;

    @Before
    public void init(){
        h = new CountHeader(5);
    }

    @Override
    public void testGenerate() {
        byte[] expected = new byte[]{(byte) 0xc0, 0x00, 0x00, 0x00, 0x5};
        byte[] actual = h.getBytes();

        Assert.assertArrayEquals(expected, actual);
    }

    @Override
    public void testGetLength(){
        Assert.assertEquals(5, h.getLength());
    }

    @Override
    public void testToString(){
        Assert.assertEquals("Count: 5", h.toString());
    }

    @Override
    public void testParse() {
        byte[] data = new byte[]{(byte) 0xc0, 0x00, 0x00, 0x00, 0x5};
        CountHeader h = CountHeader.parse(data);

        Assert.assertEquals(5, (int)h.getValue());
    }
}