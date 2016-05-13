package com.johannes_mittendorfer.simpleobex.header;

import com.johannes_mittendorfer.simpleobex.HeaderTestAbstract;
import com.johannes_mittendorfer.simpleobex.header.BodyHeader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BodyHeaderTest extends HeaderTestAbstract {

    private BodyHeader h;

    @Before
    public void init(){
        byte[] data = new byte[]{0x1, 0x2, 0x01};
        h = new BodyHeader(data);
    }

    @Override
    public void testGenerate() {
        byte[] expected = new byte[]{0x48, 0x00, 0x6, 0x1, 0x2, 0x1};
        byte[] actual = h.getBytes();

        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void testGetLength() throws Exception {
        byte[] data = new byte[]{0x1, 0x2, 0x01};
        BodyHeader h = new BodyHeader(data);
        Assert.assertEquals(6, h.getLength());
    }

    @Test
    public void testToString() throws Exception {
        byte[] data = new byte[]{0x1, 0x2, 0x01};
        BodyHeader h = new BodyHeader(data);
        Assert.assertEquals("Body: 010201", h.toString());
    }

    @Override
    public void testParse() {

    }
}