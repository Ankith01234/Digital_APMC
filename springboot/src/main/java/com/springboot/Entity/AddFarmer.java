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
public class AddFarmer
{
    @Id
    private int fid;

    private String farmername;
    private String address;
    private String phone;
    private String password;

    @OneToMany(mappedBy = "addFarmer")
    @JsonIgnore
    private List<FarmerSales> farmerSalesList;

}
