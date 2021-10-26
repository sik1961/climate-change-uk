#!/usr/bin/env bash

cd $(dirname $0)/../.. &&
java -version &&
./mvnw -s /settings.xml -DskipTests=true package &&
mkdir -p ../app/climate-change-uk-app &&
cp target/version.txt ../app/ &&
cp app/target/climate-change-uk-app-$(cat ../app/version.txt)-exec.jar ../app/climate-change-uk-app/climate-change-uk-app.jar &&
cp docker/Dockerfile ../app/