package com.videocloud.service;

import com.videocloud.entity.Result;
import com.videocloud.entity.VedioInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.videocloud.mapper.VedioInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
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
    Result selectVedioInfo(Integer limit,Integer uid);


//    根据视频ID查询视频信息
    Result selectVedioInfoById(Integer id);

//    修改视频信息
    Result updateVedioInfo(VedioInfo vedioInfo);

//    删除该条视频记录
    Result deleteVedioInfoById(Integer id);

    Result deleteVedioInfoByIds(List<Integer> ids);

    //    查询点赞数最高的视频
    Result selectByCount();

    //根据视频的分类进行模糊查询
    Result selectVedioInfoByType(Integer page, Integer limit, String type);



    //    根据时间排序显示视频
    Result selectByDate(Integer page,Integer limit);


    Result updateStar(String star, String videoId, HttpSession session);


    public Result searchLike(String keyword, Integer page,Integer limit);





}
