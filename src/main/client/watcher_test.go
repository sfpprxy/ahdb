package main

import (
	_ "github.com/andlabs/ui/winmanifest"
	"testing"
)

func TestFilterChangedTsmFiles(t *testing.T) {
	winPath := "D:\\Game\\World of Warcraft\\_classic_\\Wow.exe"
	macPath := "/Applications/World of Warcraft/_classic_/WoW.mfil"
	log.Debug("path: ", winPath, macPath)

	found := findTsmFiles(winPath)
	filterChangedTsmFilesByAccount(found)
	//t.Error() // to indicate test failed
}
