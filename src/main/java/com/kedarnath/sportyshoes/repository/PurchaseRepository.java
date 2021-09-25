package com.kedarnath.sportyshoes.repository;


import com.kedarnath.sportyshoes.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {
    @Query(value = "select * from Purchase p where lower(p.FIRSTNAME) like :keyword ",nativeQuery = true)
    List<Purchase> findByKeyword(String keyword);
}
