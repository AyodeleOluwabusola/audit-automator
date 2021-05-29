/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.audit.automator.email.api.beanfactory;

import lombok.extern.slf4j.Slf4j;
import org.antlr.stringtemplate.StringTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * @author Ayodele Oluwabusola
 * @since May 17, 2021 - 12:01:36 AM
 */
@Slf4j
@SuppressWarnings("PMD.UseConcurrentHashMap")
public class EmailBeanFactory {

    public final static String EMAIL_TEMPLATE_DIR = "/mailtemplates/";


    public static String generateMessage(String templateFileName, Map<String, Object> params) {
        String message = "";
        try {
            String line = "";
            BufferedReader buffer = new BufferedReader(new InputStreamReader(EmailBeanFactory.class.getResourceAsStream(EMAIL_TEMPLATE_DIR + templateFileName)));
            StringBuilder builder = new StringBuilder();
            while (line != null) {
                builder.append(line).append('\n');
                line = buffer.readLine() ;
            }
            StringTemplate template = new StringTemplate(builder.toString());
            template.setAttributes(params);
            message = template.toString();
        } catch (IOException ex) {
            log.error("Error generating email message", ex);
        }
        return message;
    }

     public void init() {}

}
