package org.ahdb.service;

import io.vavr.collection.List;
import org.ahdb.model.ItemScan;
import org.ahdb.repo.ItemScanRepository;
import org.ahdb.util.U;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.sql.Timestamp;

@ApplicationScoped
public class ItemScanService {

    private static final Logger log = LoggerFactory.getLogger(ItemScanService.class);

    @Inject
    ItemScanRepository itemScanRepository;

    @Transactional
    public Boolean save(List<ItemScan> lis, Timestamp createTime) {
        Timestamp scanTime = lis.head().getScanTime();
        Timestamp shouldNotExist = Timestamp.valueOf(scanTime.toLocalDateTime().minusMinutes(5));
        ItemScan maybeis = itemScanRepository.findFirstByScanTimeAfter(shouldNotExist);
        if (maybeis == null) {
            lis.forEach(is -> {
                is.setRealm("觅心者-部落").setAddTime(createTime);
            });

            U.Timer t = U.newTimer();
            log.debug("start itemScanRepository.saveAll");
            itemScanRepository.saveAll(lis);
            log.debug("itemScanRepository.saveAll time: {} seconds", t.getTime());
            return true;
        } else {
            log.debug("scanTime {} last5MinScanTime {}", scanTime, maybeis.getScanTime());
        }
        return false;
    }
}
