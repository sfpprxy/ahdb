package org.ahdb.repo;

import org.ahdb.model.RawLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RawLogRepository extends JpaRepository<RawLog, java.sql.Timestamp> {

}
