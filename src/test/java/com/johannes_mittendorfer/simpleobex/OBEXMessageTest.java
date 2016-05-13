package com.johannes_mittendorfer.simpleobex;

import com.johannes_mittendorfer.simpleobex.header.*;
import com.johannes_mittendorfer.simpleobex.header.templates.OBEXHeader;
import org.junit.Assert;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OBEXMessageTest {

    @Test
    public void testGenerate(){

        OBEXMessage message = new OBEXMessage((byte) 0x03, true);

        message.addHeader(new ConnectionIdHeader(1));
        message.addHeader(new NameHeader("telecom/pb.vcf"));
        message.addHeader(new TypeHeader("x-bt/phonebook"));

        byte[] expected = Util.hexStringToByteArray("83003bcb0000000101002100740065006c00650063006f006d002f00700062002e0076006300660000420012782d62742f70686f6e65626f6f6b00");
        byte[] actual = message.getBytes();

        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void testGenerateOpcode(){

        OBEXMessage message = new OBEXMessage(Opcode.GET, true);

        message.addHeader(new ConnectionIdHeader(1));
        message.addHeader(new NameHeader("telecom/pb.vcf"));
        message.addHeader(new TypeHeader("x-bt/phonebook"));

        byte[] expected = Util.hexStringToByteArray("83003bcb0000000101002100740065006c00650063006f006d002f00700062002e0076006300660000420012782d62742f70686f6e65626f6f6b00");
        byte[] actual = message.getBytes();

        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void testGenerateConnect(){
        OBEXMessage message = new OBEXMessage((byte)0x10, (byte)0x00, 65534);
        message.addHeader(new TargetHeader(Util.hexStringToByteArray("796135f0f0c511d809660800200c9a66")));

        byte[] expected = Util.hexStringToByteArray("80001a1000fffe460013796135f0f0c511d809660800200c9a66");
        byte[] actual = message.getBytes();

        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void testParse() throws UnsupportedEncodingException {
        byte[] data = Util.hexStringToByteArray("83003acb0000000101002100740065006c00650063006f006d002f00700062002e0076006300660000420012782d62742f70686f6e65626f6f6b00");
        OBEXMessage msg = OBEXMessage.parse(data);

        assertEquals(0x03, msg.getOpcode());
        assertTrue(msg.isFinal());

        OBEXHeader[] headers = msg.getHeaders();
        assertEquals(3, headers.length);
        assertEquals(1, ((ConnectionIdHeader)headers[0]).getId());
        assertEquals("telecom/pb.vcf", ((NameHeader) headers[1]).getValue());
        assertEquals("x-bt/phonebook", ((TypeHeader) headers[2]).getValue());
    }

    @Test
    public void testToString(){
        OBEXMessage message = new OBEXMessage((byte) 0x03, true);

        message.addHeader(new ConnectionIdHeader(1));
        message.addHeader(new NameHeader("telecom/pb.vcf"));
        message.addHeader(new TypeHeader("x-bt/phonebook"));

        assertEquals("Operation: GET\nLength: 59\nConnection-Id: 1\nName: telecom/pb.vcf\nType: x-bt/phonebook", message.toString());
    }

    @Test
    public void testToStringConnect(){
        OBEXMessage message = new OBEXMessage((byte) 0x10, (byte) 0x05, 200);

        assertEquals("Operation: CONNECT\nLength: 7\nFlags: 101\nVersion: 0x10\nMax. length: 200", message.toString());
    }
}
