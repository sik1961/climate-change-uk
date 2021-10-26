#!/usr/bin/env bash

export CREDS="$(aws sts assume-role --role-arn arn:aws:iam::978628766276:role/CiCdSharedRole --role-session-name climate-change-uk)"
export AWS_SECRET_ACCESS_KEY="$(echo $CREDS | jq .Credentials.SecretAccessKey --raw-output)"
export AWS_ACCESS_KEY_ID="$(echo $CREDS | jq .Credentials.AccessKeyId --raw-output)"
export AWS_SESSION_TOKEN="$(echo $CREDS | jq .Credentials.SessionToken --raw-output)"
export AWS_DEFAULT_REGION=eu-west-1

export PAR_WSDL_PORT="$(aws cloudformation describe-stacks --stack-name pr-nonprod-dev-hh | jq '.Stacks[].Outputs[] | select(.OutputKey=="WiremockPort").OutputValue' --raw-output)"
export PAR_WSDL_HOST="$(aws cloudformation describe-stacks --stack-name pr-nonprod-dev-hh | jq '.Stacks[].Outputs[] | select(.OutputKey=="HttpLoadBalancerAddress").OutputValue' --raw-output)"
export PAR_QAS_SERVICE_URL="http://"$PAR_WSDL_HOST":"$PAR_WSDL_PORT"/qasaddresscleanse.5.3.16/QASAddressCleanseService.svc?singleWsdl"

cd code
export DOCKER_TAG=$(./mvnw org.apache.maven.plugins:maven-help-plugin:2.1.1:evaluate -Dexpression=project.version | grep -v '\[' | grep -v Download | tail -1)
cd ci/cloudformation/
cfn-create-or-update --capabilities CAPABILITY_NAMED_IAM --template-body file://./climate-change-uk-app.yml \
--stack-name pr-nonprod-dev-climate-change-uk \
--parameters ParameterKey=ParGAImageTag,ParameterValue=$DOCKER_TAG \
--parameters ParameterKey=ParQasServiceUrl,ParameterValue=$PAR_QAS_SERVICE_URL \
--parameters ParameterKey=ParMediaServiceRoot,ParameterValue='http://enterprise-services.dev.hermescloud.co.uk/media-service/v1' \
--parameters ParameterKey=ParSvocServiceUrl,ParameterValue='https://svoc-esb-01.dev.hermescloud.co.uk/services/goldenaddresses' \
--parameters ParameterKey=ParWhat3wordsServiceUrl,ParameterValue='https://what3words-api.dev.hermescloud.co.uk/v3/convert-to-coordinates' \
--parameters ParameterKey=ParLatLongFallbackService,ParameterValue='QAS' \
--parameters ParameterKey=ParEnvironment,ParameterValue='DEV' \
--region eu-west-1 \
--wait

export jirapassword="${JIRA_PASSWORD}"
export date=$(date +%d/%b/%Y)
curl -u concourse_ci:$jirapassword -d "{\"description\":\"Created by deployment script when releasing to dev environment\", \"name\":\"climate-change-uk-$DOCKER_TAG\", \"project\":\"PRP\", \"userStartDate\":\"$date\"}" -H "Content-Type: application/json" -X POST https://jira.hermescloud.co.uk/rest/api/latest/version
