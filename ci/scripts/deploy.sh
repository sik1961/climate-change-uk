#!/usr/bin/env bash

if  [ -z "$PAR_ACCOUNT_NUMBER" ]; then
    PAR_ACCOUNT_NUMBER='978628766276';
fi

export CREDS="$(aws sts assume-role --role-arn arn:aws:iam::$PAR_ACCOUNT_NUMBER:role/CiCdSharedRole --role-session-name parcel-type)"
export AWS_SECRET_ACCESS_KEY="$(echo $CREDS | jq .Credentials.SecretAccessKey --raw-output)"
export AWS_ACCESS_KEY_ID="$(echo $CREDS | jq .Credentials.AccessKeyId --raw-output)"
export AWS_SESSION_TOKEN="$(echo $CREDS | jq .Credentials.SessionToken --raw-output)"
export AWS_DEFAULT_REGION=eu-west-1
export TAG="$(cat manifest/manifest.json | jq .\"geofencing\-adapter\" --raw-output)"

cd code/ci/cloudformation/
cfn-create-or-update --capabilities CAPABILITY_NAMED_IAM --template-body file://./climate-change-uk-app.yml \
--stack-name $STACK_NAME \
--parameters ParameterKey=ParGAImageTag,ParameterValue=$TAG \
--parameters ParameterKey=ParDebug,ParameterValue=$PAR_DEBUG \
--parameters ParameterKey=ParAppsStackName,ParameterValue=$PAR_APPS_STACK_NAME \
--parameters ParameterKey=ParDesiredCount,ParameterValue=$PAR_DESIRED_SERVICE_COUNT \
--parameters ParameterKey=ParMemoryReservation,ParameterValue=$PAR_MEMORY_RESERVATION \
--parameters ParameterKey=ParMemoryLimit,ParameterValue=$PAR_MEMORY_LIMIT \
--parameters ParameterKey=ParQasServiceUrl,ParameterValue=$PAR_QAS_SERVICE_URL \
--parameters ParameterKey=ParQasServiceHost,ParameterValue=$PAR_QAS_SERVICE_HOST \
--parameters ParameterKey=ParHostIpAddress,ParameterValue=$PAR_HOST_IP_ADDRESS \
--parameters ParameterKey=ParEnrichedAddressControllerSvocEnabled,ParameterValue=$PAR_ENRICHED_ADDRESS_CONTROLLER_SVOC_ENABLED \
--parameters ParameterKey=ParSimpleSvocControllerSimpleSvocEnabled,ParameterValue=$PAR_SIMPLE_SVOC_CONTROLLER_SIMPLESVOC_ENABLED \
--parameters ParameterKey=ParGeofenceActionOnDeliveryFlag,ParameterValue=$PAR_GEOFENCE_ACTION_ON_DELIVERY_FLAG \
--parameters ParameterKey=ParSvocServiceUrl,ParameterValue=$PAR_SVOC_SERVICE_URL \
--parameters ParameterKey=ParSimpleSvocServiceUrl,ParameterValue=$PAR_SIMPLESVOC_SERVICE_URL \
--parameters ParameterKey=ParWhat3wordsServiceUrl,ParameterValue=$PAR_WHAT3WORDS_SERVICE_URL \
--parameters ParameterKey=ParMediaServiceRoot,ParameterValue=$PAR_MEDIA_SERVICE_ROOT \
--parameters ParameterKey=ParLatLongFallbackService,ParameterValue=$PAR_LATLONG_FALLBACK_SERVICE \
--parameters ParameterKey=ParEnvironment,ParameterValue=$PAR_ENVIRONMENT \
--parameters ParameterKey=ParLogLevel,ParameterValue=$PAR_LOG_LEVEL \
--region eu-west-1 \
--wait
