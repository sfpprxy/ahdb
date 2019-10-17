package org.ahdb.server;

import org.ahdb.server.util.U;
import org.junit.Test;

public class RawMetaTest {

    @Test
    public void getItemCata() {
        RawMeta.getItemCata();
    }

    @Test
    public void time() {
        U.Timer t = U.newTimer();
        U.sleep(1111);
        System.out.println(t.getTime());
    }
}
