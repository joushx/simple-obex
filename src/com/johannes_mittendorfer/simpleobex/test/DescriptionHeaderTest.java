package com.johannes_mittendorfer.simpleobex.test;

import com.johannes_mittendorfer.simpleobex.header.DescriptionHeader;
import org.junit.Assert;
import org.junit.Before;

public class DescriptionHeaderTest extends HeaderTest {

    private DescriptionHeader h;

    @Before
    public void init(){
        h = new DescriptionHeader("Hi");
    }

    @Override
    public void testGenerate() throws Exception {
        // tested by unicode header
    }

    @Override
    public void testGetLength() throws Exception {
        // tested by unicode header
    }

    @Override
    public void testToString() throws Exception {
        Assert.assertEquals("Description: Hi", h.toString());
    }

    @Override
    public void testParse() throws Exception {
        byte[] data = new byte[]{0x05, 0x0, 0x7, 0x46, 0x6F, 0x6F, 0x00};
        DescriptionHeader h = DescriptionHeader.parse(data);

        Assert.assertEquals("Foo", h.getValue());
    }
}