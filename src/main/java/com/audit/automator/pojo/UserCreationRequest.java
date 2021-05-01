package com.audit.automator.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserCreationRequest {

    //Company's profile
    private String firmName;
    private String officeName;
    private String firmFirstAddress;
    private String firmSecondAddress;
    private String state;
    private String city;
    private String zipCode;
    private String companyPhoneNumber;
    private String taxNumber;

    //User's profile
    private String jobTitle;
    private String firstName;
    private String lastName;
    private String email;
    private String countryCode;
    private String userPhoneNo;
    private String requestedUserId;


    public UserCreationRequest(String firmName, String officeName, String firmFirstAddress, String firmSecondAddress, String state, String city, String zipCode, String companyPhoneNumber, String taxNumber) {
        this.firmName = firmName;
        this.officeName = officeName;
        this.firmFirstAddress = firmFirstAddress;
        this.firmSecondAddress = firmSecondAddress;
        this.state = state;
        this.city = city;
        this.zipCode = zipCode;
        this.companyPhoneNumber = companyPhoneNumber;
        this.taxNumber = taxNumber;
    }
}
