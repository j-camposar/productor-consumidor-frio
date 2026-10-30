package com.kafka.consumidor.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration 
public class DynamoDbConfig {
    @Value("${aws.region}")
    private String region ;
    @Value("${aws.acces-key-id}")
    private String accesKey;
    @Value("${aws.secret-access-key}")
    private String secretAccessKey;
    @Value("$aws.session-token")
    private String sessionToken;

    @Bean
    public DynamoDbClient dynamoDbClient(){
        AwsSessionCredentials credentials = AwsSessionCredentials
        .create(
           accesKey , secretAccessKey, sessionToken);

        return DynamoDbClient.builder()
        .region(Region.of(region))
        .credentialsProvider(StaticCredentialsProvider.create(credentials))
        .build();
    }

    @Bean 
    public DynamoDbEnhancedClient dynamoDbEnhancedClient(DynamoDbClient dynamoDbClient){
        return DynamoDbEnhancedClient.builder().dynamoDbClient(dynamoDbClient).build();
    }
}
