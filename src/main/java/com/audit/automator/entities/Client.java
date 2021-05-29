package com.audit.automator.entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CLIENT")
@Getter
@Setter
public class Client extends BaseLongPkEntity{

    @Column(name = "COMPANY_NAME")
    private String companyName;

    @Column(name = "WEBSITE")
    private String website;

    @Column(name = "SIGNER_TITLE")
    private String signerTitle;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "FIRM_FIRST_ADDRESS")
    private String firmFirstAddress;

    @Column(name = "FIRM_SECOND_ADDRESS")
    private String firmSecondAddress;

    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "CITY")
    private String city;

    @Column(name = "REGION")
    private String region;

    @Column(name = "POSTAL_CODE")
    private String postalCode;

    @Column(name = "SIGNER_EMAIL")
    private String signerEmail;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "TAX_ID")
    private String taxId;

    @Column(name = "BANK")
    private String bank;

    @Column(name = "LANGUAGE")
    private String language;

    @Column(name = "SEND_EMAIL_TO_CLIENT")
    private String sendEmailToClient;

    @Column(name = "AUDIT_USER_FK")
    private Long auditUserFk;
}
