package util;

import constants.ConfigurationConstants;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailUtil {

    public static void sendEmailRegistrationLink(String id, String email, String hash) throws MessagingException {

        Properties props = new Properties();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", ConfigurationConstants.MAIL_SMTP_HOST);
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(ConfigurationConstants.MAIL_USERNAME, ConfigurationConstants.MAIL_PASSWORD);
            }
        });

        String link = ConfigurationConstants.MAIL_REGISTRATION_SITE_LINK + "?scope=activation&userId=" + id + "&hash=" + hash;

        StringBuilder bodyText = new StringBuilder();
        bodyText.append("<div>")
                .append("  Dear User<br/><br/>")
                .append("  Thank you for registration. Your mail (" + email + ") is under verification<br/>")
                .append("  Please click <a href=\"" + link + "\">here</a> or open below link in browser<br/>")
                .append("  <a href=\"" + link + "\">" + link + "</a>")
                .append("  <br/><br/>")
                .append("  Thanks,<br/>")
                .append("  SodhanaLibrary Team")
                .append("</div>");

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(ConfigurationConstants.MAIL_USERNAME));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
        message.setSubject("Email Registration");
        message.setContent(bodyText.toString(), "text/html; charset=utf-8");
        Transport.send(message);
    }
}
