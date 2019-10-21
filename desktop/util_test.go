package main

import (
	"testing"
	"time"
)

func TestHttpClient(t *testing.T) {
	for {
		getHttpClient()
		time.Sleep(1 * time.Second)
	}
}
