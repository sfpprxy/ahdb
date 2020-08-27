package org.ahdb.repo;

import org.ahdb.model.ItemScan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;

public interface ItemScanRepository extends JpaRepository<ItemScan, String> {

    ItemScan findFirstByScanTimeAfter(Timestamp timestamp);

    ItemScan findFirstByItemIdOrderByScanTimeDesc(String itemId);

}
