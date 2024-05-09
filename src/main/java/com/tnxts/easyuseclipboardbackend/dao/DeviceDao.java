// DeviceDao.java
package com.tnxts.easyuseclipboardbackend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tnxts.easyuseclipboardbackend.domain.Device;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeviceDao extends BaseMapper<Device> {
}
