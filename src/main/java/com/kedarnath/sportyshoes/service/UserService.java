package com.kedarnath.sportyshoes.service;

import com.kedarnath.sportyshoes.model.User;
import com.kedarnath.sportyshoes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    public List<User> findByKeyword(String keyword){
        return userRepository.findByKeyword(keyword);
    }


}
