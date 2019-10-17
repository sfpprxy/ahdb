package main

func handleCliInput(args []string) {
	if len(args) == 0 {
		return
	}

	if args[0] == "debug" {
		debugMode = true
		log.Debug("DEBUG MODE ON")
	}
}
