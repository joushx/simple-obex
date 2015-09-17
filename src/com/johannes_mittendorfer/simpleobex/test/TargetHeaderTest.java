package com.johannes_mittendorfer.simpleobex.test;

import com.johannes_mittendorfer.simpleobex.header.TargetHeader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TargetHeaderTest extends HeaderTest{

    TargetHeader h;

    @Before
    public void init(){
        byte[] target = TestUtil.hexStringToByteArray("796135f0f0c511d809660800200c9a66");
        h = new TargetHeader(target);
    }

    @Override
    public void testGenerate() {
        byte[] expected = TestUtil.hexStringToByteArray("460013796135f0f0c511d809660800200c9a66");
        byte[] actual = h.getBytes();

        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void testGetLength(){
        Assert.fail();
    }

    @Test
    public void testToString(){
        TargetHeader h = new TargetHeader(new byte[]{(byte) 0xFF, (byte) 0xFE, 0x01});
        Assert.assertEquals("Target: FFFE01", h.toString());
    }

    @Override
    public void testParse() {
        Assert.fail();
    }
}