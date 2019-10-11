package org.jwork.ahdb;

import io.vavr.collection.List;
import org.jwork.ahdb.model.ItemScan;
import org.jwork.ahdb.util.U;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class ItemScanService {
    private static final Logger log = LoggerFactory.getLogger(ItemScanService.class);

    @Autowired
    ItemScanRepository itemScanRepository;

    public void save(List<ItemScan> lis, Timestamp createTime) {
        Optional<ItemScan> maybeis = itemScanRepository.findFirstByScanTime(lis.head().scanTime);
        if (!maybeis.isPresent()) {
            lis.forEach(is -> {
                is.setRealm("觅心者-部落").setAddTime(createTime);
            });
            U.Timer t = U.newTimer();
            log.debug("start itemScanRepository.saveAll");
            itemScanRepository.saveAll(lis);
            log.debug("itemScanRepository.saveAll time: {}", t.getTime());
        }
    }
}
