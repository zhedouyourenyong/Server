package com.im.my.protocol.response;

import com.im.my.protocol.Packet;
import com.im.my.protocol.command.Command;
import lombok.Data;


@Data
public class JoinGroupResponsePacket extends Packet
{
    private String groupId;

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand ()
    {

        return Command.JOIN_GROUP_RESPONSE;
    }
}
