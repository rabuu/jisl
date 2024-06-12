#!/bin/sh

set -xe

mvn clean compile exec:java -Dexec.args=\'"$@"\'
