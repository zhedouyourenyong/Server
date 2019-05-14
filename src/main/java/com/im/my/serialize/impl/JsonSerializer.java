package com.im.my.serialize.impl;

import com.alibaba.fastjson.JSON;
import com.im.my.serialize.Serializer;
import com.im.my.serialize.SerializerAlforithm;

public class JsonSerializer implements Serializer
{

    @Override
    public byte getSerializerAlgorithm ()
    {
        return SerializerAlforithm.JSON;
    }

    @Override
    public byte[] serialize (Object object)
    {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize (Class<T> clazz, byte[] bytes)
    {
        return JSON.parseObject(bytes,clazz);
    }
}
