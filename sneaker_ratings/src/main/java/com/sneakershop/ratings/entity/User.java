package com.sneakershop.ratings.entity;

import javax.persistence.*;

@Entity
@Table(name="users")
public class User {

    @Id
    @Column(name="id")
    private Integer id;

    @Column(name="first_name")
    private String firstName;

    public User(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id=id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName=firstName;
    }

}