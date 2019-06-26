package com.im.my.protocol.response;

import com.im.my.protocol.Packet;
import com.im.my.protocol.command.Command;
import lombok.Data;

@Data
public class AddFriendResponsePacket extends Packet
{
    private Boolean success;
    private String reason;

    @Override
    public Byte getCommand ()
    {
        return Command.ADD_FRIEND_RESPONSE;
    }
}
