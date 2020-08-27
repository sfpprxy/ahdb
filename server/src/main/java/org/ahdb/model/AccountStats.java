package org.ahdb.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class AccountStats {

    @Id
    public String accountId;
    public String chars;
    public Integer power;
    public Timestamp lastPush;

    public AccountStats() {
    }

    public String getAccountId() {
        return accountId;
    }

    public AccountStats setAccountId(String accountId) {
        this.accountId = accountId;
        return this;
    }

    public String getChars() {
        return chars;
    }

    public AccountStats setChars(String chars) {
        this.chars = chars;
        return this;
    }

    public Integer getPower() {
        return power;
    }

    public AccountStats setPower(Integer power) {
        this.power = power;
        return this;
    }

    public Timestamp getLastPush() {
        return lastPush;
    }

    public AccountStats setLastPush(Timestamp lastPush) {
        this.lastPush = lastPush;
        return this;
    }
}
