package com.videocloud.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.videocloud.entity.*;
import com.videocloud.mapper.*;
import com.videocloud.service.IVedioInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.videocloud.util.RecommendUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * <p>
 * 视频信息表 服务实现类
 * </p>
 *
 * @author lxd
 * @since 2023-04-17
 */
@Service
public class VedioInfoServiceImpl extends ServiceImpl<VedioInfoMapper, VedioInfo> implements IVedioInfoService {

    @Autowired
    private VedioInfoMapper vedioInfoMapper;
    @Autowired
    private VideoHistoryMapper videoHistoryMapper;
    @Autowired
    private VideoTypeMapper videoTypeMapper;

    @Autowired
    private StarTableMapper starTableMapper;

    @Autowired
    private UserMapper userMapper;
    @Override
    public Result saveVedioInfo(VedioInfo vedioInfo) {
        vedioInfo.setUploadTime(new Date());
        vedioInfo.setReason("未审核");
        int insert = vedioInfoMapper.insert(vedioInfo);
        if(insert == 0){
            return new Result(ResponseEnum.INSERT_FAIL,0,insert);
        }
        return new Result(ResponseEnum.INSERT_SUCCESS,0,insert);
    }

    @Override
    public Result selectVedioInfo(Integer limit,Integer uid) {

        List<VedioInfo> rsList = new ArrayList<>();
        if(limit == null){
            limit = 8;
        }

        if (uid == 0) {
            rsList = RecommendUtil.randomRecommend(limit,videoTypeMapper,vedioInfoMapper);
        }else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
            Date start = calendar.getTime();

            QueryWrapper<VideoHistory> timeWrapper = new QueryWrapper<VideoHistory>().ge("watch_time",start).eq("user_id",uid);
            List<VideoHistory> videoHistories = videoHistoryMapper.selectList(timeWrapper);

            List<Integer> watchIds = new ArrayList<>();
            for (VideoHistory videoHistory : videoHistories) {
                watchIds.add(videoHistory.getVideoId());
            }

            if (watchIds.size() == 0) {
                rsList = RecommendUtil.randomRecommend(limit,videoTypeMapper,vedioInfoMapper);
            }else {
                rsList = RecommendUtil.activeRecommend(limit,uid,watchIds,start,videoHistoryMapper,videoTypeMapper,vedioInfoMapper,starTableMapper);
            }
        }


