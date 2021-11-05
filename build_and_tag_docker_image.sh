#!/bin/bash

DIR=`dirname $0`
TARGET=target/docker/
TARGET_DP=${TARGET}climate-change-uk-app/

cd "$DIR"

./mvnw -DskipTests=true clean package &&

mkdir -p ${TARGET_DP} &&

cp version/version.txt $TARGET &&
cp app/target/climate-change-uk-app-*-exec.jar ${TARGET_DP}climate-change-uk-app.jar &&

cp docker/Dockerfile $TARGET

cd $TARGET

docker build -t sik1961/climate-change-uk . &&
docker tag sik1961/climate-change-uk:latest sik1961/climate-change-uk:latest

