package com.im.my.protocol.request;

import com.im.my.protocol.Packet;
import com.im.my.protocol.command.Command;

public class HeartBeatRequestPacket extends Packet
{
    @Override
    public Byte getCommand ()
    {
        return Command.HEAT_BEAT_REQUEST;
    }
}
