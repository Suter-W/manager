package com.example.utils;

import com.example.common.enums.OccupationEnum;
import com.example.common.enums.RegionEnum;
import com.example.entity.RelateDTO;
import com.example.entity.User;

import java.util.*;
import java.util.stream.Collectors;

public class SimilarUserFinder {

    /**
     * 将用户信息转换成向量
     * @param user 用户
     * @return 信息向量
     */
    public static List<Integer> toVector(User user){

        List<Integer> infoVector = new ArrayList<>();
        List<Integer> regionVector;
        List<Integer> occupationVector;
        if(user.getRegion() != null){
        regionVector = RegionEnum.valueOf(user.getRegion()).getVector();
        }else{
            regionVector = RegionEnum.getEmptyVector();
        }
        if(user.getRegion() != null){
            occupationVector = OccupationEnum.valueOf(user.getOccupation()).getVector();
        }else{
            occupationVector = OccupationEnum.getEmptyVector();
        }
        infoVector.addAll(regionVector);
        infoVector.addAll(occupationVector);
        if(Objects.equals(user.getGender(), "男")){
            infoVector.add(1);
        }else{
            infoVector.add(0);
        }
        infoVector.add(user.getAge() != null ? user.getAge() : 0);
        return infoVector;
    }

    public static List<Integer> recommend1(User currentUser, List<RelateDTO> data, Map<Integer,List<Integer>> infoMap){
        List<Integer> res;
        Map<Integer,Double> similarities = new HashMap<>();
        List<Integer> currentInfo = toVector(currentUser);
        int currentAge = currentInfo.get(41);
        infoMap.forEach((id,info) -> {
            if(!id.equals(currentUser.getId())){
                if (Math.abs(info.get(41) - currentInfo.get(41)) >= 4){     //检验是否同龄
                    info.set(41,0);
                    currentInfo.set(41,0);
                }
                else{
                    info.set(41,1);
                    currentInfo.set(41,1);
                }
                Double cosineSimilarity = CoreMath.cosineSimilarity(currentInfo,info);
                similarities.put(id,cosineSimilarity);
                currentInfo.set(41,currentAge);
            }
        });
        final double[] maxSimilarity = {Double.MIN_VALUE};
        final int[] maxSimilarUserId = {-1};

        // 遍历Map，并更新最大相似度和对应的用户ID
        similarities.forEach((userId, similarity) -> {
            if (similarity > maxSimilarity[0]) {
                maxSimilarity[0] = similarity;
                maxSimilarUserId[0] = userId;
            }
        });
        List<RelateDTO> tmp = new ArrayList<>();
        for(RelateDTO r : data){
            if(r.getUserId() == maxSimilarUserId[0] && r.getIndex() != 0){
                tmp.add(r);
            }
        }
        tmp.sort(Comparator.comparing(RelateDTO::getIndex));
        res = tmp.stream()
                 .limit(5)
                 .map(RelateDTO::getGoodsId)
                 .collect(Collectors.toList());
        return res;
    }
}
