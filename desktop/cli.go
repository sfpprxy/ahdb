package main

func handleCliInput(args []string) {
	if len(args) == 0 {
		return
	}

	for _, v := range args {
		if v == "debug" {
			debugMode = true
			log.Debug("DEBUG MODE ON")
		}
		if v == "remote" {
			remoteMode = true
			log.Debug("REMOTE MODE ON")
		}
	}
}
