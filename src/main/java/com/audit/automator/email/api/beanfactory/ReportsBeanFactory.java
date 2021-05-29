package com.audit.automator.email.api.beanfactory;

import com.audit.automator.email.EmailApiAuthenticator;
import com.audit.automator.email.EmailBean;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author PC
 */
@Slf4j
@SuppressWarnings({"PMD.UseConcurrentHashMap","PMD.StringToString"})
public final class ReportsBeanFactory extends EmailBeanFactory{

    private ReportsBeanFactory(){

    }

    public static EmailBean getReportEmailBean(EmailApiAuthenticator apiAuthenticator, String title, String description, String emailAddress, File attachment) {
        String templateFileName = "email_template.stl";
        String message = "";
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("title", title);
            params.put("description", description);

            message = generateMessage(templateFileName, params);
            String[] recipientMail = { emailAddress };

            EmailBean emailBean = new EmailBean(apiAuthenticator, recipientMail, message, attachment, emailAddress, title);
            emailBean.setApiAuthenticator(apiAuthenticator);
            return emailBean;
        } catch (IllegalArgumentException ex) {
            log.error("Error returning EmailBean", ex);
        }
        return null;
    }

}
