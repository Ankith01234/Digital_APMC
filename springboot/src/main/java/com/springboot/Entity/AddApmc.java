package com.springboot.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
public class AddApmc
{
    @Id
    private int id;

    private String apmcname;
    private String password;
    private String email;
    private String address;
    private String phone;

    @OneToMany(mappedBy = "farmersalesapmcid")
    @JsonIgnore
    private List<FarmerSales> farmerSales;

    @OneToMany(mappedBy = "orderItemApmcId")
    @JsonIgnore
    private List<OrderItem> orderItem;

    @OneToMany(mappedBy = "apmcStockAmpcid")
    @JsonIgnore
    private List<ApmcStock> apmcStock;

}
