package com.example.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.example.common.enums.RegionEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.*;
import com.example.mapper.*;
import com.example.utils.SimilarUserFinder;
import com.example.utils.TokenUtils;
import com.example.utils.UserCF;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GoodsService {

    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private CollectMapper collectMapper;

    @Resource
    private OrdersMapper ordersMapper;

    @Resource
    private CartMapper cartMapper;

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private InterestMapper interestMapper;



    /**
     * 新增
     */
    public void add(Goods goods) {
        Account currentUser = TokenUtils.getCurrentUser();
        if (RoleEnum.BUSINESS.name().equals(currentUser.getRole())) {
            goods.setBusinessId(currentUser.getId());
        }
        goodsMapper.insert(goods);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        goodsMapper.deleteById(id);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            goodsMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Goods goods) {
        goodsMapper.updateById(goods);
    }

    /**
     * 根据ID查询
     */
    public Goods selectById(Integer id) {
        return goodsMapper.selectById(id);
    }

    /**
     * 查询所有
     */
    public List<Goods> selectAll(Goods goods) {
        return goodsMapper.selectAll(goods);
    }

    /**
     * 分页查询
     */
    public PageInfo<Goods> selectPage(Goods goods, Integer pageNum, Integer pageSize) {
        Account currentUser = TokenUtils.getCurrentUser();
        if (RoleEnum.BUSINESS.name().equals(currentUser.getRole())) {
            goods.setBusinessId(currentUser.getId());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Goods> list = goodsMapper.selectAll(goods);
        return PageInfo.of(list);
    }
    public List<Goods> selectTop15() {
        return goodsMapper.selectTop15();
    }
    public List<Goods> selectByTypeId(Integer id) {
        return goodsMapper.selectByTypeId(id);
    }
    public List<Goods> selectByBusinessId(Integer id) {
        return goodsMapper.selectByBusinessId(id);
    }
    public List<Goods> selectByName(String name) {
        return goodsMapper.selectByName(name);
    }


    //推荐算法主体
    public List<Goods> recommend() {
        Account currentUser = TokenUtils.getCurrentUser();

        if (ObjectUtil.isEmpty(currentUser)) {
            // 没有用户登录
            return new ArrayList<>();
        }
        // 用户的哪些行为可以认为他跟商品产生了关系？收藏、加入购物车、下单、评论
        // 1. 获取所有的收藏信息
        List<Collect> allCollects = collectMapper.selectAll(null);
        // 2. 获取所有的购物车信息
        List<Cart> allCarts = cartMapper.selectAll(null);
        // 3. 获取所有的订单信息
        List<Orders> allOrders = ordersMapper.selectAllOKOrders();
        // 4. 获取所有的评论信息
        List<Comment> allComments = commentMapper.selectAll(null);
        // 5. 获取所有的用户信息
        List<User> allUsers = userMapper.selectAll(null);
        // 6. 获取所有的商品信息
        List<Goods> allGoods = goodsMapper.selectAll(null);
        // 7. 获取当前用户的所有兴趣信息
        List<Interest> interests = interestMapper.selectByUserId(currentUser.getId());

        // 定义一个存储每个商品和每个用户关系的List
        List<RelateDTO> data = new ArrayList<>();
        // 定义一个存储最后返回给前端的商品List
        List<Goods> result = new ArrayList<>();

        User currentUser1 = new User();
        for(User user : allUsers){
            if(Objects.equals(user.getId(), currentUser.getId())){
                currentUser1 = user;
            }
        }

        // 开始计算每个商品和每个用户之间的关系数据
        for (Goods goods : allGoods) {
            Integer goodsId = goods.getId();
            for (User user : allUsers) {
                Integer userId = user.getId();
                int index = 1;
                // 1. 判断该用户有没有收藏该商品，收藏的权重给 1
                Optional<Collect> collectOptional = allCollects.stream().filter(x -> x.getGoodsId().equals(goodsId) && x.getUserId().equals(userId)).findFirst();
                if (collectOptional.isPresent()) {
                    index += 1;
                }
                // 2. 判断该用户有没有给该商品加入购物车，加入购物车的权重给 2
                Optional<Cart> cartOptional = allCarts.stream().filter(x -> x.getGoodsId().equals(goodsId) && x.getUserId().equals(userId)).findFirst();
                if (cartOptional.isPresent()) {
                    index += 2;
                }
                // 3. 判断该用户有没有对该商品下过单（已完成的订单），订单的权重给 3
                Optional<Orders> ordersOptional = allOrders.stream().filter(x -> x.getGoodsId().equals(goodsId) && x.getUserId().equals(userId)).findFirst();
                if (ordersOptional.isPresent()) {
                    index += 4;
                }
                // 4. 判断该用户有没有对该商品评论过，评论的权重给 2
                Optional<Comment> commentOptional = allComments.stream().filter(x -> x.getGoodsId().equals(goodsId) && x.getUserId().equals(userId)).findFirst();
                if (commentOptional.isPresent()) {
                    index += 2;
                }
                if (index > 1) {
                    RelateDTO relateDTO = new RelateDTO(userId, goodsId, index);
                    data.add(relateDTO);
                }
            }
        }
        Map<Integer,List<Integer>> infoMap = new HashMap<>();
        for(User user : allUsers){
            infoMap.put(user.getId(),SimilarUserFinder.toVector(user));
        }
        if(!data.isEmpty()){System.out.println(data.get(2).toString());}

//        for(User user : allUsers){
//            System.out.println(user.getInfoVector());
//        }



        // 数据准备结束后，就把这些数据一起喂给这个推荐算法
        List<Integer> goodsIds = UserCF.recommend(currentUser.getId(), data);
        // 把商品id转换成商品
        List<Goods> recommendResult = goodsIds.stream().map(goodsId -> allGoods.stream()
                        .filter(x -> x.getId().equals(goodsId)).findFirst().orElse(null))
                .limit(15).collect(Collectors.toList());

        // 如果推荐数量小于15个而且用户填写过兴趣倾向，则按照兴趣类别商品销量权值进行推荐
        if(recommendResult.size() < 15 && !interests.isEmpty()){
            List<Goods> interestGoods = new ArrayList<>();
            for(Interest interest : interests){
                for(Goods goods : allGoods){
                    if(Objects.equals(interest.getTypeId(), goods.getTypeId()) && !recommendResult.contains(goods)){
                        interestGoods.add(goods);
                    }
                }
            }
            Integer total = 0;
            for (Goods goods : interestGoods) {
                total += goods.getCount();
            }
            if(total != 0) {
                for (int i = 0; i <= 4; i++) {
                    Integer tmp = 0;

                    int randomNum = new Random().nextInt(total);
                    for (Goods goods : interestGoods) {
                        tmp += goods.getCount();
                        if (randomNum < tmp) {
                            if (!recommendResult.contains(goods)) {
                                recommendResult.add(goods);
                            }
                            break;
                        }
                    }
                }
            }
        }
        if(recommendResult.size() < 15){
            List<Integer> goodsList1= SimilarUserFinder.recommend1(currentUser1,data,infoMap);
            List<Goods> recommendResult1 = goodsList1.stream().map(goodsId -> allGoods.stream()
                            .filter(x -> x.getId().equals(goodsId)).findFirst().orElse(null))
                    .limit(15).collect(Collectors.toList());
            recommendResult.addAll(recommendResult1);
        }

//                if (CollectionUtil.isEmpty(recommendResult)) {
//                    // 随机给它推荐10个
//                    return getRandomGoods(15,null);
//                }
//                if (recommendResult.size() < 15) {
//                    int num = 15 - recommendResult.size();
//                    List<Goods> list = getRandomGoods(num,recommendResult);
//                    recommendResult.addAll(list);
//                }
        return recommendResult;
    }

    private List<Goods> getRandomGoods(int num,List<Goods> existGoods) {
        List<Goods> list = new ArrayList<>(num);
        List<Goods> goods = goodsMapper.selectAll(null);
        List<Goods> availableGoods = goods.stream()
                .filter(g -> !existGoods.contains(g))
                .collect(Collectors.toList());
        for (int i = 0; i < num; i++) {
            int index = new Random().nextInt(availableGoods.size());
            list.add(availableGoods.get(index));
        }
        return list;
    }
}