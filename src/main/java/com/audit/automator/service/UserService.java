package com.audit.automator.service;

import com.audit.automator.entities.AuditUser;
import com.audit.automator.entities.Client;
import com.audit.automator.enums.ResponseCodeEnum;
import com.audit.automator.repository.DataRepository;
import com.audit.automator.pojo.AddClientRequest;
import com.audit.automator.pojo.UserCreationRequest;
import com.audit.automator.response.CreationRequestResponse;
import com.audit.automator.utils.ProxyUtil;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

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

        System.out.println("ADD CLIENT");
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

        CreationRequestResponse response =  new CreationRequestResponse();
        response.setName(request.getFirstName());
        response.setCode(ResponseCodeEnum.SUCCESS.getCode());
        response.setDescription(ResponseCodeEnum.SUCCESS.getDescription());

        return response;
    }
}

