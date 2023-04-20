package com.videocloud.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.videocloud.entity.Recommend;
import com.videocloud.entity.VedioInfo;
import com.videocloud.entity.VideoHistory;
import com.videocloud.entity.VideoTypeEntity;
import com.videocloud.mapper.VedioInfoMapper;
import com.videocloud.mapper.VideoHistoryMapper;
import com.videocloud.mapper.VideoTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Author：Saika(jiangtao_liu)
 * Date：2023/4/20
 * Description：
 */

public class RecommendUtil {
    /**
     * 根据用户近一个月内的行为进行首页推荐
     * @param limit
     * @param uid
     * @return
     */
    public static List<VedioInfo> activeRecommend(Integer limit,Integer uid,List<Integer> watchIds,Date start,
                                           VideoHistoryMapper videoHistoryMapper,
                                           VideoTypeMapper videoTypeMapper,
                                           VedioInfoMapper vedioInfoMapper){

        //查询一个月内用户的视频播放类型记录数
        List<Recommend> playRecommends = videoHistoryMapper.playHistory(uid,start);

        //查询用户一个月内的播放总数
        QueryWrapper<VideoHistory> queryWrapper1 = new QueryWrapper<VideoHistory>().eq("user_id",uid);
        double total = videoHistoryMapper.selectCount(queryWrapper1);

        //计算每种类型占用户观看总数的百分比的区间
        List<String> userTypes = new ArrayList<>();
        for (int i=0;i<playRecommends.size();i++) {
            if (i == 0) {
                playRecommends.get(i).setPercent(playRecommends.get(i).getCount()/total);
            }else{
                playRecommends.get(i).setPercent(playRecommends.get(i).getCount()/total+playRecommends.get(i-1).getPercent());
            }
            if (playRecommends.get(i).getType() != null) {
                userTypes.add(playRecommends.get(i).getType());//得到用户观看的所有类型
            }else{
                playRecommends.remove(i);
                i = i-1;
            }
        }
        //对库内的视频按照用户看过的类型和没看过的类型进行分组
        QueryWrapper<VideoTypeEntity> queryWrapper2 = new QueryWrapper<VideoTypeEntity>().in("name",userTypes);
        QueryWrapper<VideoTypeEntity> queryWrapper3 = new QueryWrapper<VideoTypeEntity>().notIn("name",userTypes);
        List<VideoTypeEntity> playVideoTypes = videoTypeMapper.selectList(queryWrapper2);
        List<VideoTypeEntity> otherVideoTypes = videoTypeMapper.selectList(queryWrapper3);

        //设置首页展示的视频,多少为用户看过的类型推荐(80%),多少为没看过的类型推荐(20%)
        Integer userRecommendCount = limit*8/10;
        Integer otherRecommendCount = limit - userRecommendCount;

        //按比例随机推荐的所有展示视频对应的种类
        //从用户常看的类型中添加
        Map<String,Integer> typesMap = new HashMap<>();
        for (int i=1; i<=userRecommendCount;i++){
            Double ran = Math.random();
            int index = 0;
            for (int j = 0;j<playVideoTypes.size();j++) {
                if (ran < playRecommends.get(j).getPercent()){
                    index = j;
                    break;
                }
            }
            if (typesMap.get(playRecommends.get(index).getType()) == null) {
                typesMap.put(playRecommends.get(index).getType(),1);
            }else{
                typesMap.put(playRecommends.get(index).getType(),typesMap.get(playRecommends.get(index).getType())+1);
            }
        }

        //从用户不看的类型中添加
        for (int i=1;i <= otherRecommendCount;i++){
            int v = (int)(Math.random() * otherVideoTypes.size());
            if (typesMap.get(otherVideoTypes.get(v).getName()) == null) {
                typesMap.put(otherVideoTypes.get(v).getName(),1);
            }else{
                typesMap.put(otherVideoTypes.get(v).getName(),typesMap.get(otherVideoTypes.get(v).getName())+1);
            }
        }

        List<VedioInfo> rsList = new ArrayList<>();
        List<Integer> recommendIds = new ArrayList<>();
        Set<Map.Entry<String, Integer>> entries = typesMap.entrySet();
        for (Map.Entry<String, Integer> entry : entries) {
            String key = entry.getKey();
            QueryWrapper<VedioInfo> queryWrapper = new QueryWrapper<VedioInfo>().eq("type",key).notIn("id",watchIds);
            List<VedioInfo> vedioInfoList = vedioInfoMapper.selectList(queryWrapper);
            for (int i = 0;i<entry.getValue();i++){
                if (vedioInfoList.size() != 0) {
                    int v = (int)(Math.random() * vedioInfoList.size());
                    rsList.add(vedioInfoList.get(v));
                    recommendIds.add(vedioInfoList.get(v).getId());
                    vedioInfoList.remove(v);
                }
            }
        }

        recommendIds.addAll(watchIds);
        if (rsList.size() < limit){
            QueryWrapper<VedioInfo> queryWrapper = new QueryWrapper<VedioInfo>().notIn("id",recommendIds);
            List<VedioInfo> batchList = vedioInfoMapper.selectList(queryWrapper);
            for (int i = rsList.size();i<limit;i++){
                if (batchList.size() != 0) {
                    int v = (int)(Math.random() * batchList.size());
                    rsList.add(batchList.get(v));
                    recommendIds.add(batchList.get(v).getId());
                    batchList.remove(v);
                }
            }
        }
        return rsList;
    }



