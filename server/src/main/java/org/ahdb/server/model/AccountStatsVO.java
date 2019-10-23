package org.ahdb.server.model;

public class AccountStatsVO {
    public String accountId;
    public String chars;
    public String power;

    public String getAccountId() {
        return accountId;
    }

    public AccountStatsVO setAccountId(String accountId) {
        this.accountId = accountId;
        return this;
    }

    public String getChars() {
        return chars;
    }

    public AccountStatsVO setChars(String chars) {
        this.chars = chars;
        return this;
    }

    public String getPower() {
        return power;
    }

    public AccountStatsVO setPower(String power) {
        this.power = power;
        return this;
    }
}
