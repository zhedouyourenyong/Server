package com.im.my.protocol.response;

import com.im.my.protocol.Packet;
import com.im.my.protocol.command.Command;

public class HeartBeatResponsePacket extends Packet
{
    @Override
    public Byte getCommand ()
    {
        return Command.HEAT_BEAT_RESPONSE;
    }
}
