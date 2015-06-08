package com.ryan_5280studios.sms_bomber;

import android.os.StrictMode;
import android.util.Log;

import java.util.Properties;
import java.util.Random;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

/**
 * Created by Ryan on 5/7/2015.
 */
public class Email {
    Random rng;
    public Email() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        rng = new Random();
    }

    public void email(String number, String carrier, int num, String text) throws MessagingException{
        String to = emailFromCarrier(number,carrier.toLowerCase());
        String address = "test@5280studios.com",password="tacomaster";
        for(int a = 1; a <= num; a++){
            Multipart multiPart;
            String finalString="";
            address = randomEmail();

            Properties props = System.getProperties();
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "mail.5280studios.com");
            props.put("mail.smtp.user", address);
            props.put("mail.smtp.password", password);
            props.put("mail.smtp.port", "26");
            props.put("mail.smtp.auth", "true");
            Session session = Session.getDefaultInstance(props, null);
            DataHandler handler=new DataHandler(new ByteArrayDataSource(finalString.getBytes(),"text/plain" ));
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(address));
            message.setDataHandler(handler);
            multiPart=new MimeMultipart();
            InternetAddress toAddress;
            toAddress = new InternetAddress(to);
            message.addRecipient(Message.RecipientType.TO, toAddress);
            message.setSubject("");
            message.setContent(multiPart);
            message.setText(text);
            Transport transport = session.getTransport("smtp");
            Log.i("check", "connecting");
            transport.connect("mail.5280studios.com", address, password);
            transport.sendMessage(message, message.getAllRecipients());
            Log.i("check", "sent" + address + " " + to);
            transport.close();
        }
    }

    private String randomEmail() {
        return (rng.nextInt(2000) + 1) + "@5280studios.com";
    }

    private String emailFromCarrier(String number, String carrier) {
        String email ="";
        if(carrier.equals("sprint")){
            email =  number + "@messaging.sprintpcs.com";
        }else{
            email = number + "@txt.att.net";
        }
        return email;
    }

}

