package main

import (
	"io/ioutil"
	"strings"
)

func writeItemStats(itemStats string) error {
	//log.Debug("itemStats", itemStats)
	fileByAccount := findSavedVariablesByAccount("AuctionDB.lua")
	log.Debug(fileByAccount)
	var err error
	for a, f := range fileByAccount {
		bytes, e := ioutil.ReadFile(f)
		if check(e) {
			err = e
		}
		log.Debug(a)
		contentStr := string(bytes)
		contentNew := ""

		tag0 := "[\"itemStats\"]"
		tag1 := "},"

		i0 := strings.Index(contentStr, tag0)

		if i0 != -1 {
			i1 := strings.Index(contentStr[i0:], tag1)
			to := i0 + i1 + len(tag1)
			log.Debug("from to ", i0, to)
			target := contentStr[i0:to]
			contentNew = strings.Replace(contentStr, target, itemStats, 1)
		} else {
			i2 := strings.LastIndex(contentStr, "},") + 2
			contentNew = contentStr[:i2] + "\n    " + itemStats + contentStr[i2:]
		}

		e = ioutil.WriteFile(f, []byte(contentNew), 0644)
		if check(e) {
			err = e
		}
	}
	return err
}
