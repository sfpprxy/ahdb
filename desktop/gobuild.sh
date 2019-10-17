#!/usr/bin/env bash

go build -ldflags="-H windowsgui" -o AHDBApp.exe $(find . -name "*.go" -and -not -name "*_test.go")

./rcedit-x64.exe "AHDBApp.exe" --set-version-string FileDescription "拍卖数据库"
./rcedit-x64.exe "AHDBApp.exe" --set-version-string ProductName "拍卖数据库"

./rcedit-x64.exe "AHDBApp.exe" --set-product-version "0.0.2.1"
./rcedit-x64.exe "AHDBApp.exe" --set-file-version "0.0.2.1"

./rcedit-x64.exe "AHDBApp.exe" --set-icon "ahdb.ico"
