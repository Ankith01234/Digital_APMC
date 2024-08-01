package com.springboot.Controller;

import com.springboot.DTO.EmailData;
import com.springboot.Entity.AddApmc;
import com.springboot.Entity.HopcomesSignup;
import com.springboot.Repository.AddApmcRepository;
import com.springboot.Repository.HopcomesSignupRepository;
import com.springboot.Smtpservice.Smtpserviceclass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Random;

@RestController
@CrossOrigin("*")
public class EmailController
{

    @Autowired
    Smtpserviceclass smtpserviceclass;

    @Autowired
    AddApmcRepository addApmcRepository;

    @Autowired
    HopcomesSignupRepository hopcomesSignupRepository;

    @PostMapping("/sendotp/{email}")
    public ResponseEntity<?> sendOTP(@PathVariable String email)
    {
        Optional<AddApmc> A=addApmcRepository.findByEmail(email);
        Optional<HopcomesSignup> Hs=hopcomesSignupRepository.findByEmail(email);
        if(A.isPresent() || Hs.isPresent())
        {
            Random rnd = new Random();
            int otp = rnd.nextInt(1000, 9999);
            EmailData emailData = new EmailData();
            emailData.setRecepient(email);
            emailData.setOtp(otp);
            emailData.setSubject("Otp to change Password");
            emailData.setMessage("Your Otp:" + otp);

            String msg= smtpserviceclass.sendOtp(emailData);

            if(msg.equals("OTP sent to your email pls check"))
                return new ResponseEntity<>(msg,HttpStatus.OK);
            else
                return new ResponseEntity<>(msg,HttpStatus.OK);

        }
        else
            return new ResponseEntity<>("Email Doesn't Exists", HttpStatus.OK);

    }

    @GetMapping("/chkotp/{otp}")
    public ResponseEntity<?> chkOTP(@PathVariable Integer otp)
    {
        EmailData emailData=new EmailData();
        emailData.setOtp(otp);

        String msg=smtpserviceclass.CheckOtp(emailData);
        if(msg.equals("Correct Otp"))
            return new ResponseEntity<>("Entered Correct OTP",HttpStatus.OK);
        else
            return new ResponseEntity<>(msg,HttpStatus.OK);
    }

    @PutMapping("/changepassword/{email}/{newpassword}")
    public ResponseEntity<?> updatePass(@PathVariable String email,@PathVariable String newpassword)
    {
        Optional<AddApmc> A=addApmcRepository.findByEmail(email);
        Optional<HopcomesSignup> Hs=hopcomesSignupRepository.findByEmail(email);
        if(A.isPresent())
        {
            AddApmc addApmc=A.get();
            addApmc.setPassword(newpassword);
            addApmcRepository.save(addApmc);
            return new ResponseEntity<>("Password Changed Sucessfully",HttpStatus.OK);
        }
        else if(Hs.isPresent())
        {
            HopcomesSignup hopcomesSignup=Hs.get();
            hopcomesSignup.setPassword(newpassword);
            hopcomesSignupRepository.save(hopcomesSignup);
            return new ResponseEntity<>("Password Changed Sucessfully",HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("Not Found Email Id",HttpStatus.OK);
    }

}
