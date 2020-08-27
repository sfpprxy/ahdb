package org.ahdb.repo;


import org.ahdb.model.AccountStats;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountStatsRepository extends JpaRepository<AccountStats, String> {

    AccountStats findFirstByAccountId(String accountId);

}
