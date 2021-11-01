#!/bin/bash

DIR=`dirname $0`
TARGET=target/docker/
TARGET_DP=${TARGET}climate-change-uk-app/

cd "$DIR"

mvn -DskipTests=true clean package &&

mkdir -p ${TARGET} &&
mkdir -p ${TARGET_DP} &&

cp version/version.txt $TARGET &&
cp app/target/climate-change-uk-app-*-exec.jar ${TARGET_DP}climate-change-uk-app.jar &&

cp docker/Dockerfile $TARGET


cd $TARGET

sudo docker build -t skusners/climate-change-uk . &&
sudo docker tag skusners/climate-change-uk:latest skusners/climate-change-uk:latest

