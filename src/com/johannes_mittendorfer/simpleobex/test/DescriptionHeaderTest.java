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
        Assert.fail();
    }
}