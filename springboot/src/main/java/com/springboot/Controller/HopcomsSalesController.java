package com.springboot.Controller;

import com.springboot.Entity.AddCrop;
import com.springboot.Entity.AddCroptype;
import com.springboot.Entity.HopcomesSignup;
import com.springboot.Entity.HopcomsSales;
import com.springboot.Repository.AddCropRepository;
import com.springboot.Repository.AddCroptypeRepository;
import com.springboot.Repository.HopcomesSignupRepository;
import com.springboot.Repository.HopcomsSalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
public class HopcomsSalesController
{

    @Autowired
    HopcomsSalesRepository hopcomsSalesRepository;

    @Autowired
    HopcomesSignupRepository hopcomesSignupRepository;

    @Autowired
    AddCropRepository addCropRepository;

    @PostMapping("/addSales/{hpid}/{cropid}")  //This api used to add the Sales to the table
    public ResponseEntity<?> addSalesToHopcoms(@PathVariable String hpid, @PathVariable Integer cropid, @RequestBody HopcomsSales obj)
    {
        Optional<HopcomesSignup> Hs=hopcomesSignupRepository.findById(Integer.parseInt(hpid));
        Optional<AddCrop> Ac=addCropRepository.findById(cropid);
        if(Hs.isPresent() && Ac.isPresent())
        {
            HopcomesSignup hopcomesSignup=Hs.get();
            AddCrop addCrop=Ac.get();
            obj.setSalesCrop(addCrop);
            obj.setSalesHpid(hopcomesSignup);
            Date d=new Date();
            SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
            String date=dateFormat.format(d);
            obj.setDate(date);
            hopcomsSalesRepository.save(obj);
            return new ResponseEntity<>("Sales added Successfully", HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("Hopcomes or Crop Id Mismatch",HttpStatus.OK);
    }

    @GetMapping("/getHopcomsSales/{stdate}/{enddate}/{hpid}")// This Api used to display the Hopcoms Sales
    public ResponseEntity<?> getSales(@PathVariable String stdate,@PathVariable String enddate,@PathVariable String hpid)
    {
        List<HopcomsSales> salesList=hopcomsSalesRepository.findByHopComsSales(stdate,enddate,Integer.parseInt(hpid));
        return new ResponseEntity<>(salesList,HttpStatus.OK);
    }

    @GetMapping("/cropDemand/{ctid}")
    public ResponseEntity<?> getCropSalesFromHopComs(@PathVariable Integer ctid)
    {
        List<Object> salesList=hopcomsSalesRepository.findBySalesCropAddCroptypeCtid(ctid);
        return new ResponseEntity<>(salesList,HttpStatus.OK);
    }

}
