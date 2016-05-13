package com.johannes_mittendorfer.simpleobex;

import com.johannes_mittendorfer.simpleobex.header.EndOfBodyHeader;
import org.junit.Assert;
import org.junit.Before;

public class EndOfBodyHeaderTest extends HeaderTestAbstract {

    private EndOfBodyHeader h;

    @Before
    public void init(){
        byte[] data = {0x01, 0x00, 0x02};
        h = new EndOfBodyHeader(data);
    }

    @Override
    public void testGenerate() throws Exception {
        byte[] expected = new byte[]{0x49, 0x00, 0x06, 0x01, 0x00, 0x02};
        byte[] actual = h.getBytes();

        Assert.assertArrayEquals(expected, actual);
    }

    @Override
    public void testGetLength() throws Exception {
        // tested by byteheader test
    }

    @Override
    public void testToString() throws Exception {
        Assert.assertEquals("End of body: 010002", h.toString());
    }

    @Override
    public void testParse() throws Exception {
        byte[] data = new byte[]{0x49, 0x00, 0x06, 0x01, 0x00, 0x02};
        EndOfBodyHeader h = EndOfBodyHeader.parse(data);

        Assert.assertArrayEquals(new byte[]{0x1, 0x0, 0x2}, h.getValue());
    }
}