package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    private Integer id;
    private Integer userId;
    private Integer businessId;
    private Integer goodsId;
    private Integer num;

    private String businessName;
    private String goodsName;
    private String goodsImg;
    private String goodUnit;
    private Double goodsPrice;
}