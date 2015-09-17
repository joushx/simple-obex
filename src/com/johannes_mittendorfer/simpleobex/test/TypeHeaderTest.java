package com.johannes_mittendorfer.simpleobex.test;

import com.johannes_mittendorfer.simpleobex.header.TypeHeader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TypeHeaderTest extends HeaderTest {

    TypeHeader h;

    @Before
    public void init(){
        h = new TypeHeader("x-bt/phonebook");
    }

    @Test
    public void testGenerate(){
        byte[] expected = TestUtil.hexStringToByteArray("420012782d62742f70686f6e65626f6f6b00");
        byte[] actual = h.getBytes();

        Assert.assertArrayEquals(expected, actual);
    }

    @Override
    public void testGetLength() throws Exception {
        // tested by string header
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGenerateNull(){
        new TypeHeader(null);
    }

    @Test
    public void testParse() {
        byte[] data = TestUtil.hexStringToByteArray("420012782d62742f70686f6e65626f6f6b00");
        TypeHeader h = TypeHeader.parse(data);

        Assert.assertEquals("x-bt/phonebook", h.getValue());
        Assert.assertEquals(1 + 2 + "x-bt/phonebook".length() + 1, h.getLength());
    }

    @Test
    public void testToString(){
        Assert.assertEquals("Type: x-bt/phonebook", h.toString());
    }
}
