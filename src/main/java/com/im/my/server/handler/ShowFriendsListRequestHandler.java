package com.im.my.server.handler;

import com.im.my.protocol.request.ShowFriendsListRequestPacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ChannelHandler.Sharable
public class ShowFriendsListRequestHandler extends SimpleChannelInboundHandler<ShowFriendsListRequestPacket>
{
    private static final Logger logger= LoggerFactory.getLogger(ShowFriendsListRequestHandler.class);
    public static final ShowFriendsListRequestHandler INSTANCE=new ShowFriendsListRequestHandler();

    private ShowFriendsListRequestHandler()
    {
    }

    @Override
    protected void channelRead0 (ChannelHandlerContext channelHandlerContext, ShowFriendsListRequestPacket showFriendsListRequestPacket) throws Exception
    {

    }
}
