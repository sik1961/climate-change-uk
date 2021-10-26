#!/usr/bin/env bash

export TAG="$(cat manifest/manifest.json | jq .\"geofencing\-adapter\" --raw-output)"

cd code
git checkout $TAG
cd ..
./code/ci/scripts/deploy.sh