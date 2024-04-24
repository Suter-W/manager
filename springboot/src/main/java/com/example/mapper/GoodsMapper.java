package com.example.mapper;

import com.example.entity.Goods;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface GoodsMapper {

    /**
     * 新增
     */
    int insert(Goods goods);

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
    Goods selectById(Integer id);

    /**
     * 查询所有
     */
    List<Goods> selectAll(Goods goods);

    @Select("select * from goods order by count desc limit 15")
    List<Goods> selectTop15();

    @Select("select * from goods where type_id = #{id}")
    List<Goods> selectByTypeId(Integer id);

    @Select("select * from goods where business_id = #{id}")
    List<Goods> selectByBusinessId(Integer id);

    @Select("select * from goods where name like concat('%', #{name}, '%')")
    List<Goods> selectByName(String name);

    @Update("update goods set count = count + #{num} where id = #{id}")
    void increaseCount(Integer id, Integer num);
}