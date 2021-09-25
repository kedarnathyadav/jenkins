package com.kedarnath.sportyshoes.repository;


import com.kedarnath.sportyshoes.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByCategory_Id(int id);

    @Query(value = "select * from Product p where lower(p.name) like :keyword ",nativeQuery = true)
    List<Product> findByKeyword(@Param("keyword") String keyword);
}
