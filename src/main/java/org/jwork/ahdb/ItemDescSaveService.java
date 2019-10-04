package org.jwork.ahdb;

import io.vavr.collection.List;
import org.jwork.ahdb.model.ItemDesc;
import org.jwork.ahdb.model.ItemScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ForkJoinPool;

@Service
public class ItemDescSaveService {
    private static final Logger log = LoggerFactory.getLogger(ItemDescSaveService.class);

    @Autowired
    ItemDescRepository itemDescRepository;

    public void save(List<ItemScan> lis) {
        ForkJoinPool poll = new ForkJoinPool(128);
        poll.submit(() ->
                lis.toJavaParallelStream().forEach(is -> {
                    try {
                        int size = itemDescRepository.findAllById(is.itemString).size();
                        if (size < 1) {
                            ItemDesc desc = ItemDescFetcher.getDesc(is.itemString);
                            itemDescRepository.save(desc);
                        }
                    } catch (Exception ex) {
                        log.error("save ItemDesc id: {} fail: ", is.itemString, ex);
                    }
                })
        );
    }
}
