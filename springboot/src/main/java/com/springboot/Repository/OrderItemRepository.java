package com.springboot.Repository;

import com.springboot.Entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.parser.Entity;
import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer>
{
    List<OrderItem> findByOrderItemApmcIdIdAndStatus(Integer id,String status);

    @Query("select ot from OrderItem ot where ot.date between ?1 and ?2 and ot.orderItemApmcId.id=?3 and ot.status=?4")
    List<OrderItem> findByHopComeTransaction(String startdate,String enddate,Integer id,String status);
}
