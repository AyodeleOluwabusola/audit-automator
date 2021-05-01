package com.audit.automator.entities;

import com.audit.automator.utils.Bcrypt;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "AUDIT_USER")
public class AuditUser extends BaseLongPkEntity{

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "EMAIL_ADDRESS")
    private String emailAddress;

    @Column(name = "FIRN_NAME")
    private String firmName;

    @Column(name = "OFFICE_NAME")
    private String officeName;

    @Column(name = "FIRM_FIRST_ADDRESS")
    private String firmFirstAddress;

    @Column(name = "FIRM_SECOND_ADDRESS")
    private String firmSecondAddress;

    private String state;
    private String city;

    @Column(name = "ZIP_CODE")
    private String zipCode;

    @Column(name = "TAX_NUMBER")
    private String taxNumber;

    @Column(name = "COMPANY_PHONE_NUMBER")
    private String companyPhoneNumber;



    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = Bcrypt.hashpw(password, Bcrypt.gensalt());
    }


    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getFirmName() {
        return firmName;
    }

    public void setFirmName(String firmName) {
        this.firmName = firmName;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getFirmFirstAddress() {
        return firmFirstAddress;
    }

    public void setFirmFirstAddress(String firmFirstAddress) {
        this.firmFirstAddress = firmFirstAddress;
    }

    public String getFirmSecondAddress() {
        return firmSecondAddress;
    }

    public void setFirmSecondAddress(String firmSecondAddress) {
        this.firmSecondAddress = firmSecondAddress;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public String getCompanyPhoneNumber() {
        return companyPhoneNumber;
    }

    public void setCompanyPhoneNumber(String companyPhoneNumber) {
        this.companyPhoneNumber = companyPhoneNumber;
    }
}
