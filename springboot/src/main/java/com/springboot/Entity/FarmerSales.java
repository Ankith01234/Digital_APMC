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
public class FarmerSales
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fsid;  //farmerssalesid

    @ManyToOne
    @JoinColumn(name = "fid")
    private AddFarmer addFarmer;

    @ManyToOne
    @JoinColumn(name = "apmcid")
    private AddApmc farmersalesapmcid;

    @ManyToOne
    @JoinColumn(name = "cpid")
    private AddCrop addCrop;

    private String salesdate;
    private int quantity;

}
