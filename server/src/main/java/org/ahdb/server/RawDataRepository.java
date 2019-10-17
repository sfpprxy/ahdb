package org.ahdb.server;

import org.ahdb.server.model.RawData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface RawDataRepository extends JpaRepository<RawData, Timestamp> {

    List<RawData> findByTimeGreaterThan(Timestamp timestamp);
}