    public static List<VedioInfo> randomRecommend(Integer limit,
                                           VideoHistoryMapper videoHistoryMapper,
                                           VideoTypeMapper videoTypeMapper,
                                           VedioInfoMapper vedioInfoMapper){
        //查询所有的类型
        List<VideoTypeEntity> videoTypes = videoTypeMapper.selectList(null);


        //按比例随机推荐的所有展示视频对应的种类
        //从用户常看的类型中添加
        Map<String,Integer> typesMap = new HashMap<>();
        //从用户不看的类型中添加
        for (int i=1;i <= limit;i++){
            int v = (int)(Math.random() * videoTypes.size());
            if (typesMap.get(videoTypes.get(v).getName()) == null) {
                typesMap.put(videoTypes.get(v).getName(),1);
            }else{
                typesMap.put(videoTypes.get(v).getName(),typesMap.get(videoTypes.get(v).getName())+1);
            }
        }

        List<VedioInfo> rsList = new ArrayList<>();
        List<Integer> recommendIds = new ArrayList<>();
        Set<Map.Entry<String, Integer>> entries = typesMap.entrySet();
        for (Map.Entry<String, Integer> entry : entries) {
            String key = entry.getKey();
            QueryWrapper<VedioInfo> queryWrapper = new QueryWrapper<VedioInfo>().eq("type",key);
            List<VedioInfo> vedioInfoList = vedioInfoMapper.selectList(queryWrapper);

            for (int i = 0;i<entry.getValue();i++){
                if (vedioInfoList.size() != 0) {
                    int v = (int)(Math.random() * vedioInfoList.size());
                    rsList.add(vedioInfoList.get(v));
                    recommendIds.add(vedioInfoList.get(v).getId());
                    vedioInfoList.remove(v);
                }
            }
        }

        if (rsList.size() < limit){
            QueryWrapper<VedioInfo> queryWrapper = new QueryWrapper<VedioInfo>().notIn("id",recommendIds);
            List<VedioInfo> batchList = vedioInfoMapper.selectList(queryWrapper);
            for (int i = rsList.size();i<limit;i++){
                if (batchList.size() != 0) {
                    int v = (int)(Math.random() * batchList.size());
                    rsList.add(batchList.get(v));
                    recommendIds.add(batchList.get(v).getId());
                    batchList.remove(v);
                }
            }
        }
        return rsList;
    }


}
