package org.jwork.ahdb;

import org.junit.Test;

public class ItemDescFetcherTest {

    @Test
    public void getInfo() {
        ItemDescFetcher.getDesc("14160");
        ItemDescFetcher.getDesc("8899");
        ItemDescFetcher.getDesc("123");
    }
}
