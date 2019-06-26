package com.im.my.model;


import lombok.Data;

import java.util.Date;

@Data
public class Message
{
    private int id;
    private String fromId;
    private String toId;
    private String content;
    private Date createdDate;
    private int hasRead;
    private String conversationId;

    public String getConversationId ()
    {
        if(Integer.valueOf(fromId) < Integer.valueOf(toId))
        {
            return fromId+toId;
        } else
        {
            return toId+fromId;
        }
    }
}
