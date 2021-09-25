package com.kedarnath.sportyshoes.repository;

import com.kedarnath.sportyshoes.model.Product;
import com.kedarnath.sportyshoes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByEmail(String email);


    @Query(value = "select * from Users u where lower(u.first_name) like :keyword ",nativeQuery = true)
    List<User> findByKeyword(String keyword);
}
