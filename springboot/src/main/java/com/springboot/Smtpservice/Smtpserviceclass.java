package com.springboot.Smtpservice;

import com.springboot.DTO.EmailData;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class Smtpserviceclass
{

    @Value("${spring.mail.username}") private String sender;

    @Autowired
    JavaMailSender javaMailSender;

    static int token;

    public String sendEmail(@RequestBody EmailData details)
    {
        MimeMessage mimeMessage=javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper;

        try
        {
            mimeMessageHelper=new MimeMessageHelper(mimeMessage,true);

            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(details.getRecepient());
            mimeMessageHelper.setSubject(details.getSubject());
            mimeMessageHelper.setText(details.getMessage());

            javaMailSender.send(mimeMessage);
            return "Mail Sent Successfully";

        }
        catch (MessagingException e2)
        {
            return "Mail can't sent";
        }
    }

    public String sendOtp(@PathVariable EmailData details)
    {
        MimeMessage mimeMessage=javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper;

        try
        {
            mimeMessageHelper=new MimeMessageHelper(mimeMessage,true);

            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setText(details.getMessage());
            mimeMessageHelper.setSubject(details.getSubject());
            mimeMessageHelper.setTo(details.getRecepient());

            token=details.getOtp();

            javaMailSender.send(mimeMessage);

            return "OTP sent to your email pls check";

        }
        catch (MessagingException e1)
        {
            return "Otp can't sent";
        }
    }

    public String CheckOtp(@RequestBody EmailData details)
    {
        if(details.getOtp()==token)
            return "Correct Otp";
        else
            return "Wrong Otp";
    }

}
