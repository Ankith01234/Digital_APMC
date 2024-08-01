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
public class OrderItemController
{

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    AddApmcRepository addApmcRepository;

    @Autowired
    HopcomesSignupRepository hopcomesSignupRepository;

    @Autowired
    ApmcStockRepository apmcStockRepository;

    @Autowired
    AddCropRepository addCropRepository;

    @PostMapping("/orderItemToApmc/{apmcid}/{hpid}/{cpid}")
    public ResponseEntity<?> orderItem(@PathVariable Integer apmcid, @PathVariable String hpid, @PathVariable Integer cpid, @RequestBody OrderItem obj)
    {
        Optional<AddApmc> A=addApmcRepository.findById(apmcid);
        Optional<HopcomesSignup> Hs=hopcomesSignupRepository.findById(Integer.parseInt(hpid));
        Optional<AddCrop> Ac=addCropRepository.findById(cpid);
        Optional<ApmcStock> As =apmcStockRepository.findByAddCropCidAndApmcStockAmpcidId(cpid,apmcid);
        if(As.isPresent())
        {
            if(A.isPresent() && Hs.isPresent() && Ac.isPresent())
            {
                AddApmc addApmc=A.get();
                HopcomesSignup hopcomesSignup=Hs.get();
                AddCrop addCrop=Ac.get();
                obj.setOrderItemApmcId(addApmc);
                obj.setOrderItemHpid(hopcomesSignup);
                obj.setOrderItemCpid(addCrop);
                obj.setStatus("Pending");
                Date d=new Date();
                SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
                String date=dateFormat.format(d);
                obj.setDate(date);
                orderItemRepository.save(obj);
                return new ResponseEntity<>("Order Items Placed Successfully",HttpStatus.OK);
            }
            else
                return new ResponseEntity<>("Crop Id,Hopcomsid or Apmcid Mismatch",HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("Item is not Present in the Apmc", HttpStatus.OK);
    }

    @GetMapping("/getOrderItems/{apmcid}")   //  Orders Placed By the Hopcoms Viewed By the Apmc
    public ResponseEntity<?> getItems(@PathVariable String apmcid)
    {
        Optional<AddApmc> A=addApmcRepository.findById(Integer.parseInt(apmcid));
        if(A.isPresent())
        {
            List<OrderItem> orderItemList=orderItemRepository.findByOrderItemApmcIdIdAndStatus(Integer.parseInt(apmcid),"Pending");
            return new ResponseEntity<>(orderItemList,HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("Apmc Id Mismatch",HttpStatus.OK);
    }

    @PutMapping("/updateApmcStocks/{orderid}")//It checks Apmc Stocks
    public ResponseEntity<?> updateStock(@PathVariable Integer orderid)
    {
        Optional<OrderItem> Ot=orderItemRepository.findById(orderid);
        if(Ot.isPresent())
        {
            OrderItem orderItem=Ot.get();
            Optional<ApmcStock> As=apmcStockRepository.findByAddCropCid(orderItem.getOrderItemCpid().getCid());
            if(As.isPresent())
            {
                ApmcStock apmcStock=As.get();
                if(apmcStock.getQuantity()>=orderItem.getQuantity())
                {
                    apmcStock.setQuantity(apmcStock.getQuantity()-orderItem.getQuantity());
                    orderItem.setStatus("Completed");
                    orderItemRepository.save(orderItem);
                    apmcStockRepository.save(apmcStock);
                    return new ResponseEntity<>("Sufficient Stock",HttpStatus.OK);
                }
                else
                    return new ResponseEntity<>("Not Having Enough stock",HttpStatus.OK);
            }
            else
                return new ResponseEntity<>("Apmc Stock not found",HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("Order Id not found",HttpStatus.OK);
    }

    @GetMapping("/getItemsDeliveredToHopcoms/{stdate}/{enddate}/{apmcid}") //This Api is used to display the delivered items from Apmc to Hopcoms
    public ResponseEntity<?> getItem(@PathVariable String stdate,@PathVariable String enddate,@PathVariable String apmcid)
    {
        List<OrderItem> deliveredList=orderItemRepository.findByHopComeTransaction(stdate,enddate,Integer.parseInt(apmcid),"Completed");
        return new ResponseEntity<>(deliveredList,HttpStatus.OK);
    }

}
