#! /bin/bash

SCMS_VERSION="0.2.0"

mkdir -p ~/usr/local/scms
curl -s "http://repo.maven.apache.org/maven2/com/leshazlewood/scms/scms/$SCMS_VERSION/scms-$SCMS_VERSION.zip" -o scms.zip
unzip scms.zip -d ~/usr/local/scms
ln -s "~/usr/local/scms/scms-$SCMS_VERSION" ~/usr/local/scms/current
export PATH="~/usr/local/scms/current/bin:$PATH"
