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

	log.Debug(string(b)[1:300])

	client := http.Client{Timeout: time.Duration(10 * time.Second)}
	resp, e := client.Post("http://localhost:9999/ahdb/push", "application/json", bytes.NewBuffer(b))
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
