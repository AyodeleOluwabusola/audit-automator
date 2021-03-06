package com.audit.automator.service;

import com.audit.automator.docusign.DocuSignEmailSender;
import com.audit.automator.docusign.DocusignRequest;
import com.audit.automator.entities.AuditUser;
import com.audit.automator.entities.Client;
import com.audit.automator.enums.ConfigEnum;
import com.audit.automator.enums.ResponseCodeEnum;
import com.audit.automator.pojo.AddClientRequest;
import com.audit.automator.pojo.UserCreationRequest;
import com.audit.automator.repository.DataRepository;
import com.audit.automator.response.CreationRequestResponse;
import com.audit.automator.utils.ProxyUtil;
import com.docusign.esign.client.auth.OAuth;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private static final String privateKeyFullPath = System.getProperty("user.dir") + "/docusign/privatekey.txt";

    DataRepository dataRepository;
    ProxyUtil proxyUtil;

    @Autowired
    public UserService(DataRepository dataRepository, ProxyUtil proxyUtil) {
        this.dataRepository = dataRepository;
        this.proxyUtil = proxyUtil;
    }

    public CreationRequestResponse createUser(UserCreationRequest request) {
        AuditUser auditUser = new AuditUser();
        auditUser.setFirstName(request.getFirstName());
        auditUser.setEmailAddress(request.getEmail());
//        auditUser.setPassword(request.getPassword());
        auditUser.setFirmName(request.getFirmName());
        auditUser.setOfficeName(request.getOfficeName());
        auditUser.setFirmFirstAddress(request.getFirmFirstAddress());
        auditUser.setFirmSecondAddress(request.getFirmSecondAddress());
        auditUser.setState(request.getState());
        auditUser.setCity(request.getCity());
        auditUser.setZipCode(request.getZipCode());
        auditUser.setTaxNumber(request.getTaxNumber());
        auditUser.setCompanyPhoneNumber(request.getCompanyPhoneNumber());

        proxyUtil.executeWithNewTransaction(() -> {
            dataRepository.create(auditUser);
        });

        CreationRequestResponse response =  new CreationRequestResponse();
        response.setName(request.getFirmName());
        response.setCode(ResponseCodeEnum.SUCCESS.getCode());
        response.setDescription(ResponseCodeEnum.SUCCESS.getDescription());

        return response;
    }

    public CreationRequestResponse addClient(AddClientRequest request) {

        CreationRequestResponse response =  new CreationRequestResponse();
        if(request == null){
            response.setCode(ResponseCodeEnum.ERROR.getCode());
            response.setDescription(ResponseCodeEnum.ERROR.getDescription());
            return response;
        }

        if (StringUtils.isAnyBlank(request.getSignerEmail(), request.getSignerName())){
            response.setCode(ResponseCodeEnum.ERROR.getCode());
            response.setDescription("Invalid Request. Signer email or Signer name not Provided");
            return response;
        }

        Client client = new Client();

        client.setCompanyName(request.getCompanyName());
        client.setWebsite(request.getWebsite());
        client.setSignerTitle(request.getSignerTitle());
        client.setFirstName(request.getFirstName());
        client.setLastName(request.getLastName());
        client.setFirmFirstAddress(request.getFirmFirstAddress());
        client.setFirmSecondAddress(request.getFirmSecondAddress());
        client.setCountry(request.getCountry());
        client.setCity(request.getCity());
        client.setRegion(request.getRegion());
        client.setPostalCode(request.getPostalCode());
        client.setSignerEmail(request.getSignerEmail());
        client.setPhone(request.getPhone());
        client.setLanguage(request.getLanguage());
        client.setSendEmailToClient(request.getSendEmailToClient());
        client.setAuditUserFk(request.getAuditUserFk());

        logger.debug("CREATING CLIENT");

        proxyUtil.executeWithNewTransaction(() -> {
            dataRepository.create(client);
        });

        response.setName(request.getFirstName());
        response.setCode(ResponseCodeEnum.SUCCESS.getCode());
        response.setDescription(ResponseCodeEnum.SUCCESS.getDescription());

        if(Boolean.valueOf(request.getSendEmailToClient())){

            String docuSignClientId = dataRepository.getConfigValue(ConfigEnum.DOCUSIGN_CLIENT_ID);
            String docuSignUserId = dataRepository.getConfigValue(ConfigEnum.DOCUSIGN_USER_ID);

            DocusignRequest docusignRequest = new DocusignRequest();
            docusignRequest.setClientId(docuSignClientId);
            docusignRequest.setUserId(docuSignUserId);

            docusignRequest.setSignerEmail(request.getSignerEmail());
            docusignRequest.setSignerName(request.getSignerName());
            docusignRequest.setSignerTitle(request.getSignerTitle());

            List<String> scopes = new ArrayList<>();
            scopes.add(OAuth.Scope_SIGNATURE);
            docusignRequest.setScopes(scopes);

            String privateKeyFullPath = System.getProperty("user.dir") + "/docusign/privatekey.txt";

            try {
                byte[] bytes = Files.readAllBytes(Paths.get(privateKeyFullPath));
                docusignRequest.setPrivateKey(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }

            boolean sentEmail = DocuSignEmailSender.sendEmail(docusignRequest);
            if(sentEmail) response.setDescription("Client created. Email Sent to Signer");
        }

        return response;
    }
}

