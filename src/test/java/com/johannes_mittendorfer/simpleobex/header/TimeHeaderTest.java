package com.johannes_mittendorfer.simpleobex.header;

import com.johannes_mittendorfer.simpleobex.HeaderTestAbstract;
import com.johannes_mittendorfer.simpleobex.Util;
import com.johannes_mittendorfer.simpleobex.header.TimeHeader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import sun.util.resources.cldr.sk.TimeZoneNames_sk;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class TimeHeaderTest extends HeaderTestAbstract {

    private TimeHeader h;
    private ZonedDateTime time;

    @Before
    public void init(){
        GregorianCalendar d = new GregorianCalendar(2012, 7, 24,13,5,58);
        d.setTimeZone(TimeZone.getTimeZone("Europe/Vienna"));
        time = ZonedDateTime.ofInstant(d.toInstant(), ZoneId.of("Europe/Vienna"));
        h = new TimeHeader(time);
    }

    @Test
    public void testGenerate(){
        byte[] expected = Util.hexStringToByteArray("440012323031323038323454313130353538");
        byte[] actual = h.getBytes();

        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void testGetLength(){
        Assert.assertEquals(18, h.getLength());
    }

    @Test
    public void testToString(){
        Assert.assertEquals("Time: 2012-08-24T11:05:58Z[UTC]", h.toString());
    }

    @Test(expected = NullPointerException.class)
    public void testGenerateNull(){
        new TimeHeader(null);
    }

    @Test
    public void testParse() throws UnsupportedEncodingException, ParseException {
        byte[] data = Util.hexStringToByteArray("440012323031323038323454313130353538");
        TimeHeader header = TimeHeader.parse(data);

        Assert.assertEquals("Time: 2012-08-24T11:05:58Z[UTC]", header.toString());
    }
}
