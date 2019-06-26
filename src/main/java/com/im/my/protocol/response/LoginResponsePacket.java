package com.im.my.protocol.response;

import com.im.my.protocol.Packet;
import com.im.my.protocol.command.Command;
import lombok.Data;

@Data
public class LoginResponsePacket extends Packet
{
    private String userId;
    private String userName;
    private Boolean success;
    private String reason;

    @Override
    public Byte getCommand ()
    {
        return Command.LOGIN_RESPONSE;
    }
}
