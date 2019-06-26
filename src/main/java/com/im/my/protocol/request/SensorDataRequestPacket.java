package com.im.my.protocol.request;

import com.alibaba.fastjson.JSONObject;
import com.im.my.protocol.Packet;
import com.im.my.protocol.command.Command;
import lombok.Data;

@Data
public class SensorDataRequestPacket extends Packet
{
    private JSONObject sensorData;

    @Override
    public Byte getCommand ()
    {
        return Command.SENSOR_REQUEST;
    }
}
