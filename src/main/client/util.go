package main

import (
	"runtime"
)

func check(args ...interface{}) bool {
	if args[0] != nil {
		log.Error(args...)
		return true
	}
	return false
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
