package com.im.my.async;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class EventModel
{
    private EventType type;
    private Map<String, Object> exts = new HashMap<>();

    public EventModel ()
    {
    }

    public EventModel (EventType type)
    {
        this.type = type;
    }

    public EventModel set (String key, Object value)
    {
        exts.put(key, value);
        return this;
    }
    public Object get (String key)
    {
        return exts.get(key);
    }
}
