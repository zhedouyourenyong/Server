package com.im.my.protocol.response;

import com.im.my.protocol.Packet;
import com.im.my.protocol.command.Command;
import lombok.Data;


@Data
public class GroupMessageResponsePacket extends Packet
{
    String fromUserName;
    String fromGroupId;
    String message;

    @Override
    public Byte getCommand ()
    {
        return Command.SEND_MESSAGE_TO_GROUP_RESPONSE;
    }
}
