package com.videocloud.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.videocloud.entity.ResponseEnum;
import com.videocloud.entity.Result;
import com.videocloud.entity.VedioInfo;
import com.videocloud.mapper.VedioInfoMapper;
import com.videocloud.service.IVedioInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
    public Result selectVedioInfo(Integer page,Integer limit) {

        if (page == null){
            page = 1;
        }
        if(limit == null){
            limit = 8;
        }
        List<VedioInfo> vedioInfos = vedioInfoMapper.selectList(null);
        IPage<VedioInfo> page1 = new Page<>(page, limit);
        IPage<VedioInfo> vedioInfoIPage = vedioInfoMapper.selectPage(page1, null);
        Long total = vedioInfoIPage.getTotal();
        if(vedioInfoIPage != null){
            return new Result(ResponseEnum.SELECT_SUCCESS,total.intValue(),vedioInfoIPage.getRecords());
        }
        return new Result(ResponseEnum.SELECT_FAIL,0,vedioInfoIPage.getRecords());
    }

    /**
     * 根据视频id 来查询所有的视频信息
     * @param id  视频id
     * @return
     */
    @Override
    public Result selectVedioInfoById(Integer id) {
        VedioInfo vedioInfo = vedioInfoMapper.selectById(id);
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


    /**
     * 获取当前播放量排在第一位的视频信息
     * @return
     */
    @Override
    public Result selectByCount(){
        QueryWrapper<VedioInfo> queryWrapper = new QueryWrapper<>();
        QueryWrapper<VedioInfo> wrapper = queryWrapper.orderByDesc("view_count");
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
        QueryWrapper<VedioInfo> wrapper = queryWrapper.orderByDesc("upload_time");
        IPage<VedioInfo> vedioInfoPage = new Page<>(page, limit);
        IPage<VedioInfo> vedioInfoIPage = vedioInfoMapper.selectPage(vedioInfoPage, wrapper);
        Long pages = vedioInfoPage.getPages();
        if(vedioInfoIPage != null){
              return new Result(ResponseEnum.SELECT_SUCCESS,pages.intValue(),vedioInfoIPage.getRecords());
        }
        return new Result(ResponseEnum.SELECT_FAIL,0,vedioInfoPage.getRecords());
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
    


}
