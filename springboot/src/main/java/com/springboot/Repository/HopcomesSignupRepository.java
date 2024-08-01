package com.springboot.Repository;

import com.springboot.Entity.HopcomesSignup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HopcomesSignupRepository extends JpaRepository<HopcomesSignup,Integer>
{
    Optional<HopcomesSignup> findByEmail(String email);
}
