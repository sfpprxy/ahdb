package main

import (
	"testing"
)

func TestSelectValuableDataByAccount(t *testing.T) {
	changedTsmfilesByAccount := make(map[string]string)
	winPath := "D:\\Game\\World of Warcraft\\_classic_\\WTF\\Account\\178635643#2\\SavedVariables\\TradeSkillMaster.lua"
	macPath := "/Applications/World of Warcraft/_classic_/WTF/Account/178635643#2/SavedVariables/TradeSkillMaster.lua"
	log.Debug("path: ", winPath, macPath)

	changedTsmfilesByAccount["178635643#2"] = winPath
	selectValuableDataByAccount(changedTsmfilesByAccount)
}
