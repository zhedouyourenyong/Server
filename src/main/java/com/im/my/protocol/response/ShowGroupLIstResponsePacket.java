package com.im.my.protocol.response;

import com.alibaba.fastjson.JSONArray;
import com.im.my.protocol.Packet;
import com.im.my.protocol.command.Command;
import lombok.Data;

@Data
public class ShowGroupLIstResponsePacket extends Packet
{
    private Boolean success;
    private String reason;

    private JSONArray groupList;

    @Override
    public Byte getCommand ()
    {
        return Command.SHOW_GROUP_LIST_RESPONSE;
    }
}
