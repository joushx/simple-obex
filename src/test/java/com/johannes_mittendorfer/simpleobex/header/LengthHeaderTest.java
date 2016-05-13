package com.johannes_mittendorfer.simpleobex.header;

import com.johannes_mittendorfer.simpleobex.HeaderTestAbstract;
import com.johannes_mittendorfer.simpleobex.Util;
import com.johannes_mittendorfer.simpleobex.header.LengthHeader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LengthHeaderTest extends HeaderTestAbstract {

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
        byte[] data = Util.hexStringToByteArray("c30007d9ca");
        LengthHeader h = LengthHeader.parse(data);

        Assert.assertEquals(514506, (int)h.getValue());
    }

    @Test
    public void testGenerate() throws Exception {
        byte[] expected = Util.hexStringToByteArray("c30007d9ca");
        byte[] actual = h.getBytes();

        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void testGetLength(){
        Assert.assertEquals(5, h.getLength());
    }
}