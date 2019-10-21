package main

import (
	"bytes"
	"io/ioutil"
	"net/http"
	"runtime"
	"time"
)

var debugMode = false

func check(args ...interface{}) bool {
	if args[0] != nil {
		log.Error(args...)
		return true
	}
	return false
}

func onDebug() bool {
	return debugMode
}

func isOnMac() bool {
	platform := runtime.GOOS
	switch platform {
	case "windows":
		return false
	case "darwin":
		return true
	}
	return false
}

func isOnWin() bool {
	platform := runtime.GOOS
	switch platform {
	case "windows":
		return true
	case "darwin":
		return false
	}
	return false
}

var httpc *http.Client

func getHttpClient() *http.Client {
	if httpc == nil {
		httpc = &http.Client{Timeout: time.Duration(10 * time.Second)}
	}
	return httpc
}

func post(url string, b []byte) ([]byte, error) {
	resp, err := getHttpClient().Post(url, "application/json", bytes.NewBuffer(b))
	check(err)
	body, err1 := ioutil.ReadAll(resp.Body)
	return body, err1
}

func get(url string) ([]byte, error) {
	resp, err := getHttpClient().Get(url)
	check(err)
	body, err1 := ioutil.ReadAll(resp.Body)
	return body, err1
}
