#!/usr/bin/env bash

go run ahdbapp.go $(find . -name "*.go" -and -not -name "*_test.go" -and -not -name "ahdbapp.go" -maxdepth 1) "$@"
