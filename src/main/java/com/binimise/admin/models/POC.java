package com.binimise.admin.models;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
public @Data
class POC {

    private String name;

    private String phoneNumber;

    private List<POC> alternates;

}
