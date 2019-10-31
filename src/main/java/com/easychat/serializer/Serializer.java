package com.easychat.serializer;

/**
 * @author Zed
 * date: 2019/08/19.
 * description:
 */
public interface Serializer {
    Serializer Default = new JsonSerializer();
    byte getSerializerAlgorithm();
    
    <T> byte [] serializer(T t);
    
    <T> T deSerializer(Class<T> clazz, byte[] bytes);
}
