package com.yourstech.springform.repository;

import com.yourstech.springform.model.AllowedDomain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AllowedDomainRepository extends JpaRepository<AllowedDomain, Integer> {
}
