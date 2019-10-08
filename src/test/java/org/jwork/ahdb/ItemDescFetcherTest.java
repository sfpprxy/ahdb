package org.jwork.ahdb;

import io.vavr.collection.List;
import io.vavr.collection.Set;
import org.junit.Test;
import org.jwork.ahdb.model.ItemDesc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ItemDescFetcherTest {
    private static final Logger log = LoggerFactory.getLogger(ItemDescFetcherTest.class);


    @Test
    public void cornerCase() {
        ItemDesc desc = ItemDescFetcher.getDesc("18335");
        log.debug(desc.toString());
    }

    @Test
    public void etc() {
        Set<Integer> dbids = List.of(1, 2, 3, 4, 5).toSet();
        Set<Integer> isids = List.of(2, 3, 4, 6).toSet();

        Set<Integer> diff = isids.diff(dbids);
        System.out.println(diff);

    }

    @Test
    public void exception() {
        boolean a = false;
        if (1*5>2) {
            throw new RuntimeException("aaa");
        }
        if (a) {
            System.out.println("OK");
        }
    }

}
