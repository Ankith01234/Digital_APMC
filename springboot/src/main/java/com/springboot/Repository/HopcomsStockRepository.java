package com.springboot.Repository;

import com.springboot.Entity.HopcomsStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HopcomsStockRepository extends JpaRepository<HopcomsStock,Integer>
{
    Optional<HopcomsStock> findByHopcomsStockCpidCidAndHopcomesStockhpidHpid(Integer cpid,Integer hpid);

    List<HopcomsStock> findByHopcomesStockhpidHpid(Integer id);

}
