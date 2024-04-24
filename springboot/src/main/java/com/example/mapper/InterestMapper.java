package com.example.mapper;

import com.example.entity.Goods;
import com.example.entity.Interest;

import java.util.List;

public interface InterestMapper {
    /**
     * 新增
     */
    int insert(Interest interest);

    /**
     * 删除
     */
    int deleteById(Integer id);

    /**
     * 修改
     */
    int updateById(Goods goods);

    /**
     * 根据ID查询
     */
    List<Interest> selectByUserId(Integer userId);

    /**
     * 查询所有
     */
//    List<Goods> selectAll(Goods goods);
}
