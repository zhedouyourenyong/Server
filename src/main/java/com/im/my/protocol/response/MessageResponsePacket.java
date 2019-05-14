package com.im.my.protocol.response;

import com.im.my.protocol.Packet;
import com.im.my.protocol.command.Command;
import lombok.Data;


@Data
public class MessageResponsePacket extends Packet
{
    private String fromUserId;
    private String fromUserName;
    private String message;

    @Override
    public Byte getCommand ()
    {
        return Command.MESSAGE_RESPONSE;
    }
}
