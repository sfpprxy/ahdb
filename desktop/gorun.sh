#!/usr/bin/env bash

go run AHDBApp.go $(find . -name "*.go" -and -not -name "*_test.go" -and -not -name "AHDBApp.go" -maxdepth 1) "$@"
