package com.im.my.protocol.response;

import com.im.my.protocol.Packet;
import com.im.my.protocol.command.Command;
import lombok.Data;

import java.util.List;


@Data
public class CreateGroupResponsePacket extends Packet
{
    private Boolean success;
    private String groupId;
    private List<String> userNameList;

    @Override
    public Byte getCommand ()
    {
        return Command.CREATE_GROUP_RESPONSE;
    }
}
