package org.jwork.ahdb;

import org.jwork.ahdb.model.RawData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RawDataRepository extends JpaRepository<RawData, java.sql.Timestamp> {

}
