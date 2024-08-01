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
public class HopcomsStock
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int stockid;

    @ManyToOne
    @JoinColumn(name="hpid")
    private HopcomesSignup hopcomesStockhpid;

    @ManyToOne
    @JoinColumn(name="cpid")
    private AddCrop hopcomsStockCpid;

    private int quantity;

}
