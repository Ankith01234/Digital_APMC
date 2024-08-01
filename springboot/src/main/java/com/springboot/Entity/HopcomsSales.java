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
public class HopcomsSales
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sid; //salesid

    @ManyToOne
    @JoinColumn(name="hid")
    private HopcomesSignup salesHpid;

    @ManyToOne
    @JoinColumn(name="cid") //cropid
    private AddCrop salesCrop;

    private String date;
    private int quantity;

}
