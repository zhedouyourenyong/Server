package com.im.my.server.handler;

import com.im.my.protocol.request.HeartBeatRequestPacket;
import com.im.my.protocol.response.HeartBeatResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ChannelHandler.Sharable
public class HeartBeatRequestHandler extends SimpleChannelInboundHandler<HeartBeatRequestPacket>
{
    private static final Logger logger= LoggerFactory.getLogger(HeartBeatRequestHandler.class);
    public static final HeartBeatRequestHandler INSTANCE=new HeartBeatRequestHandler();

    private HeartBeatRequestHandler()
    {
    }

    @Override
    protected void channelRead0 (ChannelHandlerContext ctx, HeartBeatRequestPacket msg) throws Exception
    {
        HeartBeatResponsePacket resp=new HeartBeatResponsePacket();
        ctx.writeAndFlush(resp);
    }
}
