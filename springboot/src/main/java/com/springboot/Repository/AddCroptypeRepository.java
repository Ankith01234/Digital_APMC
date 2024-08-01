package com.springboot.Repository;

import com.springboot.Entity.AddCroptype;
import com.springboot.Entity.HopcomsSales;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddCroptypeRepository extends JpaRepository<AddCroptype,Integer>
{
    Optional<AddCroptype> findByCtname(String name);

}
