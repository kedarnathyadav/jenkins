package com.kedarnath.sportyshoes.repository;

import com.kedarnath.sportyshoes.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
