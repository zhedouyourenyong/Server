package com.im.my.protocol.response;

import com.alibaba.fastjson.JSONArray;
import com.im.my.protocol.Packet;
import com.im.my.protocol.command.Command;
import lombok.Data;

@Data
public class ShowFriendsListResponsePacket extends Packet
{
    private Boolean success;
    private String reason;

    private JSONArray friendsList; //保存好友的名字和id

    @Override
    public Byte getCommand ()
    {
        return Command.SHOW_FRIENDS_LIST_RESPONSE;
    }
}
