#!/bin/bash

systemctl stop ahdb.service
systemctl disable ahdb.service
rm /lib/systemd/system/ahdb.service
systemctl daemon-reload
systemctl reset-failed
