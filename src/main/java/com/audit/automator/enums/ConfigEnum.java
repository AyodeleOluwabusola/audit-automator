package com.audit.automator.enums;


import lombok.Getter;

@Getter
public enum ConfigEnum {

    DOCUSIGN_CLIENT_ID("DOCUSIGN-CLIENT-ID", "25d82be6-812f-461d-b9ad-796b053a67ba", "DocuSign ClientID/ Integration key make Docusign esignature request"),
    DOCUSIGN_USER_ID("DOCUSIGN-USER-ID", "b1efbcac-d235-4d6a-9c81-f9afc8e41c4b", "DocuSign User ID used to make Docusign esignature request"),
        ;

    ConfigEnum(String name, String value, String description) {
        this.name = name;
        this.value = value;
        this.description = description;
    }

    private String name;
    private String value;
    private String description;
}
