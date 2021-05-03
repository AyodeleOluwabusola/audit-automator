package com.audit.automator.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddClientRequest {

    private String companyName;
    private String website;
    private String signerTitle;
    private String firstName;
    private String lastName;
    private String firmFirstAddress;
    private String firmSecondAddress;
    private String country;
    private String city;
    private String region;
    private String postalCode;
    private String signerEmail;
    private String signerName;
    private String phone;
    private String language;
    private String sendEmailToClient;
    private Long auditUserFk;

}
