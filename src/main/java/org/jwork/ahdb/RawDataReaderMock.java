package org.jwork.ahdb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class RawDataReaderMock {
    private static final Logger log = LoggerFactory.getLogger(RawDataReaderMock.class);

    static String raw;
    static {

    }

    public static String readItemsId() {
        return null;
    }

    public static String readAuctionDBScan() {
        String startTag = "[\"f@Horde - 觅心者@internalData@csvAuctionDBScan\"] = \"";
        String endTag = "\",";
        char spliter = '\n';

        int is = Objects.requireNonNull(raw).indexOf(startTag) + startTag.length();
        int ie = raw.substring(is).indexOf(endTag) + is;
        String str = raw.substring(is, ie);

        return str;
    }

}
