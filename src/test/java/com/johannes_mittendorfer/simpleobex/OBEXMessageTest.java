package com.johannes_mittendorfer.simpleobex;

import com.johannes_mittendorfer.simpleobex.header.*;
import com.johannes_mittendorfer.simpleobex.header.templates.OBEXHeader;
import org.junit.Assert;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OBEXMessageTest {

    @Test
    public void testGenerate(){

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
    public void testParsePhonebook() throws UnsupportedEncodingException {
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
    public void testParsePutFile() throws UnsupportedEncodingException {
        byte[] data = Util.hexStringToByteArray("020043cb000000010100170069006d006100670065002e006a00700067000042000d696d6167652f6a706700440012323031363037303654313630323330c300064952");
        OBEXMessage msg = OBEXMessage.parse(data);

        assertEquals(0x02, msg.getOpcode());
        assertFalse(msg.isFinal());

        OBEXHeader[] headers = msg.getHeaders();
        assertEquals(5, headers.length);
        assertEquals(1, ((ConnectionIdHeader)headers[0]).getId());
        assertEquals("image.jpg", ((NameHeader) headers[1]).getValue());
        assertEquals("image/jpg", ((TypeHeader) headers[2]).getValue());
        assertEquals("2016-07-06T16:02:30Z[UTC]", ((TimeHeader) headers[3]).getValue().toString());
        assertEquals((Integer) 411986, ((LengthHeader) headers[4]).getValue());
    }

    @Test
    public void testParseConnect() throws UnsupportedEncodingException {
        byte[] data = Util.hexStringToByteArray("80000710000384");
        OBEXMessage msg = OBEXMessage.parse(data);

        assertEquals(Opcode.CONNECT.getNumVal(), msg.getOpcode());
        assertTrue(msg.isFinal());

        OBEXHeader[] headers = msg.getHeaders();
        assertEquals(0, headers.length);

        assertEquals(0x10, msg.getVersion());
        assertEquals(0x0, msg.getFlags());
        assertEquals(900, msg.getMaxLength());
    }

    @Test
    public void testParseContinue() throws UnsupportedEncodingException {
        byte[] data = Util.hexStringToByteArray("900003");
        OBEXMessage msg = OBEXMessage.parse(data);

        assertEquals(Opcode.CONTINUE.getNumVal(), msg.getOpcode());
        assertTrue(msg.isFinal());

        OBEXHeader[] headers = msg.getHeaders();
        assertEquals(0, headers.length);
    }

    @Test
    public void testToString(){
        OBEXMessage message = new OBEXMessage(Opcode.GET, true);

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
