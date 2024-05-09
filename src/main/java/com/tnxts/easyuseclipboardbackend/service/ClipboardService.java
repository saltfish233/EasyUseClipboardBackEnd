package com.tnxts.easyuseclipboardbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tnxts.easyuseclipboardbackend.domain.ClipboardItem;
import com.tnxts.easyuseclipboardbackend.domain.ClipboardItemInfo;
import com.tnxts.easyuseclipboardbackend.domain.User;

import java.util.List;

public interface ClipboardService extends IService<ClipboardItem> {
    public ClipboardItem addClipboardItem(ClipboardItemInfo info, User user);
    public List<ClipboardItem> getAllClipboardItems(User user);
    public Boolean collectClipboardItem(Long clipboardItemId,User user);
    public Boolean unCollectClipboardItem(Long clipboardItemId,User user);
    public Boolean deleteClipboardItem(Long clipboardItemId,User user);
}
