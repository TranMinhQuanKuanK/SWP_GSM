/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import java.io.Serializable;

/**
 *
 * @author quan6
 */
public class EmailSender implements Serializable {

    public static void SendPasswordEmail(String EmailTo,String CashierName, String account, String Password) {

        String host = "smtp.gmail.com";
        final String user = "swpgsm@gmail.com";//change accordingly  
        final String password = "SWPPassword";//change accordingly  

        //Get the session object  
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.starttls.enable", "true");
        //props.setProperty("mail.smtps.ssl.enable", "true");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });

        //Compose the message  
        try {
            MimeMessage message = new MimeMessage(session);
            message.setHeader("Content-Type", "text/html; charset=UTF-8");
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(EmailTo));
            message.setSubject(MimeUtility.encodeText("Tài khoản đăng nhập ứng dụng Tạp hóa ÔNG SÁU", "UTF-8", null));
            String bodyMail = "<p>Chào <b>"+CashierName+"</b>, tài khoản đăng nhập ứng dụng Tạp hóa ÔNG SÁU của bạn là: </p>\n"
                    + "<p>Tên tài khoản: <span style='font-weight:bold; color:red;'>" + account + "</span></p>\n"
                    + "<p>Mât khẩu: <span style='font-weight:bold; color:red;'>" + Password + "</span></p>\n"
                    + "<p>Chúc bạn một ngày làm việc vui vẻ</p>\n"
                    + "<p>Thân ái</p>\n"
                    + "<p>CHỦ TIỆM</p>";
            message.setText(bodyMail, "UTF-8", "html");

            //send the message  
            Transport.send(message);

            System.out.println("message sent successfully...");

        } catch (Exception e) {
            e.printStackTrace();
        }
//        String EmailTo = "quantmse150070@fpt.edu.vn";//change accordingly  
//        String EmailFrom = "quan69999@gmail.com";
//        String host = "smtp.gmail.com";//or IP address  
//
//        //Get the session object  
//        Properties properties = System.getProperties();
//        properties.setProperty("mail.smtp.host", host);
//        
//        Session session = Session.getDefaultInstance(properties);
//
//        //compose the message  
//        try {
//            MimeMessage message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(EmailFrom));
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress(EmailTo));
//            message.setSubject("Tài khoản đăng nhập ứng dụng Tạp hóa ÔNG SÁU");
//            message.setText("Chào bạn, đây là tài khoản nhân viên đăng nhập ứng dụng Tạp hóa ÔNG SÁU: \n"
//                    + "Tên tài khoản: "+account+"\n"
//                            + "Mât khẩu: "+Password+"\n"
//                                    + "THÂN ÁI\n"
//                                    + "-Chủ tiệm-");
//
//            // Send message  
//            Transport.send(message);
//            System.out.println("message sent successfully....");
//
//        } catch (MessagingException mex) {
//            mex.printStackTrace();
//        }
    }

}
