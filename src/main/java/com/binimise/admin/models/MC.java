package com.binimise.admin.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="mc")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public @Data class MC extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


//    @OneToMany(targetEntity = Supplier.class, fetch = FetchType.LAZY, mappedBy = "mc")
//    @JsonManagedReference
//    private List<Supplier> suppliers;

    private String gst;

    private String pan;

    private String businessName;

    private String listingName;

    @Type(type = "json")
    @Column(name="address")
    private Address address;

    private String fssai;

    @Type(type = "json")
    @Column(name="poc")
    private POC poc;

}
