package org.jwork.ahdb;

import org.junit.Test;
import org.jwork.ahdb.util.U;

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
