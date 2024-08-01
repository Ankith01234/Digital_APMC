package com.springboot.Repository;

import com.springboot.Entity.ApmcStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApmcStockRepository extends JpaRepository<ApmcStock,Integer>
{
    Optional<ApmcStock> findByAddCropCid(Integer id);

    Optional<ApmcStock> findByAddCropCidAndApmcStockAmpcidId(Integer cpid,Integer apid);

    List<ApmcStock> findByApmcStockAmpcidId(Integer id);
}
