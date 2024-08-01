package com.springboot.Controller;

import com.springboot.DTO.EmailData;
import com.springboot.Entity.AddApmc;
import com.springboot.Repository.AddApmcRepository;
import com.springboot.Smtpservice.Smtpserviceclass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
@CrossOrigin("*")
public class AddApmcController
{
    @Autowired
    AddApmcRepository addApmcRepository;

    @Autowired
    Smtpserviceclass smtpserviceclass;

    @PostMapping("/addApmc")
    public ResponseEntity<?> addAPMC(@RequestBody AddApmc obj)
    {
        Optional<AddApmc> A=addApmcRepository.findByEmail(obj.getEmail());
        if(A.isPresent())
            return new ResponseEntity<>("Email Already Exists", HttpStatus.OK);
        else
        {
            Random rnd=new Random();
            int id=rnd.nextInt(100000,999999);
            int password=rnd.nextInt(1000,9999);
            obj.setId(id);
            obj.setPassword(String.valueOf(password));

            EmailData emailData=new EmailData();

            emailData.setRecepient(obj.getEmail());
            emailData.setSubject("Login Credentials");
            emailData.setMessage("Your id: "+id+" , Password: "+password);

            String msg=smtpserviceclass.sendEmail(emailData);

            if(msg.equals("Mail Sent Successfully"))
            {
                addApmcRepository.save(obj);
                return new ResponseEntity<>("Apmc Added Successfully",HttpStatus.OK);
            }
            else
                return new ResponseEntity<>(msg,HttpStatus.OK);

        }
    }

    @GetMapping("/getAllApmc")
    public ResponseEntity<?> getApmc()
    {
        List<AddApmc> ApmcList=addApmcRepository.findAll();
        return new ResponseEntity<>(ApmcList,HttpStatus.OK);
    }

    @PostMapping("/chkId")
    public ResponseEntity<?> checkId(@RequestBody AddApmc obj)
    {
        Optional<AddApmc> A=addApmcRepository.findById(obj.getId());
        if(A.isPresent())
        {
            AddApmc addApmc=A.get();
            if(addApmc.getPassword().equals(obj.getPassword()))
                return new ResponseEntity<>("Correct Password",HttpStatus.OK);
            else
                return new ResponseEntity<>("Incorrect Password",HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("Apmc not found",HttpStatus.OK);
    }

}
