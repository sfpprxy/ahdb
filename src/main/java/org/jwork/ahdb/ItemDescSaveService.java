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
public class ItemDescSaveService {
    private static final Logger log = LoggerFactory.getLogger(ItemDescSaveService.class);

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
                            try {
                                descQueue.put(desc);
                            } catch (Exception ex) {
                                log.error("put queue fail, item id: {}", desc.id, ex);
                            }
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
                itemDescRepository.saveAll(toSave);
                U.sleep(60 * 1000);
            }
        });
    }
}
