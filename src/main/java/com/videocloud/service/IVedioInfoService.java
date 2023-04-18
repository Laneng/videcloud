package com.videocloud.service;

import com.videocloud.entity.Result;
import com.videocloud.entity.VedioInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.videocloud.mapper.VedioInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 视频信息表 服务类
 * </p>
 *
 * @author lxd
 * @since 2023-04-17
 */
@Service
public interface IVedioInfoService extends IService<VedioInfo> {

//    添加视频信息
    Result saveVedioInfo(VedioInfo vedioInfo);


//    查询所有视频信息
    Result selectVedioInfo(Integer page,Integer limit);


//    根据视频ID查询视频信息
    Result selectVedioInfoById(Integer id);

//    修改视频信息
    Result updateVedioInfo(VedioInfo vedioInfo);

//    删除该条视频记录
    Result deleteVedioInfoById(Integer id);

    Result deleteVedioInfoByIds(List<Integer> ids);







}