package com.spring.bioMedical.model;

import javax.persistence.*;

@Entity
@Table(schema= "his", name = "NextOfKinEntity")
public class NextOfKinEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name")
    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    @Column(name = "relation")
    private String relation;

    public String getRelation() {
        return this.relation;
    }

    public void setRelation(String value) {
        this.relation = value;
    }

    @Column(name = "mobileNumber")
    private String mobileNumber;

    public String getMobileNumber() {
        return this.mobileNumber;
    }

    public void setMobileNumber(String value) {
        this.mobileNumber = value;
    }

}
