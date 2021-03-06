package com.johannes_mittendorfer.simpleobex.header;

import com.johannes_mittendorfer.simpleobex.HeaderTestAbstract;
import com.johannes_mittendorfer.simpleobex.Util;
import com.johannes_mittendorfer.simpleobex.header.TargetHeader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TargetHeaderTest extends HeaderTestAbstract {

    private TargetHeader h;

    @Before
    public void init(){
        byte[] target = Util.hexStringToByteArray("796135f0f0c511d809660800200c9a66");
        h = new TargetHeader(target);
    }

    @Override
    public void testGenerate() {
        byte[] expected = Util.hexStringToByteArray("460013796135f0f0c511d809660800200c9a66");
        byte[] actual = h.getBytes();

        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void testGetLength(){
        Assert.assertEquals(3+16, h.getLength());
    }

    @Test
    public void testToString(){
        TargetHeader h = new TargetHeader(new byte[]{(byte) 0xFF, (byte) 0xFE, 0x01});
        Assert.assertEquals("Target: FFFE01", h.toString());
    }

    @Override
    public void testParse() {
        byte[] data = Util.hexStringToByteArray("460013796135f0f0c511d809660800200c9a66");
        byte[] expected = Util.hexStringToByteArray("796135f0f0c511d809660800200c9a66");
        TargetHeader h = TargetHeader.parse(data);

        Assert.assertArrayEquals(expected, h.getValue());
    }
}