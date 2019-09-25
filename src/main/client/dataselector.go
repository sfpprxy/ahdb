package main

import (
	"io/ioutil"
	"strings"
)

func extractValuableDataByAccount(tsmfilesByAccount map[string]string) []ValuableDataByAccount {
	dataByAccount := make([]ValuableDataByAccount, 0)
	for account, f := range tsmfilesByAccount {
		bytes, e := ioutil.ReadFile(f)
		check(e, " extractValuableDataByAccount err")
		rawStr := string(bytes)
		charStr := subStr(rawStr, "[\"char\"] = {", "},")
		scanStr := subStr(rawStr, "[\"f@Horde - 觅心者@internalData@csvAuctionDBScan\"] = \"", "\",")

		dataByAccount = append(dataByAccount, ValuableDataByAccount{account, ValuableData{charStr, scanStr}})
	}

	return dataByAccount
}

func subStr(rawStr string, startTag string, endTag string) string {
	i1 := strings.Index(rawStr, startTag)
	i1 += len(startTag)
	sub := rawStr[i1:]
	i2 := strings.Index(sub, endTag)
	sub = rawStr[i1 : i1+i2]
	return sub
}
