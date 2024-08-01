package com.springboot.Controller;

import com.springboot.Entity.AddApmc;
import com.springboot.Entity.AddCrop;
import com.springboot.Entity.AddFarmer;
import com.springboot.Entity.ApmcStock;
import com.springboot.Repository.AddApmcRepository;
import com.springboot.Repository.AddCropRepository;
import com.springboot.Repository.ApmcStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
public class ApmcStockController
{
    @Autowired
    ApmcStockRepository apmcStockRepository;

    @Autowired
    AddCropRepository addCropRepository;

    @Autowired
    AddApmcRepository addApmcRepository;

    @GetMapping("/confirmCropid/{cropid}")
    public ResponseEntity<?> confirmCropId(@PathVariable Integer cropid)
    {
        Optional<ApmcStock> As=apmcStockRepository.findByAddCropCid(cropid);
        if(As.isPresent())
            return new ResponseEntity<>("Crop Available", HttpStatus.OK);
        else
            return new ResponseEntity<>("Crop id not found", HttpStatus.OK);
    }

    @PostMapping("/addApmcStock/{apmcid}/{cropid}")
    public ResponseEntity<?> addAPMCStock(@PathVariable String apmcid,@PathVariable Integer cropid,@RequestBody ApmcStock obj)
    {
        Optional<AddCrop> Ac=addCropRepository.findById(cropid);
        Optional<AddApmc> A=addApmcRepository.findById(Integer.parseInt(apmcid));
        if(Ac.isPresent() && A.isPresent())
        {
            AddApmc addApmc=A.get();
            AddCrop addCrop=Ac.get();
            obj.setAddCrop(addCrop);
            obj.setApmcStockAmpcid(addApmc);
            apmcStockRepository.save(obj);
            return new ResponseEntity<>("ApmcStock added Successfully",HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("Crop Id Mismatch",HttpStatus.OK);
    }

    @PutMapping("/updateApmcStock/{cropid}")
    public ResponseEntity<?> updateAPMCStock(@PathVariable Integer cropid,@RequestBody ApmcStock obj)
    {
        Optional<ApmcStock> As=apmcStockRepository.findByAddCropCid(cropid);
        if(As.isPresent())
        {
            ApmcStock apmcStock=As.get();
            apmcStock.setQuantity(apmcStock.getQuantity()+obj.getQuantity());
            apmcStockRepository.save(apmcStock);
            return new ResponseEntity<>("Apmc Stock Updated Successfully",HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("Crop Id Mismatch in Apmc Stock",HttpStatus.OK);
    }

    @GetMapping("/getApmcStock/{apmcid}/{cropid}")
    public ResponseEntity<?> getStock(@PathVariable Integer apmcid,@PathVariable Integer cropid)
    {
        Optional<ApmcStock> As=apmcStockRepository.findByAddCropCidAndApmcStockAmpcidId(cropid,apmcid);
        if(As.isPresent())
        {
            ApmcStock apmcStock=As.get();
            return new ResponseEntity<>(apmcStock,HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("Stock not found",HttpStatus.OK);
    }

    @GetMapping("/getApmcAllStock/{apmcid}") //This Api used to display the Apmc stock
    public ResponseEntity<?> getAllStock(@PathVariable String apmcid)
    {
        Optional<AddApmc> A=addApmcRepository.findById(Integer.parseInt(apmcid));
        if(A.isPresent())
        {
            List<ApmcStock> stockList=apmcStockRepository.findByApmcStockAmpcidId(Integer.parseInt(apmcid));
            return new ResponseEntity<>(stockList,HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("Apmc Id not found",HttpStatus.OK);
    }

}
