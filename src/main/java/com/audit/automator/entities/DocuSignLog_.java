package com.audit.automator.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2020-08-03T09:54:44.897+0100")
@StaticMetamodel(DocuSignLog.class)
public class DocuSignLog_ extends BaseLongPkEntity_ {
    public static volatile SingularAttribute<DocuSignLog, String> envelopeId;
    public static volatile SingularAttribute<DocuSignLog, String> errorCode;
    public static volatile SingularAttribute<DocuSignLog, String> message;
    public static volatile SingularAttribute<DocuSignLog, String> status;
    public static volatile SingularAttribute<DocuSignLog, String> statusDateTime;
    public static volatile SingularAttribute<DocuSignLog, String> uri;
    public static volatile SingularAttribute<DocuSignLog, byte[]> documentData;
    public static volatile SingularAttribute<DocuSignLog, Long> clientPk;
}

