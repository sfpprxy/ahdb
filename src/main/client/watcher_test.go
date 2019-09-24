package main

import (
	_ "github.com/andlabs/ui/winmanifest"
	"testing"
)

func TestFilterChangedTsmFiles(t *testing.T) {
	found := findTsmFiles("/Applications/World of Warcraft/_classic_/WoW.mfil")
	filterChangedTsmFilesByAccount(found)

	//t.Error() // to indicate test failed
}
