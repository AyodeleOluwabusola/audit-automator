package com.audit.automator.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "DOCUSIGN_LOG")
@Getter
@Setter
public class DocuSignLog extends BaseLongPkEntity{

    @Column(name = "ENVELOPE_ID")
    private String envelopeId;

    @Column(name = "ERROR_CODE")
    private String errorCode;

    @Column(name = "ERROR_MESSAGE")
    private String message;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "STATUS_DATE_TIME")
    private String statusDateTime;

    @Column(name = "URI")
    private String uri;

    @Column(name = "DOCUMENT_DATA")
    private byte[] documentData;

    @Column(name = "CLIENT_PK")
    private Long clientPk;

}
