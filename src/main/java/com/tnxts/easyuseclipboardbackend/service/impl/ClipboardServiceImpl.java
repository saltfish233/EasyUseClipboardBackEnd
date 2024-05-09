package com.tnxts.easyuseclipboardbackend.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tnxts.easyuseclipboardbackend.dao.ClipboardItemDao;
import com.tnxts.easyuseclipboardbackend.domain.ClipboardItem;
import com.tnxts.easyuseclipboardbackend.domain.ClipboardItemInfo;
import com.tnxts.easyuseclipboardbackend.domain.User;
import com.tnxts.easyuseclipboardbackend.service.ClipboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sound.sampled.Clip;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClipboardServiceImpl extends ServiceImpl<ClipboardItemDao, ClipboardItem> implements ClipboardService {
    @Autowired
    private ClipboardItemDao clipboardItemDao;

    @Override
    public ClipboardItem addClipboardItem(ClipboardItemInfo info, User user) {
        ClipboardItem item = new ClipboardItem();
        String content = JSON.toJSONString(info);
        ClipboardItem clipboardItem = new ClipboardItem();
        clipboardItem.setContent(content);
        clipboardItem.setCreateTime(Timestamp.valueOf(LocalDateTime.now()));
        clipboardItem.setUserId(user.getUserId());
        clipboardItem.setCollect(false);
        clipboardItem.setDeleted(false);
        clipboardItemDao.insert(clipboardItem);
        return clipboardItem;
    }

    @Override
    public List<ClipboardItem> getAllClipboardItems(User user) {
        LambdaQueryWrapper<ClipboardItem> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ClipboardItem::getUserId,user.getUserId());
        wrapper.eq(ClipboardItem::getDeleted,false);
        wrapper.orderByDesc(ClipboardItem::getClipboardItemId);
        return clipboardItemDao.selectList(wrapper);
    }

    @Override
    public Boolean collectClipboardItem(Long clipboardItemId, User user) {
        LambdaQueryWrapper<ClipboardItem> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ClipboardItem::getUserId,user.getUserId());
        wrapper.eq(ClipboardItem::getClipboardItemId,clipboardItemId);
        ClipboardItem item = clipboardItemDao.selectOne(wrapper);
        if(item != null)
        {
            LambdaUpdateWrapper<ClipboardItem> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            lambdaUpdateWrapper.eq(ClipboardItem::getClipboardItemId, item.getClipboardItemId()).set(ClipboardItem::getCollect, true);
//            updateWrapper.update(null, updateWrapper);
            clipboardItemDao.update(null,lambdaUpdateWrapper);
            return true;
        }

        return false;
    }

    @Override
    public Boolean unCollectClipboardItem(Long clipboardItemId, User user) {
        LambdaQueryWrapper<ClipboardItem> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ClipboardItem::getUserId,user.getUserId());
        wrapper.eq(ClipboardItem::getClipboardItemId,clipboardItemId);
        ClipboardItem item = clipboardItemDao.selectOne(wrapper);
        if(item != null)
        {
            LambdaUpdateWrapper<ClipboardItem> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            lambdaUpdateWrapper.eq(ClipboardItem::getClipboardItemId, item.getClipboardItemId()).set(ClipboardItem::getCollect, false);
            clipboardItemDao.update(null,lambdaUpdateWrapper);
            return true;
        }

        return false;
    }

    @Override
    public Boolean deleteClipboardItem(Long clipboardItemId, User user) {
        LambdaQueryWrapper<ClipboardItem> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ClipboardItem::getUserId,user.getUserId());
        wrapper.eq(ClipboardItem::getClipboardItemId,clipboardItemId);
        ClipboardItem item = clipboardItemDao.selectOne(wrapper);
        if(item != null)
        {
            LambdaUpdateWrapper<ClipboardItem> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            lambdaUpdateWrapper.eq(ClipboardItem::getClipboardItemId, item.getClipboardItemId()).set(ClipboardItem::getDeleted, true);
//            updateWrapper.update(null, updateWrapper);
            clipboardItemDao.update(null,lambdaUpdateWrapper);
            return true;
        }

        return false;
    }
}
