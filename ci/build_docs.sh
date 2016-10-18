#!/bin/bash

source ./ci/common.sh

if command -v scms >/dev/null 2>&1; then
  export PATH="~/usr/local/scms/current/bin:$PATH"
fi

info "Generating guides..."
git submodule init
git submodule update
(cd docs && ./build.sh &> $WORKDIR/target/guides.log) &
PID=$!
show_spinner "$PID"

wait $PID ## sets exit code from command
EXIT_CODE=$?

if [ "$EXIT_CODE" -ne 0 ]; then
  error "Error generating guides"
  cat $WORKDIR/target/guides.log
  exit $EXIT_CODE
fi

info "Generating JavaDocs..."
(mvn -q javadoc:aggregate -P travis-docs &> $WORKDIR/target/javadocs.log) &
PID=$!

show_spinner "$PID"

wait $PID ## sets exit code from command
EXIT_CODE=$?

if [ "$EXIT_CODE" -ne 0 ]; then
  error "Error generating JavaDocs"
  cat $WORKDIR/target/javadocs.log
  exit $EXIT_CODE
fi
