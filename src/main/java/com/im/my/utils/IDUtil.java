package com.im.my.utils;

import java.util.UUID;

public class IDUtil
{
    public static String randomId()
    {
        return UUID.randomUUID().toString().split("-")[0];
    }
}
