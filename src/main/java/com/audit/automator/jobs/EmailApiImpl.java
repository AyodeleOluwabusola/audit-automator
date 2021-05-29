package com.audit.automator.jobs;


import com.audit.automator.email.AbstractEmailApi;
import com.audit.automator.email.EmailApiAuthenticator;
import com.audit.automator.email.EmailBean;
import com.sun.mail.smtp.SMTPTransport;
import lombok.extern.slf4j.Slf4j;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;

@Slf4j
public class EmailApiImpl extends AbstractEmailApi {
    private static final String FALSE = "false";


    public void sendAttachment(EmailBean email) {
        handleGeneralConfiguration(email);

        try {
            EmailApiAuthenticator apiAuthenticator = email.getApiAuthenticator();

            Properties properties = System.getProperties();

            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", apiAuthenticator.getHostname());
            properties.put("mail.smtp.user", apiAuthenticator.getUsername());
            properties.put("mail.smtp.password", apiAuthenticator.getPassword());
            properties.put("mail.smtp.port", apiAuthenticator.getPort());
            properties.put("mail.smtp.auth", "true");

            Session session = Session.getInstance(properties, null);
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("oluwabusola.aayodele@gmail.com"));

            InternetAddress[] addrs = InternetAddress.parse(email.getRecipients()[0], false);
            message.setRecipients(Message.RecipientType.TO, addrs);

            message.setSubject(email.getSubject());
            message.setText("Testing some Mailgun awesomeness");
            message.setSentDate(new Date());


            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(email.getMessage(), "text/html");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(email.getAttachment().getAbsolutePath());
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(email.getAttachment().getName());
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);

            SMTPTransport t =
                    (SMTPTransport) session.getTransport("smtps");
            t.connect("smtp.mailgun.org", apiAuthenticator.getUsername(), apiAuthenticator.getPassword());
            t.sendMessage(message, message.getAllRecipients());

            System.out.println("Response: " + t.getLastServerResponse());

        } catch (AddressException ex) {
            log.error("", ex);
        } catch (MessagingException ex) {
            log.error("", ex);
        }
    }

}
