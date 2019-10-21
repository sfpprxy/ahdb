package main

import (
	"encoding/json"
)

func upload(data []ValuableDataByAccount) bool {
	b, e := json.Marshal(data)
	check(e)

	body, e := post(getUploadUrl(), b)
	if check(e) {
		return false
	}

	log.Debug(string(body))
	if string(body) != "OK" {
		return false
	}

	return true
}
