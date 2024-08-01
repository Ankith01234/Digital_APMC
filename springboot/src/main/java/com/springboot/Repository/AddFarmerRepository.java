package com.springboot.Repository;

import com.springboot.Entity.AddFarmer;
import com.springboot.Entity.FarmerSales;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddFarmerRepository extends JpaRepository<AddFarmer,Integer>
{

}
