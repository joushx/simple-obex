package com.johannes_mittendorfer.simpleobex_example;

import com.johannes_mittendorfer.simpleobex.OBEXMessage;
import com.johannes_mittendorfer.simpleobex.header.TargetHeader;

public class Main {

    public static void main(){
        
        // create a connect message
        byte version = 0x10;
        byte flags = 0x10;
        int maxLength = 1000;
        OBEXMessage message = new OBEXMessage(version, flags, maxLength);

        // add a header
        byte[] target = new byte[]{'S','Y','N','C','M','L','-','S','Y','N','C'};
        message.addHeader(new TargetHeader(target));

        // get bytes
        byte[] messageBytes = message.getBytes();
    }

}
