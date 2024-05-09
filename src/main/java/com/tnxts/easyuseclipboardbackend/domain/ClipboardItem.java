package com.tnxts.easyuseclipboardbackend.domain;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import java.sql.Timestamp;

@Data
public class ClipboardItem {
    @TableId(value = "clipboard_item_id", type = IdType.AUTO)
    private Long clipboardItemId;  // 剪贴板项的唯一标识
    private Long userId;           // 用户的唯一标识
    private Long deviceId;         // 设备的唯一标识
    private String content;        // 剪贴板内容
    private Timestamp createTime;  // 创建时间
    private Timestamp expireTime;  // 过期时间
    private Timestamp updateTime;  // 上次更新时间
    private Boolean collect;       // 是否被收藏（以 Boolean 表示，适应 Java 类型）
    private Boolean deleted;
}
