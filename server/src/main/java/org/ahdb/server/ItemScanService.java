package org.ahdb.server;

import io.vavr.collection.List;
import org.ahdb.server.model.ItemScan;
import org.ahdb.server.util.U;
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

    public Boolean save(List<ItemScan> lis, Timestamp createTime) {
        Timestamp scanTime = lis.head().scanTime;
        Timestamp shouldNotExist = Timestamp.valueOf(scanTime.toLocalDateTime().minusMinutes(5));
        Optional<ItemScan> maybeis = itemScanRepository.findFirstByScanTimeAfter(shouldNotExist);
        if (!maybeis.isPresent()) {
            lis.forEach(is -> {
                is.setRealm("觅心者-部落").setAddTime(createTime);
            });
            U.Timer t = U.newTimer();
            log.debug("start itemScanRepository.saveAll");
            itemScanRepository.saveAll(lis);
            log.debug("itemScanRepository.saveAll time: {}", t.getTime());
            return true;
        } else {
            log.debug("scanTime {} last5MinScanTime {}", scanTime, maybeis.get().scanTime);
        }
        return false;
    }
}
