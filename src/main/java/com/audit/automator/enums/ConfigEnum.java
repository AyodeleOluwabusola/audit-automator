package com.audit.automator.enums;


import lombok.Getter;

@Getter
public enum ConfigEnum {

    DOCUSIGN_CLIENT_ID("DOCUSIGN-CLIENT-ID", "", "DocuSign ClientID/ Integration key make Docusign esignature request"),
    DOCUSIGN_USER_ID("DOCUSIGN-USER-ID", "", "DocuSign User ID used to make Docusign esignature request"),
    DOCUSIGN_ACCOUNT_ID("DOCUSIGN-ACCOUNT-ID", "", "DocuSign Account ID used to make Docusign esignature request"),
    SMTP_USERNAME("SMTP-USERNAME", "", "Username for Email Authentication"),
    SMTP_PASSWORD("SMTP-PASSWORD", "", "Password for Email Authentication"),
    SMTP_PORT("SMTP-PORT", "587", "Port for Email Authentication"),
    SMTP_HOSTNAME("SMTP-HOSTNAME", "", "Hostname for Email Authentication"),
    BATCH_SIZE("BATCH-SIZE", "10", "DocuSign User ID used to make Docusign esignature request"),
    DOCUSIGN_REQUERY_INTERVAL("DOCUSIGN-REQUERY-INTERVAL", "1800000", "DocuSign User ID used to make Docusign esignature request"),
    DOCUSIGN_REQUERY_DURATION("DOCUSIGN-REQUERY-DURATION", "86400000", "The barring period shall be controlled by a setting with a default value of 24 hours measured in milliseconds"),

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
