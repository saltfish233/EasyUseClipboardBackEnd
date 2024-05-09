package com.tnxts.easyuseclipboardbackend.utils;

import com.alibaba.fastjson2.JSON;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class FastJson2JsonRedisSerializer<T> implements RedisSerializer<T> {
    /**
     * json序列化redis工具类
     */
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    private final Class<T> clazz;

    public FastJson2JsonRedisSerializer(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }

    /**
     *  object2json
     * @param t 要序列化的对象
     * @return 序列化后的json字节数组
     * @throws SerializationException 序列化异常
     */
    public byte[] serialize(T t) throws SerializationException {
        if (t == null) {
            return new byte[0];
        }
        return JSON.toJSONString(t).getBytes(DEFAULT_CHARSET);
    }

    /**
     *  json2object
     * @param bytes 要反序列化的json字节数组
     * @return  反序列化后的对象
     * @throws SerializationException 序列化异常
     */
    public T deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        String str = new String(bytes, DEFAULT_CHARSET);

        return (T) JSON.parseObject(str, clazz);
    }
}
