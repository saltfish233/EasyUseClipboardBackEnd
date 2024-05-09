package com.tnxts.easyuseclipboardbackend.domain;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Device {
    @TableId(value = "deviceId",type = IdType.AUTO)
    private Long deviceId;
    private String imei;
    private String name;
    private String ip;
    private Long online;
}

