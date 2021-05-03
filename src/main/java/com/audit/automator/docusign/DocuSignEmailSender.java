package com.audit.automator.docusign;

import com.docusign.esign.api.EnvelopesApi;
import com.docusign.esign.client.ApiClient;
import com.docusign.esign.client.ApiException;
import com.docusign.esign.client.Configuration;
import com.docusign.esign.client.auth.OAuth.OAuthToken;
import com.docusign.esign.client.auth.OAuth.UserInfo;
import com.docusign.esign.model.Document;
import com.docusign.esign.model.EnvelopeDefinition;
import com.docusign.esign.model.EnvelopeSummary;
import com.docusign.esign.model.Recipients;
import com.docusign.esign.model.Signer;
import com.migcomponents.migbase64.Base64;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DocuSignEmailSender {

    private static final String SignTest1File = "/docusign/World_Wide_Corp_fields.pdf";
    private static final String BaseUrl = "https://demo.docusign.net/restapi";

    public static boolean sendEmail(DocusignRequest request){

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
        envDef.setStatus("sent");

        ApiClient apiClient = new ApiClient(BaseUrl);
        try {
            OAuthToken oAuthToken = apiClient.requestJWTUserToken(request.getClientId(), request.getUserId(), request.getScopes(), request.getPrivateKey(),
                    3600);
            // now that the API client has an OAuth token, let's use it in all
            // DocuSign APIs
            apiClient.setAccessToken(oAuthToken.getAccessToken(), oAuthToken.getExpiresIn());
            UserInfo userInfo = apiClient.getUserInfo(oAuthToken.getAccessToken());

            apiClient.setBasePath(userInfo.getAccounts().get(0).getBaseUri() + "/restapi");
            Configuration.setDefaultApiClient(apiClient);
            String accountId = userInfo.getAccounts().get(0).getAccountId();

            EnvelopesApi envelopesApi = new EnvelopesApi();

            EnvelopeSummary envelopeSummary = envelopesApi.createEnvelope(accountId, envDef);

            if(envelopeSummary != null){
                return envelopeSummary.getStatus().equals("sent");
            }
            return false;
        } catch (ApiException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


}