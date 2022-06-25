package com.binimise.admin.dtos.request;

import lombok.Data;

public @Data
class CreateBookRequest {

    private String name;
    private String author;
    private String price;

}
