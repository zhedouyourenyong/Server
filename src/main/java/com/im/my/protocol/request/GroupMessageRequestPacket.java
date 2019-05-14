package com.im.my.protocol.request;

import com.im.my.protocol.Packet;
import com.im.my.protocol.command.Command;
import lombok.Data;


@Data
public class GroupMessageRequestPacket extends Packet
{
    String toGroupId;
    String message;

    @Override
    public Byte getCommand ()
    {
        return Command.SEND_MESSAGE_TO_GROUP_REQUEST;
    }
}
