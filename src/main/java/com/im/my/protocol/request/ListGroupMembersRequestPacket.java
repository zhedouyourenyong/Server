package com.im.my.protocol.request;

import com.im.my.protocol.Packet;
import com.im.my.protocol.command.Command;
import lombok.Data;


@Data
public class ListGroupMembersRequestPacket extends Packet
{

    private String groupId;

    @Override
    public Byte getCommand ()
    {

        return Command.LIST_GROUP_MEMBERS_REQUEST;
    }
}
