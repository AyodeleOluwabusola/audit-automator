package com.audit.automator.docusign;

import com.audit.automator.repository.DataRepository;
import com.docusign.esign.client.ApiClient;
import com.docusign.esign.client.Configuration;
import com.docusign.esign.client.auth.OAuth;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class DocuSignAuth {

    final DataRepository dataRepository;

    public DocuSignAuth(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public String getDocuSignAccountId(){

        List<String> scopes = new ArrayList<>();
        scopes.add(OAuth.Scope_SIGNATURE);

        ApiClient apiClient = new ApiClient("https://demo.docusign.net/restapi");
        try {
            String privateKeyFullPath = System.getProperty("user.dir") + "/docusign/privatekey.txt";
            byte[] bytes = Files.readAllBytes(Paths.get(privateKeyFullPath));

            OAuth.OAuthToken oAuthToken = apiClient.requestJWTUserToken("25d82be6-812f-461d-b9ad-796b053a67ba", "b1efbcac-d235-4d6a-9c81-f9afc8e41c4b", scopes, bytes,
                    3600);
            // now that the API client has an OAuth token, let's use it in all
            // DocuSign APIs
            apiClient.setAccessToken(oAuthToken.getAccessToken(), oAuthToken.getExpiresIn());
            OAuth.UserInfo userInfo = apiClient.getUserInfo(oAuthToken.getAccessToken());

            apiClient.setBasePath(userInfo.getAccounts().get(0).getBaseUri() + "/restapi");
            Configuration.setDefaultApiClient(apiClient);
            String accountId = userInfo.getAccounts().get(0).getAccountId();

            return accountId;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
