package com.johannes_mittendorfer.simpleobex.test;

import com.johannes_mittendorfer.simpleobex.header.HTTPHeader;
import org.junit.Assert;
import org.junit.Before;

public class HTTPHeaderTest extends HeaderTest {

    private HTTPHeader h;

    @Before
    public void init(){
        h = new HTTPHeader("a");
    }

    @Override
    public void testGenerate() throws Exception {

    }

    @Override
    public void testGetLength() throws Exception {

    }

    @Override
    public void testToString() throws Exception {
        Assert.assertEquals("HTTP: a", h.toString());
    }

    @Override
    public void testParse() throws Exception {

    }
}