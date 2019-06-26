package com.im.my.protocol.response;

import com.alibaba.fastjson.JSONObject;
import com.im.my.protocol.Packet;
import com.im.my.protocol.command.Command;
import lombok.Data;

@Data
public class RegisterResponsePacket extends Packet
{
    private boolean success;
    private String reason;
    private String userId;  //返回生成的id
    private String userName;

    @Override
    public Byte getCommand ()
    {
        return Command.REGISTER_RESPONSE;
    }
}
