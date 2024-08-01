package com.springboot.Controller;

import com.springboot.Entity.AddCroptype;
import com.springboot.Repository.AddCroptypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
public class AddCroptypeController
{
    @Autowired
    AddCroptypeRepository addCroptypeRepository;

    @PostMapping("/addCropType")
    public ResponseEntity<?> addCt(@RequestBody AddCroptype obj)
    {
        Optional<AddCroptype> Ac=addCroptypeRepository.findByCtname(obj.getCtname());
        if(Ac.isPresent())
            return new ResponseEntity<>("Crop Type Already Exists",HttpStatus.OK);
        else
        {
            addCroptypeRepository.save(obj);
            return new ResponseEntity<>("Crop Type Added SuccessFully", HttpStatus.OK);
        }
    }

    @GetMapping("/getAllCropType")
    public ResponseEntity<?> getCropType()
    {
        List<AddCroptype> croptypeList=addCroptypeRepository.findAll();
        return new ResponseEntity<>(croptypeList,HttpStatus.OK);
    }

}
