#!/bin/sh
PID=$(cat stop.pid)
kill -9 $PID      
