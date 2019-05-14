package com.im.my.protocol.request;

import com.im.my.protocol.Packet;
import com.im.my.protocol.command.Command;
import lombok.Data;

@Data
public class LogoutRequestPacket extends Packet
{
    @Override
    public Byte getCommand ()
    {
        return Command.LOGOUT_REQUEST;
    }
}
