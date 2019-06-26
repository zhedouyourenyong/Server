package com.im.my.protocol.request;

import com.im.my.protocol.Packet;
import com.im.my.protocol.command.Command;
import lombok.Data;


@Data
public class RegisterRequestPacket extends Packet
{
    private String name;
    private String password;

    @Override
    public Byte getCommand ()
    {
        return Command.REGISTER_REQUEST;
    }
}
