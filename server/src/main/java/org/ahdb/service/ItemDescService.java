package org.ahdb.service;

import io.vavr.collection.List;
import io.vavr.collection.Set;
import org.ahdb.common.AhdbUserException;
import org.ahdb.model.IdsOnly;
import org.ahdb.model.ItemDesc;
import org.ahdb.model.ItemScan;
import org.ahdb.repo.ItemDescRepository;
import org.ahdb.util.U;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.LinkedBlockingQueue;

@ApplicationScoped
public class ItemDescService {

    private static final Logger log = LoggerFactory.getLogger(ItemDescService.class);

    @Inject
    ItemDescRepository itemDescRepository;

    private ForkJoinPool pool;
    private BlockingQueue<ItemDesc> descQueue;

    public java.util.List<ItemDesc> findItems(String item) {
        List<ItemDesc> descs = List.ofAll(itemDescRepository.findTop10ByIdOrNameContains(item, item));
        if (descs.isEmpty()) {
            throw new AhdbUserException(AhdbUserException.NO_ITEM);
        }
        return descs.asJava();
    }

    public ItemDesc getItem(String item) {
        java.util.List<ItemDesc> descs = itemDescRepository.findByIdOrName(item, item);
        if (descs.isEmpty()) {
            throw new AhdbUserException(AhdbUserException.NO_ITEM);
        }
        ItemDesc desc = descs.get(0);
        return desc;
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

    public void save(List<ItemScan> lis) {
        // TODO: fix java.lang.ClassCastException: org.ahdb.model.ItemDesc incompatible with org.ahdb.model.IdsOnly
        Set<String> dbIds = List.ofAll(itemDescRepository.findIdsByIdNotNull()).map(IdsOnly::getId).toSet();
        Set<String> isIds = lis.map(is -> is.getItemId()).toSet();
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

}
