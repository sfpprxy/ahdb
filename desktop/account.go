package main

import "encoding/json"

func getAccountStats(account string) AccountStats {
	var as AccountStats
	url := getAccountStatsUrl() + "?account=" + account
	log.Debug(url)
	// todo fix missing "#" within wow account id
	b, err := get(url)
	check(err, "获取帐号信息失败")
	log.Debug(string(b))
	err = json.Unmarshal(b, &as)
	check(err)
	return as
}
