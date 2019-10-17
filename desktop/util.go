package main

import (
	"runtime"
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
