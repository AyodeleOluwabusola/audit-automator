package com.audit.automator.docusign;

import com.audit.automator.entities.DocuSignLog;
import com.audit.automator.enums.DocuSignStatusEnum;
import com.audit.automator.repository.DataRepository;
import com.audit.automator.utils.ProxyUtil;
import com.docusign.esign.api.EnvelopesApi;
import com.docusign.esign.model.Document;
import com.docusign.esign.model.EnvelopeDefinition;
import com.docusign.esign.model.EnvelopeSummary;
import com.docusign.esign.model.Recipients;
import com.docusign.esign.model.Signer;
import com.migcomponents.migbase64.Base64;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class DocuSignEmailSender {

    private static final String SignTest1File = "/docusign/World_Wide_Corp_fields.pdf";
    private static final String BaseUrl = "https://demo.docusign.net/restapi";

    @Autowired
    DataRepository dataRepository;

    @Autowired
    ProxyUtil proxyUtil;

    public boolean sendEmail(DocusignRequest request, Long clientPk){

        byte[] fileBytes = null;
        try {
            String currentDir = System.getProperty("user.dir");

            Path path = Paths.get(currentDir + SignTest1File);
            fileBytes = Files.readAllBytes(path);
        } catch (IOException ioExcp) {
            ioExcp.printStackTrace();
        }

        EnvelopeDefinition envDef = new EnvelopeDefinition();
        envDef.setEmailSubject("Please Sign My Sample Document");
        envDef.setEmailBlurb("Hello, Please Sign My Sample Document.");

        // add a document to the envelope
        Document doc = new Document();
        String base64Doc = Base64.encodeToString(fileBytes, false);
        doc.setDocumentBase64(base64Doc);
        doc.setName("TestFile.pdf");
        doc.setDocumentId("1");

        List<Document> docs = new ArrayList<Document>();
        docs.add(doc);
        envDef.setDocuments(docs);

        // Add a recipient to sign the document

        Signer signer = new Signer();
        signer.setEmail(request.getSignerEmail());
        signer.setName(request.getSignerName());
        signer.setRecipientId("1");

        // Above causes issue
        envDef.setRecipients(new Recipients());
        envDef.getRecipients().setSigners(new ArrayList<Signer>());
        envDef.getRecipients().getSigners().add(signer);

        // send the envelope (otherwise it will be "created" in the Draft folder
        envDef.setStatus(DocuSignStatusEnum.SENT.getName());

        try {
            String accountId = new DocuSignAuth(dataRepository).getDocuSignAccountId();
            EnvelopesApi envelopesApi = new EnvelopesApi();

            EnvelopeSummary envelopeSummary = envelopesApi.createEnvelope(accountId, envDef);
            System.out.println("ENVELOPE SUMMARY IS {}"+ envelopeSummary);
            System.out.println("ENVELOPE ID IS {}"+ envelopeSummary.getEnvelopeId());

            byte[] envelope = envelopesApi.getDocument(accountId, envelopeSummary.getEnvelopeId(), "combined");

            System.out.println("ENVELOPE SIZE {}"+ envelope.length);
            if(envelopeSummary != null) {
                DocuSignLog docuSignLog = new DocuSignLog();
                docuSignLog.setEnvelopeId(envelopeSummary.getEnvelopeId());
                if (envelopeSummary.getErrorDetails() != null) {
                    docuSignLog.setErrorCode(envelopeSummary.getErrorDetails().getErrorCode());
                    docuSignLog.setMessage(envelopeSummary.getErrorDetails().getMessage());
                }
                docuSignLog.setUri(envelopeSummary.getUri());
                docuSignLog.setStatusDateTime(envelopeSummary.getStatusDateTime());
                docuSignLog.setStatus(envelopeSummary.getStatus().toUpperCase());
                docuSignLog.setClientPk(clientPk);
                docuSignLog.setDocumentData(envelope);

                proxyUtil.executeWithNewTransaction(()-> dataRepository.create(docuSignLog));

                if (envelopeSummary != null) {
                    return envelopeSummary.getStatus().equals("sent");
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


}