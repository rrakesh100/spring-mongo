package com.binimise.admin.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Table(name="acl_sid")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public @Data
class AclSid  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean principal;

    private String sid;
}