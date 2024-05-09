// ClipboardItemDao.java
package com.tnxts.easyuseclipboardbackend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tnxts.easyuseclipboardbackend.domain.ClipboardItem;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ClipboardItemDao extends BaseMapper<ClipboardItem> {
}
