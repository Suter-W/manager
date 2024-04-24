package com.example.service;

import com.example.entity.Account;
import com.example.entity.Goods;
import com.example.entity.Interest;
import com.example.entity.User;
import com.example.mapper.InterestMapper;
import com.example.mapper.UserMapper;
import com.example.utils.TokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class InterestService {
    @Resource
    private InterestMapper interestMapper;

    @Resource
    private UserMapper userMapper;

    public void add(List<Integer> typeIds) {
        Account currentUser = TokenUtils.getCurrentUser();
        for(Integer typeId : typeIds){
            Interest temp = new Interest();
            temp.setUserId(currentUser.getId());
            temp.setTypeId(typeId);
            interestMapper.insert(temp);
        }
        User tmpUser = new User();
        tmpUser.setId(currentUser.getId());
        tmpUser.setIsNew(0);
        userMapper.updateById(tmpUser);
    }
}
