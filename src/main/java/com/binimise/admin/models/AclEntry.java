package com.binimise.admin.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="acl_entry")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public @Data
class AclEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long aclObjectIdentity;

    private Integer aceOrder;

    private Long sid;

    private Integer mask;

    private boolean granting;

    private boolean auditSuccess;

    private boolean auditFailure;



}