package com.example.wordquest.domain.valueObjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class EmailVO {

    @Column(name = "email_address", nullable = false, unique = true) // Change column name to avoid conflict
    private String emailAddress;  // Change field name

    protected EmailVO() {
    }

    public EmailVO(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getValue() {
        return emailAddress;
    }
}