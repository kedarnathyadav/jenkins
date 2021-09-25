package com.kedarnath.sportyshoes.service;

import com.kedarnath.sportyshoes.model.Purchase;
import com.kedarnath.sportyshoes.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PurchaseService {
    @Autowired
    PurchaseRepository purchaseRepository;

    public List<Purchase> getAllPurchases(){
        return purchaseRepository.findAll();
    }

    public List<Purchase> findByKeyword(String keyword){
        return purchaseRepository.findByKeyword(keyword);
    }
}
