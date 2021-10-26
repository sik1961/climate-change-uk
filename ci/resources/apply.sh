fly -t hermes set-pipeline -p climate-change-uk -c ../../ci/pipelines/pipeline.yml --var "bitbucket-private-key=$(cat ~/.ssh/concourse)"
