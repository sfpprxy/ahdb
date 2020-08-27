package org.ahdb.repo;

import org.ahdb.model.CacheWrapper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CacheRepository extends JpaRepository<CacheWrapper, String> {

}
