package com.im.my.protocol.request;

import com.im.my.protocol.Packet;
import com.im.my.protocol.command.Command;
import lombok.Data;

@Data
public class ShowGroupListRequestPacket extends Packet
{
    private String userId;

    @Override
    public Byte getCommand ()
    {
        return Command.SHOW_GROUP_LIST_REQUEST;
    }
}
