package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orders implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Integer id;
    private Integer userId;
    private Integer businessId;
    private Integer goodsId;
    private String orderId;
    private Integer addressId;
    private Integer num;
    private Double price;
    private String status;

    private List<Cart> cartData;


    private String businessName;
    private String goodsName;
    private String goodsImg;
    private String goodsUnit;
    private Double goodsPrice;
    private String username;
    private String useraddress;
    private String phone;
}