package com.binimise.admin.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document(collection = "books")
@Builder
public @Data
class Book {

    @Id
    private String id;
    private String name;
    private String author;
    private String price;
}