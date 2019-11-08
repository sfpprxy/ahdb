package main

import (
	"io/ioutil"
	"os"
	"path/filepath"
	"strings"
)

var lastScanStrByAccount = make(map[string]string)

func findSavedVariablesByAccount(fileName string) map[string]string {
	dir := filepath.Dir(readWowPath())
	accountDir := filepath.Join(dir, "WTF", "Account")
	accountDirs, e := ioutil.ReadDir(accountDir)
	check(e)
	if len(accountDirs) < 1 {
		log.Warn("未找到魔兽世界账户文件，请登录魔兽")
	}

	savedVariablesByAccount := make(map[string]string)
	for _, v := range accountDirs {
		name := v.Name()
		if strings.Contains(name, "#") {
			svFilePath := filepath.Join(accountDir, name, "SavedVariables", fileName)
			info, err := os.Stat(svFilePath)
			if os.IsNotExist(err) {
				log.Debug(info, err)
			} else {
				savedVariablesByAccount[name] = svFilePath
			}
		}
	}
	if len(savedVariablesByAccount) == 0 {
		log.Warn("未找到数据文件 " + fileName)
	}

	return savedVariablesByAccount
}

func subStr(rawStr string, startTag string, endTag string) string {
	i1 := strings.Index(rawStr, startTag)
	i1 += len(startTag)
	sub := rawStr[i1:]
	i2 := strings.Index(sub, endTag)
	sub = rawStr[i1 : i1+i2]
	return sub
}

func extractValuableDataByAccount(tsmfilesByAccount map[string]string) []ValuableDataByAccount {
	dataByAccount := make([]ValuableDataByAccount, 0)
	for account, f := range tsmfilesByAccount {
		bytes, e := ioutil.ReadFile(f)
		check(e, " extractValuableDataByAccount err")
		rawStr := string(bytes)

		charStr := subStr(rawStr, "[\"char\"] = {", "},")
		scanStr := subStr(rawStr, "[\"f@Horde - 觅心者@internalData@csvAuctionDBScan\"] = \"", "\",")

		if lastScanStrByAccount[account] == scanStr {
			continue
		} else {
			lastScanStrByAccount[account] = scanStr
		}

		var typ string
		if isOnMac() {
			typ = "mac"
		} else {
			typ = "win"
		}
		if onDebug() {
			typ = "debug"
		}

		dataByAccount = append(dataByAccount, ValuableDataByAccount{typ, account, ValuableData{charStr, scanStr}})
	}

	return dataByAccount
}
