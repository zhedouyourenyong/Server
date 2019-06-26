package com.im.my.async;

public enum EventType
{
    MESSAGE_SQL(0);

    private int value;

    EventType (int value)
    {
        this.value = value;
    }

    public int getValue ()
    {
        return value;
    }
}
