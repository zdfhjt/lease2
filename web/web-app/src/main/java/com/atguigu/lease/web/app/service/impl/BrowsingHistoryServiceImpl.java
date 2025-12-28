package com.atguigu.lease.web.app.service.impl;

import com.atguigu.lease.model.entity.BrowsingHistory;
import com.atguigu.lease.web.app.mapper.BrowsingHistoryMapper;
import com.atguigu.lease.web.app.service.BrowsingHistoryService;
import com.atguigu.lease.web.app.vo.history.HistoryItemVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author liubo
 * @description 针对表【browsing_history(浏览历史)】的数据库操作Service实现
 * @createDate 2023-07-26 11:12:39
 */
@Service
public class BrowsingHistoryServiceImpl extends ServiceImpl<BrowsingHistoryMapper, BrowsingHistory>
        implements BrowsingHistoryService {
    @Autowired
    private BrowsingHistoryMapper browsingHistoryMapper;
    @Override
    public IPage<HistoryItemVo> pageHistoryItemByUserId(IPage<HistoryItemVo> page, Long userId) {
        return browsingHistoryMapper.pageHistoryItemByUserId(page,userId);
    }

    @Override
    @Async//异步注解
    public void saveHistory(Long userId, Long id) {
        System.out.println("保存浏览历史"+Thread.currentThread().getName());
        //判断用户是不是首次访问改房间
        LambdaQueryWrapper<BrowsingHistory> browsingHistoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        browsingHistoryLambdaQueryWrapper.eq(BrowsingHistory::getUserId, userId);
        browsingHistoryLambdaQueryWrapper.eq(BrowsingHistory::getRoomId, id);
        BrowsingHistory browsingHistory = browsingHistoryMapper.selectOne(browsingHistoryLambdaQueryWrapper);
        if(browsingHistory!=null){
            //修改
            browsingHistory.setBrowseTime(new Date());
            browsingHistoryMapper.updateById(browsingHistory);
        }else{
            browsingHistory = new BrowsingHistory();
            browsingHistory.setUserId(userId);
            browsingHistory.setRoomId(id);
            browsingHistory.setBrowseTime(new Date());
            browsingHistoryMapper.insert(browsingHistory);
        }
    }
}