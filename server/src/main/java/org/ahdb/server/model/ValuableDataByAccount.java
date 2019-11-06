package org.ahdb.server.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

@Getter
@Setter
@Accessors(chain = true)
public class ValuableDataByAccount {

    public Timestamp time;
    public String type;
    public String accountId;
    public ValuableData valuableData;

    @Override
    public String toString() {
        return "ValuableDataByAccount{" +
                "\ntime=" + time +
                "\ntype='" + type + '\'' +
                "\naccountId='" + accountId + '\'' +
                "\nvaluableData=" + valuableData +
                '}';
    }
}
