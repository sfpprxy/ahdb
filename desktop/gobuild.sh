#!/usr/bin/env bash

go build -ldflags="-H windowsgui" -o 地精传送器.exe $(find . -name "*.go" -and -not -name "*_test.go")

./rcedit-x64.exe "地精传送器.exe" --set-version-string FileDescription "地精传送器"
./rcedit-x64.exe "地精传送器.exe" --set-version-string ProductName "地精传送器"

./rcedit-x64.exe "地精传送器.exe" --set-product-version "1.1.0"
./rcedit-x64.exe "地精传送器.exe" --set-file-version "1.1.0"

./rcedit-x64.exe "地精传送器.exe" --set-icon "ahdb.ico"
