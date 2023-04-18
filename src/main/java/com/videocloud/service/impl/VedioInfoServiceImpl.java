package com.videocloud.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
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
import io.swagger.models.auth.In;
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
            limit = 20;
        }
        List<VedioInfo> vedioInfos = vedioInfoMapper.selectList(null);
        IPage<VedioInfo> page1 = new Page<>(page, limit);
        IPage<VedioInfo> vedioInfoIPage = vedioInfoMapper.selectPage(page1, null);
        Long total = vedioInfoIPage.getTotal();
        if(vedioInfoIPage != null){
            return new Result(ResponseEnum.SELECT_SUCCESS,total.intValue(),vedioInfos);
        }
        return new Result(ResponseEnum.SELECT_FAIL,0,vedioInfos);
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


    private static Wrapper<VedioInfo> updateWrapper(VedioInfo vedioInfo){
        UpdateWrapper<VedioInfo> updateWrapper = new UpdateWrapper<>();
        LambdaUpdateWrapper<VedioInfo> wrapper = updateWrapper.lambda().eq(StringUtils.isEmpty(vedioInfo.getUrl()), VedioInfo::getUrl, vedioInfo.getUrl())
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
                .eq(VedioInfo::getId,vedioInfo.getId());
        return wrapper;

    }
    


}
