package com.springboot.Controller;

import com.springboot.Entity.AddCrop;
import com.springboot.Entity.HopcomesSignup;
import com.springboot.Entity.HopcomsStock;
import com.springboot.Entity.OrderItem;
import com.springboot.Repository.HopcomesSignupRepository;
import com.springboot.Repository.HopcomsStockRepository;
import com.springboot.Repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
public class HopcomsStockController
{
    @Autowired
    HopcomsStockRepository hopcomsStockRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    HopcomesSignupRepository hopcomesSignupRepository;

    @GetMapping("/chkHopcomsStock/{orderid}")  //Checks Hopcomes Stock
    public ResponseEntity<?> chkItem(@PathVariable Integer orderid)
    {
        Optional<OrderItem> Ot=orderItemRepository.findById(orderid);
        if(Ot.isPresent())
        {
            OrderItem orderItem=Ot.get();
            Optional<HopcomsStock> Hs=hopcomsStockRepository.findByHopcomsStockCpidCidAndHopcomesStockhpidHpid(orderItem.getOrderItemCpid().getCid(),orderItem.getOrderItemHpid().getHpid());
            if(Hs.isPresent())
                return new ResponseEntity<>("Stock Available",HttpStatus.OK);
            else
                return new ResponseEntity<>("Stock Unavailable",HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("Order Item Id not found", HttpStatus.OK);
    }

    @PutMapping("/updateHopcomsStock/{orderid}") //Use This Api if Stock is Available
    public ResponseEntity<?> addHopcomsSTOCK(@PathVariable Integer orderid)
    {
        Optional<OrderItem> Ot=orderItemRepository.findById(orderid);
        if(Ot.isPresent())
        {
            OrderItem orderItem=Ot.get();
            Optional<HopcomsStock> Hs=hopcomsStockRepository.findByHopcomsStockCpidCidAndHopcomesStockhpidHpid(orderItem.getOrderItemCpid().getCid(),orderItem.getOrderItemHpid().getHpid());
            if(Hs.isPresent())
            {
                HopcomsStock hopcomsStock=Hs.get();
                hopcomsStock.setQuantity(hopcomsStock.getQuantity()+orderItem.getQuantity());
                hopcomsStockRepository.save(hopcomsStock);
                return new ResponseEntity<>("Hopcoms Stock Updated Successfully",HttpStatus.OK);
            }
            else
                return new ResponseEntity<>("Stock not found in Hopcoms",HttpStatus.OK);
        }
        return new ResponseEntity<>("Order Item not found",HttpStatus.OK);
    }

    @PostMapping("/addHopcomsStock/{orderid}")  //Use This Api if Stock is not Available
    public ResponseEntity<?> addHopcomsSTOCKS(@PathVariable Integer orderid)
    {
        Optional<OrderItem> Ot=orderItemRepository.findById(orderid);
        if(Ot.isPresent())
        {
            OrderItem orderItem=Ot.get();
            HopcomsStock hopcomsStock=new HopcomsStock();
            hopcomsStock.setHopcomesStockhpid(orderItem.getOrderItemHpid());
            hopcomsStock.setHopcomsStockCpid(orderItem.getOrderItemCpid());
            hopcomsStock.setQuantity(orderItem.getQuantity());
            hopcomsStockRepository.save(hopcomsStock);
            return new ResponseEntity<>("New Stock added Successfully",HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("Order Id not found",HttpStatus.OK);
    }

    @GetMapping("/getHopcomsStock/{hpid}")
    public ResponseEntity<?> getHopcomeSTOCK(@PathVariable Integer hpid)
    {
        Optional<HopcomesSignup> Hs=hopcomesSignupRepository.findById(hpid);
        if(Hs.isPresent())
        {
            List<HopcomsStock> hopcomsStock=hopcomsStockRepository.findByHopcomesStockhpidHpid(hpid);
            return new ResponseEntity<>(hopcomsStock,HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("Hopcomes Id not found",HttpStatus.OK);
    }

    @GetMapping("/getHopcomsStockQty/{hpid}/{cropid}") //This Api used to display the Qty of the Crop based on the Hopcoms
    public ResponseEntity<?> getQty(@PathVariable String hpid,@PathVariable Integer cropid)
    {
        Optional<HopcomsStock> Hs=hopcomsStockRepository.findByHopcomsStockCpidCidAndHopcomesStockhpidHpid(cropid,Integer.parseInt(hpid));
        if(Hs.isPresent())
        {
            HopcomsStock hopcomsStock=Hs.get();
            return new ResponseEntity<>(hopcomsStock.getQuantity(),HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("Stock Unavailable",HttpStatus.OK);
    }

    @PutMapping("/reduceHopcomsStock/{hpid}/{cropid}") //This Api used to reduce the stock in the Hopcoms
    public ResponseEntity<?> reduceStock(@PathVariable String hpid,@PathVariable Integer cropid,@RequestBody HopcomsStock obj)
    {
        Optional<HopcomsStock> Hs=hopcomsStockRepository.findByHopcomsStockCpidCidAndHopcomesStockhpidHpid(cropid,Integer.parseInt(hpid));
        if(Hs.isPresent())
        {
            HopcomsStock hopcomsStock=Hs.get();
            if(hopcomsStock.getQuantity()>=obj.getQuantity())
            {
                hopcomsStock.setQuantity(hopcomsStock.getQuantity()-obj.getQuantity());
                hopcomsStockRepository.save(hopcomsStock);
                return new ResponseEntity<>("Sufficient Stock",HttpStatus.OK);
            }
            else
                return new ResponseEntity<>("Insufficient Stock",HttpStatus.OK);
        }
        else
            return new ResponseEntity<>("Stock Unavailable in the Hopcomes",HttpStatus.OK);
    }

}
