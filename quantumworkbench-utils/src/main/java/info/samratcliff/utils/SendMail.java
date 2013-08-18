/**
 * @Author = Sam Ratcliff
 */
package info.samratcliff.utils;

/**
 * @author Sam Ratcliff
 *
 */

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendMail {

    private class SMTPAuthenticator extends javax.mail.Authenticator {
        @Override
        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(from, d_password);
        }
    }

    public static void main(String[] args) {

        String from = "mengquantum@gmail.com";
        String to = "sam.ratcliff@gmail.com";
        String subject = "Test";
        String message = "A test message";

        SendMail sendMail = new SendMail(from, to, subject, message);
        sendMail.send();
    }

    private final String from;
    private final String to;

    private final String subject;

    private final String text;

    private final String d_password = "JohnClark";

    public SendMail(String from, String to, String subject, String text) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.text = text;
    }

    public void send() {

        System.out.println("SendMail.send");
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.debug", "true");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");

        try {

            Authenticator auth = new SMTPAuthenticator();
            Session session = Session.getInstance(props, auth);
            // session.setDebug(true);
            MimeMessage msg = new MimeMessage(session);
            msg.setText(this.text);
            msg.setSubject(this.subject);
            msg.setFrom(new InternetAddress(this.from));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
                    this.to));

            Transport.send(msg);
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}