package org.ahdb.server;

import io.vavr.collection.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ahdb.server.model.ItemScan;
import org.ahdb.server.util.U;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ItemScanService {

    final ItemScanRepository itemScanRepository;

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
