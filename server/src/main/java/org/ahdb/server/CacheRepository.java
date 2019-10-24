package org.ahdb.server;

import org.ahdb.server.model.CacheWrapper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CacheRepository extends JpaRepository<CacheWrapper, String> {

}
