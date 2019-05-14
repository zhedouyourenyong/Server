package com.im.my.protocol.response;

import com.im.my.protocol.Packet;
import com.im.my.protocol.command.Command;
import com.im.my.session.Session;
import lombok.Data;

import java.util.List;



@Data
public class ListGroupMembersResponsePacket extends Packet
{

    private String groupId;

    private List<Session> sessionList;

    @Override
    public Byte getCommand ()
    {

        return Command.LIST_GROUP_MEMBERS_RESPONSE;
    }
}
