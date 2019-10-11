package org.jwork.ahdb;

import org.jwork.ahdb.model.ItemScan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.Optional;

public interface ItemScanRepository extends JpaRepository<ItemScan, String> {

    Optional<ItemScan> findFirstByScanTime(Timestamp timestamp);
}
