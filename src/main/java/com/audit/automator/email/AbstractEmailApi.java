/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.audit.automator.email;

import com.audit.automator.email.api.beanfactory.EmailBeanFactory;
import lombok.extern.slf4j.Slf4j;
import org.antlr.stringtemplate.StringTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Ayodele Oluwabusola
 * @since May 17, 2021 - 12:45:36 AM
 */
@Slf4j
@SuppressWarnings("PMD.UseConcurrentHashMap")
public abstract class AbstractEmailApi {

    public EmailBean handleGeneralConfiguration (EmailBean emailBean) {
        
        try {
            String line = "";
            BufferedReader buffer = new BufferedReader(new InputStreamReader(EmailBeanFactory.class.getResourceAsStream("/mailtemplates/send_general_email.stl")));
            StringBuilder builder = new StringBuilder();
            while (line  != null) {
                builder.append(line).append('\n');
                line =  buffer.readLine(); 
            }

            Map<String, String> params = new HashMap<String, String>();
            params.put("message", emailBean.getMessage());
            params.put("year", new SimpleDateFormat("yyyy").format(new Date ()));

            StringTemplate template = new StringTemplate(builder.toString());
            template.setAttributes(params);
            String message = template.toString();
            emailBean.setMessage(message);
        } catch (IOException e) {
            log.error("", e);
        }
        
        return emailBean;
    }
}