        return new Result(ResponseEnum.SELECT_SUCCESS,rsList.size(),rsList);
    }

    /**
     * 根据视频id 来查询所有的视频信息
     * @param id  视频id
     * @return
     */
    @Override
    public Result selectVedioInfoById(Integer id) {
        VedioInfo vedioInfo = vedioInfoMapper.selectById(id);
//        播放量
        Integer viewCount = vedioInfo.getViewCount();
//        点赞数
        Integer viewStar = vedioInfo.getViewStar();

        viewCount = viewCount + 1;
        vedioInfo.setViewCount(viewCount);
        Wrapper<VedioInfo> wrapper = updateWrapper(vedioInfo);
        int update = vedioInfoMapper.update(vedioInfo, wrapper);
        if(update == 0){
            throw new RuntimeException("播放书加载错误，程序停止执行");
        }

        if(vedioInfo != null){
            return new Result(ResponseEnum.SELECT_SUCCESS,0,vedioInfo);
        }
        return new Result(ResponseEnum.SELECT_FAIL,0,null);
    }



    @Override
    public Result updateVedioInfo(VedioInfo vedioInfo) {
        Wrapper<VedioInfo> wrapper = VedioInfoServiceImpl.updateWrapper(vedioInfo);
        int update = vedioInfoMapper.update(vedioInfo, wrapper);
        if(update != 0){
            return new Result(ResponseEnum.UPDATE_SUCCESS,0,update);
        }else{
            return new Result(ResponseEnum.UPDATE_FAIL,0,update);
        }


    }

    @Override
    public Result deleteVedioInfoById(Integer id) {
        int i = vedioInfoMapper.deleteById(id);
        if(i != 0){
            return new Result(ResponseEnum.DELETE_SUCCESS,0,i);
        }
        return new Result(ResponseEnum.DELETE_FAIL,0,i);
    }

    @Override
    public Result deleteVedioInfoByIds(List<Integer> ids) {
        int i = vedioInfoMapper.deleteBatchIds(ids);
        if(i != 0){
            return new Result(ResponseEnum.DELETE_SUCCESS,0,i);
        }
        return new Result(ResponseEnum.DELETE_FAIL,0,i);
    }

    //根据视频的分类进行模糊查询
    @Override
    public Result selectVedioInfoByType(Integer page, Integer limit, String type) {

        // 查询视频信息
        List<VedioInfo> vedioInfos = vedioInfoMapper.selectVedioInfoByType(page, limit, type);
        int pages = vedioInfos.size()/limit == 0 ? vedioInfos.size()/limit : vedioInfos.size()/limit + 1;
        int start = (page-1)*limit;
        int end = start + limit - 1;
        if (end >= vedioInfos.size() - 1){
            end = vedioInfos.size() - 1;
        }
        List<VedioInfo> rsList = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            rsList.add(vedioInfos.get(i));
        }

        if(rsList != null){
            return new Result(ResponseEnum.SELECT_SUCCESS,vedioInfos.size(),rsList);
        }
        return new Result(ResponseEnum.SELECT_FAIL,0,null);

    }
    /**
     * 获取当前播放量排在第一位的视频信息
     * @return
     */
    @Override
    public Result selectByCount(){
        QueryWrapper<VedioInfo> queryWrapper = new QueryWrapper<>();
        QueryWrapper<VedioInfo> wrapper = queryWrapper.orderByDesc("view_count").eq("state",1);
        List<VedioInfo> vedioInfos = vedioInfoMapper.selectList(wrapper);
        VedioInfo vedioInfo = vedioInfos.get(0);
        if(vedioInfo != null){
            return new Result(ResponseEnum.SELECT_SUCCESS,0,vedioInfo);
        }
        return new Result(ResponseEnum.SELECT_FAIL,0,vedioInfo);
    }

    @Override
    public Result selectByDate(Integer page,Integer limit) {

        if(limit == null){
            limit = 6;
        }
        QueryWrapper<VedioInfo> queryWrapper = new QueryWrapper<>();
        QueryWrapper<VedioInfo> wrapper = queryWrapper.orderByDesc("upload_time").eq("state",1);
        IPage<VedioInfo> vedioInfoPage = new Page<>(page, limit);
        IPage<VedioInfo> vedioInfoIPage = vedioInfoMapper.selectPage(vedioInfoPage, wrapper);
        Long pages = vedioInfoPage.getPages();
        if(vedioInfoIPage != null){
            return new Result(ResponseEnum.SELECT_SUCCESS,pages.intValue(),vedioInfoIPage.getRecords());
        }
        return new Result(ResponseEnum.SELECT_FAIL,0,vedioInfoPage.getRecords());
    }

    @Override
    public Result updateStar(String star,String vedioId,HttpSession session) {

//        从session域中获取用户信息
        User user = (User) session.getAttribute("user");
//       判断用户是否登录  如果没有登录 直接返回
        if(user == null){
            return new Result(ResponseEnum.LOGIN_B,0,null);
        }

//        如果用户已经登录
        int viewStar = Integer.parseInt(star);
        int vedioId1 = Integer.parseInt(vedioId);

        VedioInfo vedioInfo1 = vedioInfoMapper.selectById(vedioId);
        Integer userId = user.getId();

        //        判断点赞表里面是否有该数据
        QueryWrapper<StarTable> queryWrapper = new QueryWrapper<>();
        QueryWrapper<StarTable> wrapper = queryWrapper.eq("user_id", userId)
                .eq("video_id",vedioId1);
        StarTable starTable = starTableMapper.selectOne(wrapper);
//        如果点赞表里面没有该数据，表示该用户没有给本条视频点赞过，所以现在可以点赞
        if(starTable == null){
            starTable = new StarTable();
            starTable.setVideoId(vedioId1);
            starTable.setUserId(userId);
            starTable.setCreateTime(new Date());
            int insert = starTableMapper.insert(starTable);

            viewStar++;
            vedioInfo1.setViewStar(viewStar);
            Wrapper<VedioInfo> updateWrapper = updateWrapper(vedioInfo1);
            int update = vedioInfoMapper.update(vedioInfo1, updateWrapper);
            return new Result(ResponseEnum.UPDATE_SUCCESS,0,viewStar);
        }else{
            QueryWrapper<StarTable> queryWrapper1 = new QueryWrapper<>();
            QueryWrapper<StarTable> queryWrapper2 = queryWrapper1
                    .eq("user_id", userId)
                    .eq("video_id", vedioId1);
            int delete = starTableMapper.delete(queryWrapper2);
            viewStar--;
            vedioInfo1.setViewStar(viewStar);
            Wrapper<VedioInfo> updateWrapper = updateWrapper(vedioInfo1);
            int update = vedioInfoMapper.update(vedioInfo1, updateWrapper);
            return new Result(ResponseEnum.UPDATE_FAIL,0,viewStar);
        }
    }


    @Override
    public Result changeVideoState(Integer id, String state) {
        int state1 = Integer.parseInt(state);
        VedioInfo vedioInfo = vedioInfoMapper.selectById(id);
        vedioInfo.setState(state1);
        Wrapper<VedioInfo> wrapper = updateWrapper(vedioInfo);
        int update = vedioInfoMapper.update(vedioInfo, wrapper);
        if(update != 0){
            return new Result(ResponseEnum.UPDATE_SUCCESS,0,update);
        }
        return new Result(ResponseEnum.UPDATE_FAIL,0,update);
    }

    @Override
    public Result changeVideoReason(String id, String reason) {
        int vedioId = Integer.parseInt(id);
        VedioInfo vedioInfo = vedioInfoMapper.selectById(vedioId);
        Wrapper<VedioInfo> updateWrapper = updateWrapper(vedioInfo);
        vedioInfo.setReason(reason);
        int update = vedioInfoMapper.update(vedioInfo, updateWrapper);
        if(update != 0){
            return new Result(ResponseEnum.UPDATE_SUCCESS,0,update);
        }
        return new Result(ResponseEnum.UPDATE_FAIL,0,update);
    }



    private static Wrapper<VedioInfo> updateWrapper(VedioInfo vedioInfo){
        UpdateWrapper<VedioInfo> updateWrapper = new UpdateWrapper<>();
        LambdaUpdateWrapper<VedioInfo> wrapper = updateWrapper.lambda().set(StringUtils.isEmpty(vedioInfo.getUrl()), VedioInfo::getUrl, vedioInfo.getUrl())
                .set(StringUtils.isEmpty(vedioInfo.getTitle()), VedioInfo::getTitle, vedioInfo.getType())
                .set(StringUtils.isEmpty(vedioInfo.getIntro()), VedioInfo::getIntro, vedioInfo.getIntro())
                .set(StringUtils.isEmpty(vedioInfo.getAuthor()), VedioInfo::getAuthor, vedioInfo.getAuthor())
                .set(StringUtils.isEmpty(vedioInfo.getType()), VedioInfo::getType, vedioInfo.getType())
                .set(StringUtils.isEmpty(vedioInfo.getImg()), VedioInfo::getImg, vedioInfo.getImg())
                .set(StringUtils.isEmpty(vedioInfo.getReason()), VedioInfo::getReason, vedioInfo.getReason())
                .set(StringUtils.isEmpty(vedioInfo.getOper()), VedioInfo::getOper, vedioInfo.getOper())
                .set(vedioInfo.getState() == null, VedioInfo::getState, vedioInfo.getState())
                .set(vedioInfo.getState() == null, VedioInfo::getUserId, vedioInfo.getUserId())
                .set(vedioInfo.getVideoTypeId() == null, VedioInfo::getVideoTypeId, vedioInfo.getVideoTypeId())
                .set(vedioInfo.getViewCount() == null,VedioInfo::getViewCount,vedioInfo.getViewCount())
                .eq(VedioInfo::getId,vedioInfo.getId());
        return wrapper;

    }


    @Override
    public Result searchLike(String keyword, Integer page,Integer limit) {

        int scrollLimit = 12;

        if (page == 1) {
            limit = 24;
        }
        if (limit == null){
            limit = scrollLimit;
        }

        List<VedioInfo> list = vedioInfoMapper.searchLike(keyword);

        int start = (page - 1)*limit;
        int end = start + limit - 1;
        if (end >= list.size() - 1){
            end = list.size() - 1;
        }
        int pages = 0;
        if (list.size() <= 24) {
            pages = 1;
        }else{
            pages = (list.size()-24)/12 + 2;
        }

        List<VedioInfo> rsList = new ArrayList<>();

        for (int i = start;i <= end;i++) {
            rsList.add(list.get(i));
        }


        return new Result(ResponseEnum.SELECT_SUCCESS,pages,rsList);
    }
}
