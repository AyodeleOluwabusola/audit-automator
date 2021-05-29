package com.audit.automator.utils;

import java.util.Base64;

public class Commons {

    public static String getBase64 (byte[] value) {
        if (value == null) {
            return null;
        }
        return Base64.getEncoder().encodeToString(value);
    }

//    public class EmailWithPdf {
//
//        /**
//         * Sends an email with a PDF attachment.
//         */
//        public void email() {
//            String smtpHost = "yourhost.com"; //replace this with a valid host
//            int smtpPort = 587; //replace this with a valid port
//
//            String sender = "sender@yourhost.com"; //replace this with a valid sender email address
//            String recipient = "recipient@anotherhost.com"; //replace this with a valid recipient email address
//            String content = "dummy content"; //this will be the text of the email
//            String subject = "dummy subject"; //this will be the subject of the email
//
//            Properties properties = new Properties();
//            properties.put("mail.smtp.host", smtpHost);
//            properties.put("mail.smtp.port", smtpPort);
//            Session session = Session.getDefaultInstance(properties, null);
//
//            ByteArrayOutputStream outputStream = null;
//
//            try {
//                //construct the text body part
//                MimeBodyPart textBodyPart = new MimeBodyPart();
//                textBodyPart.setText(content);
//
//                //now write the PDF content to the output stream
//                outputStream = new ByteArrayOutputStream();
//                writePdf(outputStream);
//                byte[] bytes = outputStream.toByteArray();
//
//                //construct the pdf body part
//                DataSource dataSource = new ByteArrayDataSource(bytes, "application/pdf");
//                MimeBodyPart pdfBodyPart = new MimeBodyPart();
//                pdfBodyPart.setDataHandler(new DataHandler(dataSource));
//                pdfBodyPart.setFileName("test.pdf");
//
//                //construct the mime multi part
//                MimeMultipart mimeMultipart = new MimeMultipart();
//                mimeMultipart.addBodyPart(textBodyPart);
//                mimeMultipart.addBodyPart(pdfBodyPart);
//
//                //create the sender/recipient addresses
//                InternetAddress iaSender = new InternetAddress(sender);
//                InternetAddress iaRecipient = new InternetAddress(recipient);
//
//                //construct the mime message
//                MimeMessage mimeMessage = new MimeMessage(session);
//                mimeMessage.setSender(iaSender);
//                mimeMessage.setSubject(subject);
//                mimeMessage.setRecipient(Message.RecipientType.TO, iaRecipient);
//                mimeMessage.setContent(mimeMultipart);
//
//                //send off the email
//                Transport.send(mimeMessage);
//
//                System.out.println("sent from " + sender +
//                        ", to " + recipient +
//                        "; server = " + smtpHost + ", port = " + smtpPort);
//            } catch(Exception ex) {
//                ex.printStackTrace();
//            } finally {
//                //clean off
//                if(null != outputStream) {
//                    try { outputStream.close(); outputStream = null; }
//                    catch(Exception ex) { }
//                }
//            }
//        }
//
//        /**
//         * Writes the content of a PDF file (using iText API)
//         * to the {@link OutputStream}.
//         * @param outputStream {@link OutputStream}.
//         * @throws Exception
//         */
//        public void writePdf(OutputStream outputStream) throws Exception {
//            Document document = new Document();
//            PdfWriter.getInstance(document, outputStream);
//
//            document.open();
//
//            document.addTitle("Test PDF");
//            document.addSubject("Testing email PDF");
//            document.addKeywords("iText, email");
//            document.addAuthor("Jee Vang");
//            document.addCreator("Jee Vang");
//
//            Paragraph paragraph = new Paragraph();
//            paragraph.add(new Chunk("hello!"));
//            document.add(paragraph);
//
//            document.close();
//        }
//
//        /**
//         * Main method.
//         * @param args No args required.
//         */
//        public static void main(String[] args) {
//            EmailWithPdf demo = new EmailWithPdf();
//            demo.email();
//        }
//    }
}
