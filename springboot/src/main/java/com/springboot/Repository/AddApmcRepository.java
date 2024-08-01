package com.springboot.Repository;

import com.springboot.Entity.AddApmc;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddApmcRepository extends JpaRepository<AddApmc,Integer>
{
    Optional<AddApmc> findByEmail(String email);
}
