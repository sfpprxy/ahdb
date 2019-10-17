package org.ahdb.server;

import org.ahdb.server.model.RawLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RawLogRepository extends JpaRepository<RawLog, java.sql.Timestamp> {

}
