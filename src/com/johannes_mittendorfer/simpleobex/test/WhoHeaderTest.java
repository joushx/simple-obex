package com.johannes_mittendorfer.simpleobex.test;

import com.johannes_mittendorfer.simpleobex.header.WhoHeader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WhoHeaderTest extends HeaderTest {

    private WhoHeader h;

    @Before
    public void init(){
        byte[] uuid = TestUtil.hexStringToByteArray("796135f0f0c511d809660800200c9a66");
        h = new WhoHeader(uuid);
    }

    @Override
    public void testGenerate() {
        byte[] expected = TestUtil.hexStringToByteArray("4a0013796135f0f0c511d809660800200c9a66");
        byte[] actual = h.getBytes();

        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void testGetLength(){
        byte[] uuid = TestUtil.hexStringToByteArray("796135f0f0c511d809660800200c9a66");
        int expected = 1+2+uuid.length;

        Assert.assertEquals(expected, h.getLength());
    }

    @Test
    public void testToString(){
        WhoHeader h = new WhoHeader(new byte[]{(byte) 0xFF, (byte) 0xFE, 0x01});
        Assert.assertEquals("Who: FFFE01", h.toString());
    }

    @Override
    public void testParse() {
        byte[] expected = TestUtil.hexStringToByteArray("796135f0f0c511d809660800200c9a66");
        byte[] data = TestUtil.hexStringToByteArray("4a0013796135f0f0c511d809660800200c9a66");

        WhoHeader h = WhoHeader.parse(data);

        Assert.assertArrayEquals(expected, h.getValue());
    }
}