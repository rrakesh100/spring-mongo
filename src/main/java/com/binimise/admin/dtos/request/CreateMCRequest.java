package com.binimise.admin.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public @Data  class CreateMCRequest {

    @NotBlank
    @Size(max = 20)
    private String gst;

    @NotBlank
    @Size(min=6, max = 20)
    private String pan;

    @JsonProperty("business_name")
    private String businessName;

    @JsonProperty("listing_name")
    private String listingName;

    @JsonProperty("address_line1")
    private String addressLine1;

    @JsonProperty("address_line2")
    private String addressLine2;

    @JsonProperty("city")
    private String city;

    @JsonProperty("state")
    private String state;

    @JsonProperty("pin")
    private String pin;

    @JsonProperty("fssai")
    private String fssai;

    @JsonProperty("poc_name")
    private String pocName;

    @JsonProperty("poc_phone_number")
    private String pocPhoneNumber;

    @JsonProperty("alternate_poc_name")
    private String alternatePocName;

    @JsonProperty("alternate_poc_phone_number")
    private String alternatePocNumber;


}
