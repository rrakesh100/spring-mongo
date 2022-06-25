package com.binimise.admin.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="acl_object_identity")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public @Data
class AclObjectIdentity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long objectIdClass;

    private String objectIdIdentity;

    private Long parentObject;

    private Long ownerSid;

    private boolean entriesInheriting;
}