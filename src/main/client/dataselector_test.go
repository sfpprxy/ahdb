package main

import (
	"testing"
)

func TestSelectValuableDataByAccount(t *testing.T) {
	changedTsmfilesByAccount := make(map[string]string)
	changedTsmfilesByAccount["178635643#2"] = "/Applications/World of Warcraft/" +
		"_classic_/WTF/Account/178635643#2/SavedVariables/TradeSkillMaster.lua"
	selectValuableDataByAccount(changedTsmfilesByAccount)
}
