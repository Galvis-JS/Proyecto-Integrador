#!/bin/bash

echo "spring.datasource.username=${DB_USERNAME}" > src/main/resources/application-secrets.properties
echo "spring.datasource.password=${DB_PASSWORD}" >> src/main/resources/application-secrets.properties
echo "aws.access.key.id=${AWS_ACCESS_KEY_ID}" >> src/main/resources/application-secrets.properties
echo "aws.secret.access.key=${AWS_SECRET_ACCESS_KEY}" >> src/main/resources/application-secrets.properties
echo "aws.s3.region=${AWS_DEFAULT_REGION}" >> src/main/resources/application-secrets.properties
echo "aws.s3.bucket.name=${AWS_BUCKET_NAME}" >> src/main/resources/application-secrets.properties