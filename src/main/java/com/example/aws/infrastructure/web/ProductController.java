package com.example.aws.infrastructure.web;

import com.example.aws.application.ProductService;
import com.example.aws.domain.Product;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

/**
 * Controlador REST reactivo.
 * Demuestra Spring WebFlux y programación reactiva.
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public Mono<ResponseEntity<Product>> createProduct(@Valid @RequestBody CreateProductRequest request) {
        return productService.createProduct(
            request.name(),
            request.description(),
            request.price(),
            request.stock()
        )
        .map(product -> ResponseEntity.status(HttpStatus.CREATED).body(product));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Product>> getProduct(@PathVariable String id) {
        return productService.getProduct(id)
            .map(ResponseEntity::ok)
            .onErrorResume(error -> Mono.just(ResponseEntity.notFound().build()));
    }

    @GetMapping
    public Flux<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Product>> updateProduct(
            @PathVariable String id,
            @Valid @RequestBody UpdateProductRequest request) {
        return productService.updateProduct(
            id,
            request.name(),
            request.description(),
            request.price(),
            request.stock()
        )
        .map(ResponseEntity::ok)
        .onErrorResume(error -> Mono.just(ResponseEntity.notFound().build()));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteProduct(@PathVariable String id) {
        return productService.deleteProduct(id)
            .then(Mono.just(ResponseEntity.noContent().<Void>build()))
            .onErrorResume(error -> Mono.just(ResponseEntity.notFound().build()));
    }

    public record CreateProductRequest(
        @NotBlank String name,
        String description,
        @DecimalMin("0.0") BigDecimal price,
        @Min(0) Integer stock
    ) {}

    public record UpdateProductRequest(
        @NotBlank String name,
        String description,
        @DecimalMin("0.0") BigDecimal price,
        @Min(0) Integer stock
    ) {}
}

