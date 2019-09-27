#!/usr/bin/env bash

go build -ldflags="-H windowsgui" -o AHDBApp.exe $(find . -name "*.go" -and -not -name "*_test.go" -maxdepth 1)
