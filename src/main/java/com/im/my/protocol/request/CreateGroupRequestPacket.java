package com.im.my.protocol.request;

import com.im.my.protocol.Packet;
import com.im.my.protocol.command.Command;
import lombok.Data;

import java.util.List;


@Data
public class CreateGroupRequestPacket extends Packet
{

    private List<String> userIdList;

    @Override
    public Byte getCommand ()
    {

        return Command.CREATE_GROUP_REQUEST;
    }
}
