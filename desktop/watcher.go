package main

import (
	"os"
	"time"
)

func findTsmFiles() map[string]string {
	return findSavedVariablesByAccount("TradeSkillMaster.lua")
}

func filterChangedTsmFilesByAccount(tsmfilesByAccount map[string]string) map[string]string {
	changedTsmfilesByAccount := make(map[string]string)
	for a, f := range tsmfilesByAccount {
		info, e := os.Stat(f)
		check(e, a)
		if e != nil {
			continue
		}
		durSinceChanged := time.Now().Sub(info.ModTime())
		if durSinceChanged.Minutes() < 10 {
			changedTsmfilesByAccount[a] = f
		}
	}
	return changedTsmfilesByAccount
}

func getChangedTsmfilesByAccount() map[string]string {
	tsmfilesByAccount := findTsmFiles()
	return filterChangedTsmFilesByAccount(tsmfilesByAccount)
}
