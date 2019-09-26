#!/usr/bin/env bash

go build -o AHDBApp.exe $(find . -name "*.go" -and -not -name "*_test.go" -maxdepth 1)
