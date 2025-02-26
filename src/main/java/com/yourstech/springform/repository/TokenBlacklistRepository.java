package com.yourstech.springform.repository;

import com.yourstech.springform.model.TokenBlacklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenBlacklistRepository extends JpaRepository<TokenBlacklist, Integer> {
    boolean existsByToken(String token);
}
