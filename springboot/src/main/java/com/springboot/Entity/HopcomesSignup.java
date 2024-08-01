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
public class HopcomesSignup
{
    @Id
    private int hpid;

    private String hopcomesname;
    private String email;
    private String phone;
    private String address;
    private String password;

    @OneToMany(mappedBy = "orderItemHpid")
    @JsonIgnore
    private List<OrderItem> orderItem;

    @OneToMany(mappedBy = "hopcomesStockhpid")
    @JsonIgnore
    private List<HopcomsStock> hopcomsStocks;

    @OneToMany(mappedBy = "salesHpid")
    @JsonIgnore
    private List<HopcomsSales> hopcomsSales;

}
