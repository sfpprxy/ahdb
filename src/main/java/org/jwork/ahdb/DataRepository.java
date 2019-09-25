package org.jwork.ahdb;

import org.jwork.ahdb.model.ValuableDataByAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataRepository extends JpaRepository<ValuableDataByAccount, String> {


}
