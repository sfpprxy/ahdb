package main

import (
	"fmt"
	"strings"
)

func handleCliInput(args []string) {
	fmt.Println(args)
	if len(args) == 0 {
		return
	}

	for _, v := range args {
		if v == "debug" {
			debugMode = true
			fmt.Println("DEBUG MODE ON")
		}
		if v == "remote" {
			remoteMode = true
			fmt.Println("REMOTE MODE ON")
		}
		if strings.Contains(v, "type=") {
			typ := strings.Split(v, "type=")[1]
			fmt.Printf("SCAN_TYPE=%s", typ)
			scanType = typ
		}
	}
}
