package com.colak;

import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

import java.net.URI;

@Slf4j
class SQSTest {

    private SqsClient sqsClient;

    public static void main() {
        SQSTest SQSTest = new SQSTest();
        SQSTest.connect();
    }

    private void connect() {
        AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create("test", "test");
        StaticCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(awsBasicCredentials);

        sqsClient = SqsClient.builder()
                // Region doesn't matter for LocalStack
                .region(Region.EU_CENTRAL_1)
                .endpointOverride(URI.create("http://localhost:4566"))
                .credentialsProvider(credentialsProvider)
                .build();
        log.info("Connected successfully");
    }


}