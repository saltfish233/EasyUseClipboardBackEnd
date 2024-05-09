package com.tnxts.easyuseclipboardbackend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tnxts.easyuseclipboardbackend.domain.ClipboardItem;
import com.tnxts.easyuseclipboardbackend.domain.RoleAuthority;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleAuthorityDao extends BaseMapper<RoleAuthority> {
}
