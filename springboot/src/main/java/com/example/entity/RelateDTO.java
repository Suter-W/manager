package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelateDTO {
    /** 用户id */
    private Integer userId;
    /** 商品id */
    private Integer goodsId;
    /** 指数 */
    private Integer index;

}