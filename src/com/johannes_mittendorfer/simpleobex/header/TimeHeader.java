package com.johannes_mittendorfer.simpleobex.header;

import com.johannes_mittendorfer.simpleobex.header.templates.OBEXHeader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimeHeader extends OBEXHeader<Date> {

    public TimeHeader(Date time){
        super((byte) 0x44, time);

        String str = getISO8601StringForDate(time);

        int length = str.getBytes().length + 3;
        bytes.add((byte) (length >> 8));
        bytes.add((byte) length);
        for(byte b: str.getBytes()){
            bytes.add(b);
        }
    }

    private String getISO8601StringForDate(Date date) {

        if(date == null){
            throw new IllegalArgumentException("date cannot be null");
        }

        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String str = dateFormat.format(date);
        return str;
    }

    public static TimeHeader parse(byte[] data){
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public int getLength() {
        return 1 + 2 + 15;
    }

    @Override
    public String toString() {
        return "Time: " + value;
    }
}
