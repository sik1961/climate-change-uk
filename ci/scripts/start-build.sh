#!/usr/bin/env bash

set -ex

cd code

PREVIOUS_BUILD=$(git for-each-ref --sort=-taggerdate --format '%(refname)' refs/tags | grep -v release- | head -n2 | tail -n1)
COMMITS=$(git log HEAD --not ${PREVIOUS_BUILD} --format="%H %s - %an" | tail -n +2 | sed -e 's#\([a-f0-9]\{40\}\) \(.*\) - \(.*\)#\[\2\ - \3\]\(https://git.hermescloud.co.uk/pulse-replacement/climate-change-uk/commit/\1\)#' | grep -v '\[release-plugin\]' | grep -v "Merge branch 'develop' of")
COMMITS="${COMMITS//$'\n'/<br />}"
THIS_RELEASE=$(git for-each-ref --sort=-taggerdate --format '%(refname)' refs/tags | grep -v release- | head -n1 | awk -F/ '{print $NF}')
cd ..

echo mkdir messages
echo >> messages/start-build
echo "Started Build<br><br>" >> messages/start-build
echo "Release Version: ${THIS_RELEASE}<br>" >> messages/start-build
echo "---- Commits ----<br>" >> messages/start-build
echo "${COMMITS}" >> messages/start-build

echo Created message:
echo
cat messages/start-build
