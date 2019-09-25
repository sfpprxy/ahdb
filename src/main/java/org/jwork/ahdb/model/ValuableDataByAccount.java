package org.jwork.ahdb.model;

import javax.persistence.Id;

public class ValuableDataByAccount {

    @Id
    public Long id;
    public String type;
    public String accountId;
    public ValuableData valuableData;

    public Long getId() {
        return id;
    }

    public ValuableDataByAccount setId(Long idcc) {
        id = idcc;
        return this;
    }

    public String getType() {
        return type;
    }

    public ValuableDataByAccount setType(String typecc) {
        type = typecc;
        return this;
    }

    public String getAccountId() {
        return accountId;
    }

    public ValuableDataByAccount setAccountId(String accountIdcc) {
        accountId = accountIdcc;
        return this;
    }

    public ValuableData getValuableData() {
        return valuableData;
    }

    public ValuableDataByAccount setValuableData(ValuableData valuableDatacc) {
        valuableData = valuableDatacc;
        return this;
    }
}
