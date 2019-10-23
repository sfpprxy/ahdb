package main

import (
	"encoding/json"
	"io/ioutil"
	"time"
)

var confFile = "adhb.json"
var timeLayout = "2006-01-02 15:04:05"

func getBaseUrl() string {
	host := "http://123.206.124.78:9999/ahdb"
	if onDebug() {
		if !onRemote() {
			host = "http://localhost:9999/ahdb"
		}
	}
	return host
}

func getUploadUrl() string {
	return getBaseUrl() + "/push"
}

func getBootyBayUrl() string {
	return getBaseUrl()
}

func getAccountStatsUrl() string {
	return getBaseUrl() + "/account-stats"
}

func loadConf() (error, Config) {
	var c Config
	b, err := ioutil.ReadFile(confFile)
	check(err, "读取配置文件失败")
	err = json.Unmarshal(b, &c)
	return err, c
}

func saveConf(c Config) error {
	b, err := json.Marshal(c)
	err = ioutil.WriteFile(confFile, b, 0644)
	return err
}

func readWowPath() string {
	err, conf := loadConf()
	check(err, "读取WoW路径失败")
	return conf.WowPath
}

func saveWowPath(wowPath string) {
	err, c := loadConf()
	c.WowPath = wowPath
	err = saveConf(c)
	check(err, "保存WoW路径失败")
}

func readLastUploadTime() time.Time {
	err, c := loadConf()
	t, err := time.Parse(timeLayout, c.LastUploadTime)
	check(err, "读取上次上传时间失败")
	return t
}

func saveLastUploadTime(t time.Time) {
	err, c := loadConf()
	c.LastUploadTime = t.Format(timeLayout)
	err = saveConf(c)
	check(err, "保存上次上传时间失败")
}

func readLastUploadAccount() string {
	err, c := loadConf()
	check(err, "读取上次上传账号失败")
	return c.LastUploadAccount
}

func saveLastUploadAccount(aid string) {
	err, c := loadConf()
	c.LastUploadAccount = aid
	err = saveConf(c)
	check(err, "保存上次上传账号失败")
}
