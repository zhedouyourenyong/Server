package com.im.my.protocol.request;

import com.im.my.protocol.Packet;
import com.im.my.protocol.command.Command;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class MessageRequestPacket extends Packet
{
    private String toUserId;
    private String message;

    public MessageRequestPacket(String toUserId, String message)
    {
        this.toUserId = toUserId;
        this.message = message;
    }

    @Override
    public Byte getCommand ()
    {
        return Command.MESSAGE_REQUEST;
    }
}
