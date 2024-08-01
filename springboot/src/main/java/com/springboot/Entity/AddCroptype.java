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
public class AddCroptype
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ctid;

    private String ctname; //Crop type Name

    @OneToMany(mappedBy = "addCroptype")
    @JsonIgnore
    private List<AddCrop> addCropList;
}
