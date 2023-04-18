package com.videocloud.service.impl;

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
        int insert = vedioInfoMapper.insert(vedioInfo);
        if(insert == 0){
            return new Result(ResponseEnum.INSERT_FAIL,0,insert);
        }
        return new Result(ResponseEnum.INSERT_SUCCESS,0,insert);
    }

    @Override
    public Result selectVedioInfo(Integer page,Integer limit) {

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
        UpdateWrapper<VedioInfo> wraper = VedioInfoServiceImpl.updateWrapper(vedioInfo);
        int update = vedioInfoMapper.update(vedioInfo, wraper);
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


    private static UpdateWrapper<VedioInfo> updateWrapper(VedioInfo vedioInfo){
        UpdateWrapper<VedioInfo> uw = new UpdateWrapper<>();
        UpdateWrapper<VedioInfo> wrapper = uw.isNotNull("url").set("url", vedioInfo.getUrl())
                .isNotNull("title").set("title", vedioInfo.getTitle())
                .isNotNull("intro").set("intro", vedioInfo.getIntro())
                .isNotNull("author").set("author", vedioInfo.getAuthor())
                .isNotNull("type").set("type", vedioInfo.getType())
                .isNotNull("img").set("img", vedioInfo.getImg())
                .isNotNull("state").set("state", vedioInfo.getState())
                .isNotNull("reason").set("reason", vedioInfo.getReason())
                .isNotNull("oper").set("oper", vedioInfo.getOper())
                .isNotNull("user_id").set("user_id", vedioInfo.getUserId())
                .isNotNull("video_type_id").set("video_type_id", vedioInfo.getVideoTypeId());
        return wrapper;

    }
    


}
