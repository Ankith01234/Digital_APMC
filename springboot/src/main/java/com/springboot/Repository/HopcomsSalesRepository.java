package com.springboot.Repository;

import com.springboot.Entity.HopcomsSales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HopcomsSalesRepository extends JpaRepository<HopcomsSales,Integer>
{
    @Query("select hs from HopcomsSales hs where hs.date between ?1 and ?2 and hs.salesHpid.hpid=?3")
    List<HopcomsSales> findByHopComsSales(String startdate,String endadte,Integer id);

    @Query("select sum(hs.quantity) as TotalCount,ac.cropname from HopcomsSales hs inner join AddCrop ac on hs.salesCrop.cid=ac.cid where ac.addCroptype.ctid=?1 group by hs.salesCrop.cid")
    List<Object> findBySalesCropAddCroptypeCtid(Integer ctid);

}
