package com.springboot.Controller;

import com.springboot.Entity.*;
import com.springboot.Repository.*;
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
public class FarmerSalesController
{
    @Autowired
    FarmerSalesRepository farmerSalesRepository;

    @Autowired
    AddFarmerRepository addFarmerRepository;

    @Autowired
    AddApmcRepository addApmcRepository;

    @Autowired
    AddCropRepository addCropRepository;

    @Autowired
    HopcomsSalesRepository hopcomsSalesRepository;

    @PostMapping("/addFarmerSales/{farmerid}/{apmcid}/{cropid}")
    public ResponseEntity<?> addFarmerSalesDetails(@PathVariable Integer farmerid, @PathVariable String apmcid, @PathVariable Integer cropid, @RequestBody FarmerSales obj)
    {
        Optional<AddFarmer> Af=addFarmerRepository.findById(farmerid);
        Optional<AddApmc> A=addApmcRepository.findById(Integer.parseInt(apmcid));
        Optional<AddCrop> Ac=addCropRepository.findById(cropid);
        if(Af.isPresent() && A.isPresent() && Ac.isPresent())
        {
            AddFarmer addFarmer=Af.get();
            AddApmc addApmc=A.get();
            AddCrop addCrop=Ac.get();
            obj.setAddFarmer(addFarmer);
            obj.setAddCrop(addCrop);
            obj.setFarmersalesapmcid(addApmc);
            Date d=new Date();
            SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
            String date= dateFormat.format(d);
            obj.setSalesdate(date);
            farmerSalesRepository.save(obj);
            return new ResponseEntity<>("Farmer Sales Added Successfully", HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("Either one of the Id is Mismatch",HttpStatus.OK);
    }

    @GetMapping("/getFarmerSalesDetails/{stdate}/{enddate}/{farmerid}")
    public ResponseEntity<?> getFarmerSalesList(@PathVariable String stdate,@PathVariable String enddate,@PathVariable Integer farmerid)
    {
        //System.out.println(stdate+","+enddate+","+farmerid);
        Optional<AddFarmer> Af=addFarmerRepository.findById(farmerid);
        if(Af.isPresent())
        {
            List<FarmerSales> salesList=farmerSalesRepository.findByAddFarmerFid(stdate,enddate,farmerid);
            return new ResponseEntity<>(salesList,HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("Farmer Id not Found",HttpStatus.OK);
    }

    @GetMapping("/getSalesBasedOnDates/{stdate}/{enddate}/{apmcid}")
    public ResponseEntity<?> getSales(@PathVariable String stdate,@PathVariable String enddate,@PathVariable String apmcid)
    {
        Optional<AddApmc> A=addApmcRepository.findById(Integer.parseInt(apmcid));
        if(A.isPresent())
        {
            List<FarmerSales> salesList=farmerSalesRepository.findBySalesOnDate(stdate,enddate,Integer.parseInt(apmcid));
            return new ResponseEntity<>(salesList,HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("Apmc is not found",HttpStatus.OK);
    }

}

//  .   ____          _            __ _ _
// /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
//( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
// \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
//  '  |____| .__|_| |_|_| |_\__, | / / / /
// =========|_|==============|___/=/_/_/_/
