package com.example.aws.infrastructure.aws.dynamodb;

import com.example.aws.domain.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.PagePublisher;

import java.util.concurrent.CompletableFuture;

/**
 * Repositorio reactivo para DynamoDB.
 * Demuestra programación reactiva con Project Reactor.
 */
@Repository
public class ProductRepository {

    private final DynamoDbAsyncTable<Product> productTable;

    public ProductRepository(DynamoDbEnhancedAsyncClient enhancedClient,
                           @Value("${aws.dynamodb.table.products:products}") String tableName) {
        this.productTable = enhancedClient.table(tableName, TableSchema.fromBean(Product.class));
    }

    public Mono<Product> save(Product product) {
        return Mono.fromFuture(
            CompletableFuture.supplyAsync(() -> 
                productTable.putItem(product).join()
            )
        ).thenReturn(product);
    }

    public Mono<Product> findById(String id) {
        Product key = new Product();
        key.setId(id);
        
        return Mono.fromFuture(
            CompletableFuture.supplyAsync(() -> 
                productTable.getItem(key).join()
            )
        );
    }

    public Flux<Product> findAll() {
        PagePublisher<Product> pages = productTable.scan();
        return Flux.from(pages)
            .flatMap(page -> Flux.fromIterable(page.items()));
    }

    public Mono<Void> delete(String id) {
        Product key = new Product();
        key.setId(id);
        
        return Mono.fromFuture(
            CompletableFuture.supplyAsync(() -> {
                productTable.deleteItem(key).join();
                return null;
            })
        ).then();
    }
}

