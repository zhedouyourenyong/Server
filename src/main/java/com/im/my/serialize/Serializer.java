package com.im.my.serialize;

import com.im.my.serialize.impl.JsonSerializer;

public interface Serializer
{
    //默认使用的序列化算法
    Serializer DEFAULT = new JsonSerializer();

    //获取使用de 序列化算法
    byte getSerializerAlgorithm ();

    //将java对象转化为二进制
    byte[] serialize(Object object);

    //将二进制转化为java对象
    <T> T deserialize(Class<T> clazz, byte[] bytes);
}
