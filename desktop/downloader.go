package main

import (
	"strings"
)

func fetchItemStats() string {
	body, e := get(getItemStatsUrl() + "?account=" + accountStats.AccountId)
	if e != nil {
		check(e, "获取拍卖信息失败")
		return ""
	}

	// fixme do not hard-code
	s := string(body)
	if strings.Contains(s, "not enough power!") {
		log.Info("能量不足，无法传输数据")
		return ""
	}
	return s
}
