package com.im.my.config;

import com.im.my.model.Session;
import io.netty.util.AttributeKey;

public interface Attributes
{
    AttributeKey<Session> SESSION=AttributeKey.newInstance("model");
}