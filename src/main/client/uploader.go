package main

import (
	"bytes"
	"encoding/json"
	"io/ioutil"
	"net/http"
	"time"
)

func upload(data []ValuableDataByAccount) bool {
	b, e := json.Marshal(data)
	check(e)

	client := http.Client{Timeout: time.Duration(10 * time.Second)}
	resp, e := client.Post(getUploadUrl(), "application/json", bytes.NewBuffer(b))
	if check(e) {
		return false
	}

	body, e := ioutil.ReadAll(resp.Body)
	check(e)
	if string(body) != "OK" {
		return false
	}

	return true
}
