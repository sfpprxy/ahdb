package main

import (
	"testing"
)

func TestUpload(t *testing.T) {
	changedTsmfilesByAccount := make(map[string]string)
	winPath := "D:\\Game\\World of Warcraft\\_classic_\\WTF\\Account\\178635643#2\\SavedVariables\\TradeSkillMaster.lua"
	macPath := "/Applications/World of Warcraft/_classic_/WTF/Account/178635643#2/SavedVariables/TradeSkillMaster.lua"

	path := winPath
	if isOnMac() {
		path = macPath
	}

	changedTsmfilesByAccount["178635643#2"] = path
	valuableDataByAccount := extractValuableDataByAccount(changedTsmfilesByAccount)

	ok := upload(valuableDataByAccount)
	log.Debug("ok ", ok)
}
