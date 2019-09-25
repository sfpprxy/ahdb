package main

import (
	_ "github.com/andlabs/ui/winmanifest"
	"testing"
)

var winPath = "D:\\Game\\World of Warcraft\\_classic_\\Wow.exe"
var macPath = "/Applications/World of Warcraft/_classic_/WoW.mfil"

func TestFilterChangedTsmFiles(t *testing.T) {

	path := winPath
	if isOnMac() {
		path = macPath
	}

	found := findTsmFiles(path)
	filterChangedTsmFilesByAccount(found)
	//t.Error() // to indicate test failed
}

func TestSaveReadWowPath(t *testing.T) {
	saveWowPath(winPath)
	path := readWowPath()
	log.Debug(path)
}
