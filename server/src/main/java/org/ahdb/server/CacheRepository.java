package org.ahdb.server;

import org.ahdb.server.model.BizCache;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CacheRepository extends JpaRepository<BizCache, String> {

}
