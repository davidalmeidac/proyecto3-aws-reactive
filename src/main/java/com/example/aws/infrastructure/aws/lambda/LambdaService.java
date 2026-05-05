package com.example.aws.infrastructure.aws.lambda;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.lambda.LambdaAsyncClient;
import software.amazon.awssdk.services.lambda.model.InvocationType;
import software.amazon.awssdk.services.lambda.model.InvokeRequest;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

/**
 * Servicio para invocar AWS Lambda functions.
 * Demuestra integración reactiva con AWS Lambda.
 */
@Service
public class LambdaService {

    private final LambdaAsyncClient lambdaClient;
    private final String functionName;

    public LambdaService(@Value("${aws.lambda.function.name:process-order}") String functionName,
                        @Value("${aws.region:us-east-1}") String region) {
        this.functionName = functionName;
        this.lambdaClient = LambdaAsyncClient.builder()
            .region(Region.of(region))
            .credentialsProvider(DefaultCredentialsProvider.create())
            .build();
    }

    public Mono<String> invokeProcessOrder(String orderData) {
        InvokeRequest request = InvokeRequest.builder()
            .functionName(functionName)
            .invocationType(InvocationType.REQUEST_RESPONSE)
            .payload(SdkBytes.fromString(orderData, StandardCharsets.UTF_8))
            .build();

        return Mono.fromFuture(
            lambdaClient.invoke(request)
                .thenApply(response -> 
                    response.payload().asString(StandardCharsets.UTF_8)
                )
        );
    }
}

