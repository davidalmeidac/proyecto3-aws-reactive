package com.example.aws.application;

import com.example.aws.domain.Product;
import com.example.aws.infrastructure.aws.dynamodb.ProductRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Servicio de productos.
 * Demuestra programación reactiva y funcional con Project Reactor.
 */
@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Mono<Product> createProduct(String name, String description, 
                                      BigDecimal price, Integer stock) {
        Product product = new Product(
            UUID.randomUUID().toString(),
            name,
            description,
            price,
            stock
        );
        return repository.save(product);
    }

    public Mono<Product> getProduct(String id) {
        return repository.findById(id)
            .switchIfEmpty(Mono.error(new RuntimeException("Producto no encontrado")));
    }

    public Flux<Product> getAllProducts() {
        return repository.findAll();
    }

    public Mono<Product> updateProduct(String id, String name, String description,
                                      BigDecimal price, Integer stock) {
        return repository.findById(id)
            .flatMap(product -> {
                product.setName(name);
                product.setDescription(description);
                product.setPrice(price);
                product.setStock(stock);
                return repository.save(product);
            })
            .switchIfEmpty(Mono.error(new RuntimeException("Producto no encontrado")));
    }

    public Mono<Void> deleteProduct(String id) {
        return repository.delete(id);
    }
}

