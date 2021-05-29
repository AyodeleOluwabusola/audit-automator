/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.audit.automator.email;

import java.io.File;
import java.util.List;

/**
 *
 * @author Ayodele Oluwabusola
 * @since May 17, 2021 - 12:50:36 AM
 */
public class EmailBean {

    private static final String[] EMPTY_ARRAY = new String[0];
    private static final File[] EMPTY_FILE_ARRAY = new File[0];
    private EmailApiAuthenticator apiAuthenticator;
    private String[] recipients;
    private String message;
    private File attachment;
    private File[] attachments;
    private byte[] byteData;
    private String toName;
    private List<String> attachmentPreview;
    private String subject;

    public EmailBean(EmailApiAuthenticator apiAuthenticator, String[] recipients, String message, File attachment, String toName, String subject) {
        this.apiAuthenticator = apiAuthenticator;
        this.recipients = recipients.clone();
        this.message = message;
        this.attachment = attachment;
        this.byteData = byteData;
        this.toName = toName;
        this.subject = subject;
    }

    public EmailBean(EmailApiAuthenticator apiAuthenticator, String[] recipients, String message, File[] attachments, String toName, String subject, List<String> attachmentPreview) {
        this.apiAuthenticator = apiAuthenticator;
        this.recipients = recipients.clone();
        this.message = message;
        this.attachments = attachments.clone();
        this.attachmentPreview = attachmentPreview;
        this.toName = toName;
        this.subject = subject;
    }

    public String[] getRecipients() {
        if(recipients != null){
            return recipients.clone();
        }
        return EMPTY_ARRAY;
    }

    public void setRecipients(String[] recipients) {
        this.recipients = recipients.clone();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public File getAttachment() {
        return attachment;
    }

    public void setAttachment(File attachment) {
        this.attachment = attachment;
    }

    public EmailApiAuthenticator getApiAuthenticator() {
        return apiAuthenticator;
    }

    public void setApiAuthenticator(EmailApiAuthenticator apiAuthenticator) {
        this.apiAuthenticator = apiAuthenticator;
    }

    public File[] getAttachments() {
        if(attachments != null){
            return attachments.clone();
        }
        return EMPTY_FILE_ARRAY;
    }

    public void setAttachments(File[] attachments) {
        this.attachments = attachments.clone();
    }

    public List<String> getAttachmentPreview() {
        return attachmentPreview;
    }

    public void setAttachmentPreview(List<String> attachmentPreview) {
        this.attachmentPreview = attachmentPreview;
    }
}
