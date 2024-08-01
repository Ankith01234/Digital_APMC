package com.springboot.Repository;

import com.springboot.Entity.FarmerSales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FarmerSalesRepository extends JpaRepository<FarmerSales,Integer>
{
    @Query("select fs from FarmerSales fs where fs.salesdate between ?1 and ?2 and fs.addFarmer.fid=?3")
    List<FarmerSales> findByAddFarmerFid(String startdate,String enddate,Integer id);

    @Query("select fs from FarmerSales fs where fs.salesdate between ?1 and ?2 and fs.farmersalesapmcid.id=?3")
    List<FarmerSales> findBySalesOnDate(String startdate,String enddate,Integer id);

}
