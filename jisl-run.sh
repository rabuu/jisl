#!/bin/sh

set -xe

ARGS="$(echo "$@")"
mvn clean compile exec:java -Dexec.args="$ARGS"
