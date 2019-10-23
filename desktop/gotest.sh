#!/usr/bin/env bash

go test $(find . -name "*.go" -and -not -name "*_test.go" -maxdepth 1) "$@" -v
