package main

import (
	"bytes"
	"encoding/json"
	"io/ioutil"
	"net/http"
	"time"
)

func upload(data []ValuableDataByAccount) bool {
	// todo impl
	b, e := json.Marshal(data)
	check(e)

	log.Debug(string(b)[1:300])

	client := http.Client{Timeout: time.Duration(3 * time.Second)}
	resp, e := client.Post("http://localhost:9999", "application/json", bytes.NewBuffer(b))
	if check(e) {
		return false
	}

	body, e := ioutil.ReadAll(resp.Body)
	check(e)
	log.Debug(body)

	return false
}
