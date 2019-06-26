package com.im.my.server.handler;

import com.im.my.protocol.request.ShowGroupListRequestPacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ChannelHandler.Sharable
public class ShowGroupListRequestHandler extends SimpleChannelInboundHandler<ShowGroupListRequestPacket>
{
    private static final Logger logger= LoggerFactory.getLogger(ShowGroupListRequestHandler.class);
    public static final ShowGroupListRequestHandler INSTANCE=new ShowGroupListRequestHandler();

    private ShowGroupListRequestHandler()
    {
    }

    @Override
    protected void channelRead0 (ChannelHandlerContext channelHandlerContext, ShowGroupListRequestPacket showGroupListRequestPacket) throws Exception
    {

    }
}
