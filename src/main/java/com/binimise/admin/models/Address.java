package com.binimise.admin.models;


import lombok.Builder;
import lombok.Data;

@Builder
public @Data
class Address {

    private String addressLine1;

    private String addressLine2;

    private String city;

    private String state;

    private String pin;

}
