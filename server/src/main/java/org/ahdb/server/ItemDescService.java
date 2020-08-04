package org.ahdb.server;

import io.vavr.collection.List;
import io.vavr.collection.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ahdb.server.model.ItemDesc;
import org.ahdb.server.model.ItemScan;
import org.ahdb.server.util.U;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ItemDescService {

    final ItemDescRepository itemDescRepository;

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

}
