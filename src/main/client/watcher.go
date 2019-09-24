package main

import (
	"io/ioutil"
	"os"
	"path/filepath"
	"strings"
	"time"
)

func readWowPath() string {

	return ""
}

func saveWowPath(file string) {


}

func findTsmFiles(wowPathStr string) map[string]string {
	dir := filepath.Dir(wowPathStr)
	accountDir := filepath.Join(dir, "WTF", "Account")
	accountDirs, e := ioutil.ReadDir(accountDir)
	if e != nil {
		log.Error(e)
	}
	if len(accountDirs) < 1 {
		log.Error("no Account under WTF")
		os.Exit(1)
	}

	tsmfilesByAccount := make(map[string]string)
	for _, v := range accountDirs {
		name := v.Name()
		if strings.Contains(name, "#") {
			tsmFilePath := filepath.Join(accountDir, name, "SavedVariables", "TradeSkillMaster.lua")
			tsmfilesByAccount[name] = tsmFilePath
		}
	}

	return tsmfilesByAccount
}

func filterChangedTsmFilesByAccount(tsmfilesByAccount map[string]string) map[string]string {
	changedTsmfilesByAccount := make(map[string]string)
	for a, f := range tsmfilesByAccount {
		info, e := os.Stat(f)
		if e != nil {
			log.Error(e, a)
		}
		durSinceChanged := time.Now().Sub(info.ModTime())
		if durSinceChanged.Minutes() < 10 {
			changedTsmfilesByAccount[a] = f
		}
	}
	return changedTsmfilesByAccount
}

func getChangedTsmfilesByAccount() map[string]string {
	wowPath := readWowPath()
	tsmfilesByAccount := findTsmFiles(wowPath)
	return filterChangedTsmFilesByAccount(tsmfilesByAccount)
}
