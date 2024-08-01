package com.springboot.Controller;

import com.springboot.DTO.EmailData;
import com.springboot.Entity.AddApmc;
import com.springboot.Entity.AddFarmer;
import com.springboot.Entity.FarmerSales;
import com.springboot.Repository.AddFarmerRepository;
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
public class AddFarmerController
{
    @Autowired
    AddFarmerRepository addFarmerRepository;

    @Autowired
    Smtpserviceclass smtpserviceclass;

    @PostMapping("/addFarmer")
    public ResponseEntity<?> addFarmerVal(@RequestBody AddFarmer obj)
    {
        Random rnd=new Random();
        int id=rnd.nextInt(100000,999999);
        int password=rnd.nextInt(1000,9999);
        obj.setFid(id);
        obj.setPassword(String.valueOf(password));
        addFarmerRepository.save(obj);
        return new ResponseEntity<>("Farmer Details added Successfully, Farmer Id:"+id+" ,Password:"+password,HttpStatus.OK);
    }

    @GetMapping("getAllFarmer")
    public ResponseEntity<?> getFarmer()
    {
        List<AddFarmer> addFarmerList=addFarmerRepository.findAll();
        return new ResponseEntity<>(addFarmerList,HttpStatus.OK);
    }

    @PostMapping("/chkFarmerId/{id}/{password}")
    public ResponseEntity<?> chkFarmer(@PathVariable Integer id,@PathVariable String password)
    {
        Optional<AddFarmer> Af=addFarmerRepository.findById(id);
        if(Af.isPresent())
        {
            AddFarmer addFarmer=Af.get();
            if(addFarmer.getPassword().equals(password))
                return new ResponseEntity<>("Correct Password",HttpStatus.OK);
            else
                return new ResponseEntity<>("Incorrect Password",HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("Farmer Id not found",HttpStatus.OK);
    }

}
