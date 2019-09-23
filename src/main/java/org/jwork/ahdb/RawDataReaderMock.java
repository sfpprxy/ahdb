package org.jwork.ahdb;

import org.jwork.ahdb.util.U;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;

public class RawDataReaderMock {
    private static final Logger log = LoggerFactory.getLogger(RawDataReaderMock.class);

    static String raw;
    static {
        try {
            raw = U.readAll(Paths.get("/Users/joe/Dropbox/Work/wow/ahdb/src/main/resources/tsm_valuable_info.lua"));
        } catch (IOException ecc) {
            ecc.printStackTrace();
        }
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
