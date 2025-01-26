#!/bin/bash

if [ -z "$1" ]; then
  echo "Usage: $0 <message>"
  exit 1
fi

message=$1
hostname="localhost"
port=8888

# Send the message using nc (Netcat)
echo "$message" | nc -q 0 $hostname $port