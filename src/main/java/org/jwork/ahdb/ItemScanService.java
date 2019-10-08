package org.jwork.ahdb;

import io.vavr.collection.List;
import org.jwork.ahdb.model.ItemScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class ItemScanService {
    private static final Logger log = LoggerFactory.getLogger(ItemScanService.class);

    @Autowired
    ItemScanRepository itemScanRepository;

    public void save(List<ItemScan> lis, Timestamp createTime) {
        // TODO: only save not existed
        lis.forEach(is -> {
            is.setRealm("觅心者-部落").setAddTime(createTime);
        });
        itemScanRepository.saveAll(lis);
    }
}
