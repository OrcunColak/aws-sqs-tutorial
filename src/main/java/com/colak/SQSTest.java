package com.colak;

import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

import java.net.URI;

@Slf4j
class SQSTest {

    private SqsClient sqsClient;

    private static final String localStackEndpoint = "http://localhost:4566";

    // Queue URL for LocalStack
    private static final String queueUrl = "http://sqs.eu-central-1.localhost.localstack.cloud:4566/000000000000/test-queue";

    public static void main() {
        SQSTest sqsTest = new SQSTest();
        try {
            sqsTest.connect();
            sqsTest.sendMessage();
        } finally {
            sqsTest.close();
        }
    }

    private void close() {
        if (sqsClient != null) {
            sqsClient.close();
        }
    }

    private void connect() {
        AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create("test", "test");
        StaticCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(awsBasicCredentials);

        sqsClient = SqsClient.builder()
                // Region doesn't matter for LocalStack
                .region(Region.EU_CENTRAL_1)
                .endpointOverride(URI.create(localStackEndpoint))
                .credentialsProvider(credentialsProvider)
                .build();
        log.info("Connected successfully");
    }

    private void sendMessage() {

        String messageBody = "Hello from SQS!";

        // Send a message
        SendMessageRequest sendMessageRequest = SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(messageBody)
                .delaySeconds(0) // Optional delay in seconds
                .build();

        SendMessageResponse sendMessageResponse = sqsClient.sendMessage(sendMessageRequest);

        log.info("Message sent with ID: {}", sendMessageResponse.messageId());

    }

}