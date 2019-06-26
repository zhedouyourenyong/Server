package com.im.my.async.handler;

import com.im.my.async.EventHandler;
import com.im.my.async.EventModel;
import com.im.my.async.EventType;
import com.im.my.model.Message;
import com.im.my.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class MessageStorageHandler implements EventHandler
{
    @Autowired
    MessageService messageService;

    @Override
    public void doHandle (EventModel model)
    {
        Message message=(Message) model.get("model");

        if(message!=null)
           messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventTypes ()
    {
        return Arrays.asList(EventType.MESSAGE_SQL);
    }
}
