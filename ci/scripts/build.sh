#!/usr/bin/env bash

START_DOCKER=$(realpath $(dirname $0)/start_docker.sh)
STOP_DOCKER=$(realpath $(dirname $0)/stop_docker.sh)
ECR_LOGIN=$(realpath $(dirname $0)/ecr_login.sh)

${START_DOCKER}

${ECR_LOGIN}

echo '10.128.33.182    win-v0bpsvt8lve' >> /etc/hosts

cd $(dirname $0)/../.. &&
ls .mvn/wrapper/ &&
java -version &&
./mvnw -s /settings.xml verify site

cat /etc/hosts

TEST_EXIT_CODE=$?

cd app/target &&
apk update
apk add py-pip
pip install --upgrade pip
pip install awscli
$(aws ecr get-login --region=eu-west-1 --no-include-email | sh $1)
aws s3 rm "s3://pulse-replacement-test-reports/Integration/climate-change-uk/" --recursive
aws s3 cp climate-change-uk-app-*test-report.zip "s3://pulse-replacement-test-reports/Integration/climate-change-uk/"

${STOP_DOCKER}

exit $TEST_EXIT_CODE