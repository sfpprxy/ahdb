package org.jwork.ahdb;

import io.vavr.collection.List;
import io.vavr.collection.Set;
import org.jwork.ahdb.model.ItemDesc;
import org.jwork.ahdb.model.ItemScan;
import org.jwork.ahdb.util.U;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class ItemDescService {
    private static final Logger log = LoggerFactory.getLogger(ItemDescService.class);

    @Autowired
    ItemDescRepository itemDescRepository;

    private ForkJoinPool pool;
    private BlockingQueue<ItemDesc> descQueue;

    public void save(List<ItemScan> lis) {
        Set<String> dbIds = List.ofAll(itemDescRepository.findAllItemId()).toSet();
        Set<String> isIds = lis.map(is -> is.itemId).toSet();
        Set<String> newIds = isIds.diff(dbIds);

        if (pool == null) {
            pool = new ForkJoinPool(64);
        }
        if (descQueue == null) {
            descQueue = new LinkedBlockingQueue<>();
            startBatchSaveJob();
        }

        log.debug("start fetch ItemDesc...");
        pool.submit(() -> {
            newIds.toJavaParallelStream()
                    .forEach(id -> {
                        ItemDesc desc = ItemDescFetcher.getDesc(id);
                        if (desc != null) {
                            descQueue.offer(desc);
                        }
                    });
        });
    }

    public void startBatchSaveJob() {
        log.debug("startBatchSaveJob...");
        pool.submit(() -> {
            while (true) {
                ArrayList<ItemDesc> toSave = new ArrayList<>();
                descQueue.drainTo(toSave, 2000);
                if (!toSave.isEmpty()) {
                    itemDescRepository.saveAll(toSave);
                    log.info("{} new ItemDesc added", toSave.size());
                }
                U.sleep(60 * 1000);
            }
        });
    }
}
