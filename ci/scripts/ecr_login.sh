#!/usr/bin/env bash

apk update
apk add py-pip
pip install --upgrade pip
pip install awscli
$(aws ecr get-login --region=eu-west-1)

