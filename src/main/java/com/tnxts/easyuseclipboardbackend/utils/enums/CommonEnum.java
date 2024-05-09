package com.tnxts.easyuseclipboardbackend.utils.enums;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonEnum {
    DEFAULT_ROLE("USER"),
    AUTHORIZATION("Authorization");

    private final String key;
}