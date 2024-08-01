package com.springboot.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OrderItem
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderid;

    @ManyToOne
    @JoinColumn(name="apmcid")
    private AddApmc orderItemApmcId;

    @ManyToOne
    @JoinColumn(name="hpid")
    private HopcomesSignup orderItemHpid;

    @ManyToOne
    @JoinColumn(name="cpid")
    private AddCrop orderItemCpid;

    private String date;
    private int quantity;
    private String status;

}
