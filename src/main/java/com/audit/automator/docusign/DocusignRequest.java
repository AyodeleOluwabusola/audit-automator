package com.audit.automator.docusign;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DocusignRequest {

    private String clientId;
    private String userId;
    private String signerEmail;
    private String signerTitle;
    private String signerName;
    private List<String> scopes;
    private byte[] privateKey;

}
