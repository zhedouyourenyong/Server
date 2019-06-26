package com.im.my.protocol.request;

import com.im.my.protocol.Packet;
import com.im.my.protocol.command.Command;
import lombok.Data;

@Data
public class ShowFriendsListRequestPacket extends Packet
{
    private String userId;

    @Override
    public Byte getCommand ()
    {
        return Command.SHOW_FRIENDS_LIST_REQUEST;
    }
}
