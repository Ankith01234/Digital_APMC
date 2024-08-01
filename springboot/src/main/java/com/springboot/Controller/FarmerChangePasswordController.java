package com.springboot.Controller;

import com.springboot.Entity.AddFarmer;
import com.springboot.Repository.AddFarmerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;
import java.util.Optional;

@RestController
@CrossOrigin("*")
public class FarmerChangePasswordController
{

    @Autowired
    AddFarmerRepository addFarmerRepository;

    @GetMapping("/getOldPassword/{farmerid}") //This APi used to get the old Password of the Farmer
    public ResponseEntity<?> getFarmerOldPassword(@PathVariable String farmerid)
    {
        Optional<AddFarmer> Af=addFarmerRepository.findById(Integer.parseInt(farmerid));
        if(Af.isPresent())
        {
            AddFarmer addFarmer=Af.get();
            return new ResponseEntity<>(addFarmer.getPassword(), HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("Farmer Id Mismatch",HttpStatus.OK);
    }

    @PutMapping("/changeFarmerPassword/{farmerid}/{newpassword}") //This Api used to change the farmer password
    public ResponseEntity<?> changePassword(@PathVariable String farmerid,@PathVariable String newpassword)
    {
        Optional<AddFarmer> Af=addFarmerRepository.findById(Integer.parseInt(farmerid));
        if(Af.isPresent())
        {
            AddFarmer addFarmer=Af.get();
            addFarmer.setPassword(newpassword);
            addFarmerRepository.save(addFarmer);
            return new ResponseEntity<>("Password Changed Sucessfully",HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("Farmer Id not found",HttpStatus.OK);
    }

}
