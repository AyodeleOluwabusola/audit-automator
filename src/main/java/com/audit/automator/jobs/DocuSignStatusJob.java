package com.audit.automator.jobs;

import com.audit.automator.docusign.DocuSignAuth;
import com.audit.automator.email.EmailApiAuthenticator;
import com.audit.automator.email.EmailBean;
import com.audit.automator.email.api.beanfactory.ReportsBeanFactory;
import com.audit.automator.entities.BaseLongPkEntity;
import com.audit.automator.entities.Client;
import com.audit.automator.entities.DocuSignLog;
import com.audit.automator.enums.BankEnum;
import com.audit.automator.enums.ConfigEnum;
import com.audit.automator.enums.DocuSignStatusEnum;
import com.audit.automator.repository.DataRepository;
import com.docusign.esign.api.EnvelopesApi;
import com.docusign.esign.model.Envelope;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class DocuSignStatusJob {

    final DataRepository dataRepository;
    private int batchSize;
    private String smtpUsername;
    private String smtpPassword;
    private int smtpPort;
    private String smtpHostName;
    private final long requeryInterval = 300000L;
    private Date docuSignDateLimit;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    public DocuSignStatusJob(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    @PostConstruct
    public void init() {
        batchSize = Integer.parseInt(dataRepository.getConfigValue(ConfigEnum.BATCH_SIZE));
        smtpUsername = dataRepository.getConfigValue(ConfigEnum.SMTP_USERNAME);
        smtpPassword = dataRepository.getConfigValue(ConfigEnum.SMTP_PASSWORD);
        smtpPort = dataRepository.getConfigAsInteger(ConfigEnum.SMTP_PORT);
        smtpHostName = dataRepository.getConfigValue(ConfigEnum.SMTP_HOSTNAME);
        docuSignDateLimit = DateUtils.addMilliseconds(new Date(), dataRepository.getConfigAsInteger(ConfigEnum.DOCUSIGN_REQUERY_DURATION));
    }

    @Scheduled(fixedRate = requeryInterval)
    public void reportCurrentTime() {
        log.info("The time is now {}", dateFormat.format(new Date()));
        long currentMinPk = Long.parseLong(String.valueOf(Integer.MIN_VALUE));
        handleDocuSignStatusRequery(currentMinPk);

    }

    private void handleDocuSignStatusRequery(long currentMinPk) {
        List<DocuSignLog> docuSignLogs = dataRepository.getPendingDocuSignLogs(batchSize, currentMinPk, DocuSignStatusEnum.SENT.name(), docuSignDateLimit);

        while (docuSignLogs != null && !docuSignLogs.isEmpty()) {
            checkDocuSignStatus(docuSignLogs);

            currentMinPk = docuSignLogs.stream().map(BaseLongPkEntity::getPk).max(Long::compare).get();

            docuSignLogs = dataRepository.getPendingDocuSignLogs(batchSize, currentMinPk, DocuSignStatusEnum.SENT.name(), docuSignDateLimit);
        }
    }

    private void checkDocuSignStatus(List<DocuSignLog> docuSignLogs) {
        for(DocuSignLog docuSignLog :  docuSignLogs){

            try {
                String accountId = new DocuSignAuth(dataRepository).getDocuSignAccountId();

                EnvelopesApi envelopesApi = new EnvelopesApi();
                Envelope envelope = envelopesApi.getEnvelope(accountId, docuSignLog.getEnvelopeId());
                System.out.println("ENVELOPE IS {}"+ envelope);

                if(envelope.getStatus().equalsIgnoreCase(DocuSignStatusEnum.COMPLETED.getName())){
                    Client client = dataRepository.findById(Client.class, docuSignLog.getClientPk());
                    String bankEmail = BankEnum.getEmailFromString(client.getBank());
                    System.out.println("BANK EMAIL {}"+bankEmail);

                    byte[] signedDocument = envelopesApi.getDocument(accountId, docuSignLog.getEnvelopeId(), "combined");

                    File file = File.createTempFile("document", ".pdf");
                    FileUtils.writeByteArrayToFile(file, signedDocument);

                    EmailApiAuthenticator authenticator = new EmailApiAuthenticator(smtpUsername, docuSignLog.getDocumentData(), smtpPassword,
                            smtpPort, smtpHostName, "Report");
                    authenticator.setStartTls(true);
                    authenticator.setSmtpAuth(true);
                    authenticator.setSslEnabled(false);
                    authenticator.setMailDebug(false);

                    EmailBean userEmailBean = ReportsBeanFactory.getReportEmailBean(authenticator, "title", "description", bankEmail, file);
                    new EmailApiImpl().sendAttachment(userEmailBean);

                    docuSignLog.setStatus(DocuSignStatusEnum.COMPLETED.name());
                    dataRepository.update(docuSignLog);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
