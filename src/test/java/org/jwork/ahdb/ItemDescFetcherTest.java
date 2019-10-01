package org.jwork.ahdb;

import org.junit.Test;
import org.jwork.ahdb.model.ItemDesc;

public class ItemDescFetcherTest {

    @Test
    public void getInfo() {
        ItemDesc id1 = ItemDescFetcher.getDesc("2291");
        ItemDesc id2 = ItemDescFetcher.getDesc("8899");
        ItemDesc id3 = ItemDescFetcher.getDesc("2447");
        System.out.println();
        System.out.println();
        System.out.println(id1);
        System.out.println(id1.vendorSell);
        System.out.println(id2);
        System.out.println(id3);
    }
}
