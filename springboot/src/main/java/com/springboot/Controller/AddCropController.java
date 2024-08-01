package com.springboot.Controller;

import com.springboot.Entity.AddCrop;
import com.springboot.Entity.AddCroptype;
import com.springboot.Repository.AddCropRepository;
import com.springboot.Repository.AddCroptypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
public class AddCropController
{
    @Autowired
    AddCropRepository addCropRepository;

    @Autowired
    AddCroptypeRepository addCroptypeRepository;

    @PostMapping("/addCrop/{croptypeid}")
    public ResponseEntity<?> addCropVal(@RequestBody AddCrop obj, @PathVariable Integer croptypeid)
    {
        Optional<AddCroptype> Ac=addCroptypeRepository.findById(croptypeid);
        if(Ac.isPresent())
        {
            AddCroptype addCroptype=Ac.get();
            obj.setAddCroptype(addCroptype);
            addCropRepository.save(obj);
            return new ResponseEntity<>("Crop Added Successfully", HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("Crop Type Mismatch",HttpStatus.OK);
    }

    @GetMapping("/getAllCrop")
    public ResponseEntity<?> getCrop()
    {
        List<AddCrop> cropList=addCropRepository.findAll();
        return new ResponseEntity<>(cropList,HttpStatus.OK);
    }

    @GetMapping("/editcrop/{id}")
    public ResponseEntity<?> editCrop(@PathVariable Integer id)
    {
        Optional<AddCrop> Ac=addCropRepository.findById(id);
        if(Ac.isPresent())
        {
            AddCrop addCrop=Ac.get();
            return new ResponseEntity<>(addCrop,HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("Crop Id not found",HttpStatus.OK);
    }

    @PutMapping("/editPrice/{editid}/{croptypeid}")
    public ResponseEntity<?> editPrice(@PathVariable Integer editid,@RequestBody AddCrop obj,@PathVariable Integer croptypeid)
    {
        Optional<AddCrop> Ac=addCropRepository.findById(editid);
        Optional<AddCroptype> At=addCroptypeRepository.findById(croptypeid);
        if(Ac.isPresent() && At.isPresent())
        {
            AddCroptype addCroptype=At.get();
            AddCrop addCrop=Ac.get();
            addCrop.setCropname(obj.getCropname());
            addCrop.setRate(obj.getRate());
            addCrop.setAddCroptype(addCroptype);
            addCropRepository.save(addCrop);
            return new ResponseEntity<>("Crop Details Edited Successfully",HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("Crop Id or Crop type id Mismatch",HttpStatus.OK);
    }

}
