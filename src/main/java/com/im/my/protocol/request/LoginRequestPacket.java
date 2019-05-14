package com.im.my.protocol.request;

import com.im.my.protocol.Packet;
import com.im.my.protocol.command.Command;
import lombok.Data;

@Data
public class LoginRequestPacket extends Packet
{
    public String userName;
    public String password;

    @Override
    public Byte getCommand ()
    {
        return Command.LOGIN_REQUEST;
    }
}
