package com.example.controller;

import com.example.common.Result;
import com.example.entity.DataDTO;
import com.example.entity.Goods;
import com.example.entity.Interest;
import com.example.service.GoodsService;
import com.example.service.InterestService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/interest")
public class InterestController {
    @Resource
    private InterestService interestService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody DataDTO data) {
        List<Integer> typeIds = data.getTypeIds();
        interestService.add(typeIds);
        return Result.success();
    }

}
