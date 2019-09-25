#!/usr/bin/env bash

go run $(find . -name "*.go" -and -not -name "*_test.go" -maxdepth 1)
