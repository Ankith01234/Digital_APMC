package com.springboot.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AddCrop
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cid;

    private String cropname;

    @ManyToOne
    @JoinColumn(name="ctid")
    private AddCroptype addCroptype;

    private int rate;

    @OneToMany(mappedBy = "addCrop")
    @JsonIgnore
    private List<FarmerSales> farmerSales;

    @OneToMany(mappedBy = "orderItemCpid")
    @JsonIgnore
    private List<OrderItem> orderItem;

    @OneToMany(mappedBy = "hopcomsStockCpid")
    @JsonIgnore
    private List<HopcomsStock> hopcomsStock;

    @OneToMany(mappedBy = "salesCrop")
    @JsonIgnore
    private List<HopcomsSales> hopcomsSalesList;

}
