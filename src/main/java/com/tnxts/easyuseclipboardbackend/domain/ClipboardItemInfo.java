package com.tnxts.easyuseclipboardbackend.domain;

import lombok.Data;

@Data
public class ClipboardItemInfo {
    private String text;
    private String html;
    private Object image;
    private String rtf;
}