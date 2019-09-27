package org.jwork.ahdb;

import org.jwork.ahdb.model.RawLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RawLogRepository extends JpaRepository<RawLog, java.sql.Timestamp> {

}
