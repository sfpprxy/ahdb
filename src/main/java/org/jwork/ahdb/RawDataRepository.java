package org.jwork.ahdb;

import org.jwork.ahdb.model.RawData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface RawDataRepository extends JpaRepository<RawData, java.sql.Timestamp> {

    List<RawData> findByTimeGreaterThan(Timestamp timestamp);
}
