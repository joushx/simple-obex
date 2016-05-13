package com.johannes_mittendorfer.simpleobex.header;

import com.johannes_mittendorfer.simpleobex.header.templates.OBEXHeader;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;

public class TimeHeader extends OBEXHeader<ZonedDateTime> {

    private static DateFormat dateFormat;

    static {
        dateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
    }

    public TimeHeader(ZonedDateTime time){

        // convert to UTC
        super((byte) 0x44, ZonedDateTime.ofInstant(time.toInstant(), ZoneId.of("UTC")));

        // create ISO 8601 string
        String str = getISO8601StringForDate(value);

        int length = str.getBytes().length + 3;
        bytes.add((byte) (length >> 8));
        bytes.add((byte) length);
        for(byte b: str.getBytes()){
            bytes.add(b);
        }
    }

    private String getISO8601StringForDate(ZonedDateTime date) {

        if(date == null){
            throw new IllegalArgumentException("date cannot be null");
        }

        return date.format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss"));
    }

    public static TimeHeader parse(byte[] data) throws ParseException {
        String timestamp = new String(Arrays.copyOfRange(data, 3, data.length));
        ZonedDateTime time = ZonedDateTime.ofInstant(dateFormat.parse(timestamp).toInstant(), ZoneId.of("UTC"));
        return new TimeHeader(time);
    }

    @Override
    public int getLength() {
        return 1 + 2 + 15;
    }

    @Override
    public String toString() {
        return "Time: " + value.format(DateTimeFormatter.ISO_DATE_TIME);
    }
}
