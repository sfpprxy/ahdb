package org.ahdb.server;

import org.ahdb.server.model.AccountStats;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountStatsRepository extends JpaRepository<AccountStats, String> {

    AccountStats findFirstByAccountId(String accountId);

}
