package main

import (
	"fmt"
	"strings"
)

func fetchItemStats() (string, error) {
	body, e := get(getItemStatsUrl() + "?account=" + accountStats.AccountId)
	if e != nil {
		r := "获取拍卖信息失败"
		check(e, r)
		return "", fmt.Errorf(r)
	}

	// fixme do not hard-code
	s := string(body)
	if strings.Contains(s, "not enough power!") {
		r := "能量不足，无法传输数据"
		log.Info(r)
		return "", fmt.Errorf(r)
	}
	return s, nil
}
