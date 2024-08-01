package com.springboot.Controller;

import com.springboot.DTO.EmailData;
import com.springboot.Entity.HopcomesSignup;
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
public class HopcomesSignUpController
{
    @Autowired
    HopcomesSignupRepository hopcomesSignupRepository;

    @Autowired
    Smtpserviceclass smtpserviceclass;

    @PostMapping("/signup")
    public ResponseEntity<?> addHopComes(@RequestBody HopcomesSignup obj)
    {
        Optional<HopcomesSignup> Hs=hopcomesSignupRepository.findByEmail(obj.getEmail());
        if(Hs.isPresent())
            return new ResponseEntity<>("Hopcomes Already Exists", HttpStatus.OK);
        else
        {
            Random rnd=new Random();
            int id=rnd.nextInt(100000,999999);
            int password=rnd.nextInt(1000,9999);
            obj.setHpid(id);
            obj.setPassword(String.valueOf(password));

            EmailData emailData=new EmailData();
            emailData.setRecepient(obj.getEmail());
            emailData.setSubject("Login Credentials of Hopcomes");
            emailData.setMessage("Your Id: "+id+" , Password:"+password);

            String msg=smtpserviceclass.sendEmail(emailData);

            if(msg.equals("Mail Sent Successfully"))
            {
                hopcomesSignupRepository.save(obj);
                return new ResponseEntity<>("Account has been created for Hopcomes", HttpStatus.OK);
            }
            else
                return new ResponseEntity<>(msg,HttpStatus.OK);

        }
    }

    @PostMapping("/chkHopcomsId/{id}/{password}")
    public ResponseEntity<?> chkId(@PathVariable Integer id,@PathVariable String password)
    {
        Optional<HopcomesSignup> Hs=hopcomesSignupRepository.findById(id);
        if(Hs.isPresent())
        {
            HopcomesSignup hopcomesSignup=Hs.get();
            if(hopcomesSignup.getPassword().equals(password))
                return new ResponseEntity<>("Correct Password",HttpStatus.OK);
            else
                return new ResponseEntity<>("Wrong Password",HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("HopcomsId not Found",HttpStatus.OK);
    }

}
