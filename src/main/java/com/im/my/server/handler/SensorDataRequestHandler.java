package com.im.my.server.handler;

import com.alibaba.fastjson.JSONObject;
import com.im.my.mapper.dao.SensorDao;
import com.im.my.protocol.request.SensorDataRequestPacket;
import com.im.my.utils.SpringBeanFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@ChannelHandler.Sharable
public class SensorDataRequestHandler extends SimpleChannelInboundHandler<SensorDataRequestPacket>
{
    private static final Logger logger= LoggerFactory.getLogger(SensorDataRequestHandler.class);
    public static final SensorDataRequestHandler INSTANCE=new SensorDataRequestHandler();
    private SensorDao dao;

    private SensorDataRequestHandler()
    {
        dao= SpringBeanFactory.getBean(SensorDao.class);
    }

    @Override
    protected void channelRead0 (ChannelHandlerContext ctx, SensorDataRequestPacket msg) throws Exception
    {
        JSONObject sensorData=msg.getSensorData();
        dao.insertData(sensorData.toJSONString());
        System.out.println(sensorData.toJSONString());
    }
}
