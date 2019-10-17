package org.jwork.ahdb;

import org.jwork.ahdb.model.ItemScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface ItemScanRepository extends JpaRepository<ItemScan, String> {

    Optional<ItemScan> findFirstByScanTimeAfter(Timestamp timestamp);

//    @QueryHints(value = @QueryHint(name = HINT_FETCH_SIZE, value = "1"))
    @Query(value = "SELECT DISTINCT (scan_time) FROM item_scan ORDER BY scan_time", nativeQuery = true)
    List<Timestamp> streamAll();

}
