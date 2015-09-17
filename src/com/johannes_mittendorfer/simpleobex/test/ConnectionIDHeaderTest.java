package com.johannes_mittendorfer.simpleobex.test;

import com.johannes_mittendorfer.simpleobex.header.ConnectionIdHeader;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ConnectionIDHeaderTest extends HeaderTest{

    private ConnectionIdHeader h;

    @Before
    public void init(){
        h = new ConnectionIdHeader(1);
    }

    @Test
    public void testGenerate(){
        byte[] actual = h.getBytes();
        byte[] expected = TestUtil.hexStringToByteArray("cb00000001");

        Assert.assertArrayEquals(expected, actual);
    }

    @Override
    public void testGetLength() {
        int expected = 5;
        Assert.assertEquals(expected, h.getLength());
    }

    @Test
    public void testParse(){
        byte[] data = TestUtil.hexStringToByteArray("cb00000001");
        ConnectionIdHeader h = ConnectionIdHeader.parse(data);

        TestCase.assertEquals(1, h.getId());
        TestCase.assertEquals(5, h.getLength());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGenerateTooShort(){
        ConnectionIdHeader.parse(new byte[]{1});
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGenerateTooLong(){
        ConnectionIdHeader.parse(new byte[]{1,2,3,4,5,5,6,7,7,8,8});
    }

    @Test
    public void testToString() throws Exception {
        Assert.assertEquals("Connection-Id: 1", h.toString());
    }
}
