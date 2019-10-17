#!/bin/bash

cp ahdb.service /lib/systemd/system/ahdb.service
systemctl enable --now ahdb.service
