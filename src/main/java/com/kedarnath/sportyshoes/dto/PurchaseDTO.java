package com.kedarnath.sportyshoes.dto;

import lombok.Data;

@Data
public class PurchaseDTO {
    private Long purchase_id;
    private String firstname;
    private String lastname;
    private String product_name;
    private String purchase_date;
    private String address_1;
    private String address_2;
    private String phoneno;
    private String email;
    private String postalcode;
    private String city;
    private String product_categoryName;
    private String product_imageName;
    private Long cost;
}
