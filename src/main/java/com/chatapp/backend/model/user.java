package com.chatapp.backend.model;

import org.springframework.data.annotation.Id;

public class user {

    @Id
    public String id;

    public String firstName;
    public String lastName;

    public user() {
    }

    public user(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%s, firstName='%s', lastName='%s']",
                id, firstName, lastName);
    }
}