#!/usr/bin/env bash

if [ -z "$1" ]; then
  targets=( 'servlet' 'springboot' 'sczuul' )
else
  targets=( "$1" )
fi

command -v scms >/dev/null 2>&1 || { echo >&2 "The docs/build.sh requires scms to be installed.  Aborting."; exit 1; }
command -v sphinx-build >/dev/null 2>&1 || { echo >&2 "The docs/build.sh requires sphinx to be installed.  Aborting."; exit 1; }

for target in "${targets[@]}"; do
  scms -e "$target" "build/$target"
  cd "build/$target"
  sphinx-build -n -b html -E -d build/doctrees source -t "$target" build/html
  cd ../..
done