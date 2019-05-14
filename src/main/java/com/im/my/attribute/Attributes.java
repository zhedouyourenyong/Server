package com.im.my.attribute;

import com.im.my.session.Session;
import io.netty.util.AttributeKey;

public interface Attributes
{
    AttributeKey<Session> SESSION=AttributeKey.newInstance("session");
}
