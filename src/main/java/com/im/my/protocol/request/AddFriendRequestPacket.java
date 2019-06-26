package com.im.my.protocol.request;

import com.im.my.protocol.Packet;
import com.im.my.protocol.command.Command;
import lombok.Data;

@Data
public class AddFriendRequestPacket extends Packet
{
    private String friendId;

    @Override
    public Byte getCommand ()
    {
        return Command.ADD_FRIEND_REQUEST;
    }
}
