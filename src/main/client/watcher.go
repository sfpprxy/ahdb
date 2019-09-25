package main

import (
	"io/ioutil"
	"os"
	"path/filepath"
	"strings"
	"time"
)

var confFile = "adhb.conf"

func readWowPath() string {
	bytes, e := ioutil.ReadFile(confFile)
	check(e)
	return string(bytes)
}

func saveWowPath(wowPath string) {
	e := ioutil.WriteFile(confFile, []byte(wowPath), 0644)
	check(e)
}

func findTsmFiles(wowPathStr string) map[string]string {
	dir := filepath.Dir(wowPathStr)
	accountDir := filepath.Join(dir, "WTF", "Account")
	accountDirs, e := ioutil.ReadDir(accountDir)
	check(e)
	if len(accountDirs) < 1 {
		log.Warn("未找到魔兽世界账户文件，请登录魔兽")
	}

	tsmfilesByAccount := make(map[string]string)
	for _, v := range accountDirs {
		name := v.Name()
		if strings.Contains(name, "#") {
			tsmFilePath := filepath.Join(accountDir, name, "SavedVariables", "TradeSkillMaster.lua")
			tsmfilesByAccount[name] = tsmFilePath
		}
	}
	if len(tsmfilesByAccount) == 0 {
		log.Warn("未找到TSM数据文件")
	}

	return tsmfilesByAccount
}

func filterChangedTsmFilesByAccount(tsmfilesByAccount map[string]string) map[string]string {
	changedTsmfilesByAccount := make(map[string]string)
	for a, f := range tsmfilesByAccount {
		info, e := os.Stat(f)
		check(e, a)
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
