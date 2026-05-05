package com.example.aws.domain;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;

import java.math.BigDecimal;

/**
 * Entidad de dominio Product.
 * Mapeada a DynamoDB usando AWS SDK Enhanced.
 */
@DynamoDbBean
public class Product {
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;

    public Product() {}

    public Product(String id, String name, String description, BigDecimal price, Integer stock) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    @DynamoDbPartitionKey
    @DynamoDbAttribute("id")
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    @DynamoDbAttribute("name")
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @DynamoDbAttribute("description")
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @DynamoDbAttribute("price")
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    @DynamoDbAttribute("stock")
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
}

