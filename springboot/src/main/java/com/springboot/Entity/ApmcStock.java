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
public class ApmcStock
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int asid;  //apmcstockid

    @ManyToOne
    @JoinColumn(name = "apmcid")
    private AddApmc apmcStockAmpcid;

    @OneToOne
    @JoinColumn(name="cpid")
    private AddCrop addCrop;

    private int quantity;

}
