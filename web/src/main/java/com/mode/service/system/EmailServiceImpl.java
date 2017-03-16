//package com.mode.service.system;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.mode.base.response.Message;
//import com.mode.exception.ModeException;
//
//import net.sargue.mailgun.Mail;
//import net.sargue.mailgun.Response;
//
///**
// * Created by Ben Hu on 2016/7/21.
// */
//@Service
//public class EmailServiceImpl  implements EmailService{
//    @Autowired
//    private net.sargue.mailgun.Configuration configuration;
//
//    @Override
//    public void send(String sendTo, String subject, String message) {
//
//        Response response = Mail.using(configuration)
//                .to(sendTo)
//                .subject(subject)
//                .text(message)
//                .build()
//                .send();
//        if (!response.isOk()) {
//            throw new ModeException(Message.SEND_EMAIL_FAILED);
//        }
//    }
//}
