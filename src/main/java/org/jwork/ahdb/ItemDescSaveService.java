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

import java.util.Objects;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

@Service
public class ItemDescSaveService {
    private static final Logger log = LoggerFactory.getLogger(ItemDescSaveService.class);

    @Autowired
    ItemDescRepository itemDescRepository;

    public void save(List<ItemScan> lis) {
        log.debug("itemDescSaveService.save start");
        Set<String> dbIds = List.ofAll(itemDescRepository.findAllItemId()).toSet();
        Set<String> isIds = lis.map(is -> is.itemId).toSet();
        Set<String> newIds = isIds.diff(dbIds);

        ForkJoinPool poll = new ForkJoinPool(64);
        log.debug("Pool Submit");
        poll.submit(() ->
                {
                    U.Timer t1 = U.newTimer();
                    java.util.List<ItemDesc> newlis = newIds.toJavaParallelStream()
                            .map(ItemDescFetcher::getDesc)
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());
                    log.debug("fetch time: {}", t1.getTime());
                    itemDescRepository.saveAll(newlis);
                }
        );
    }
}
