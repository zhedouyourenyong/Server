package com.im.my.protocol.request;

import com.im.my.protocol.Packet;
import com.im.my.protocol.command.Command;
import lombok.Data;


@Data
public class JoinGroupRequestPacket extends Packet
{

    private String groupId;

    @Override
    public Byte getCommand ()
    {

        return Command.JOIN_GROUP_REQUEST;
    }
}
