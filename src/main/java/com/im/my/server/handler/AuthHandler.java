package com.im.my.server.handler;

import com.im.my.utils.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/*
* 所以本handler用于防止客户端在未登录状态发送请求包
* 在LoginRequestHandler中只能接接收LoginRequestPacket
* */

@ChannelHandler.Sharable
public class AuthHandler extends ChannelInboundHandlerAdapter
{
    private static final Logger logger= LoggerFactory.getLogger(AuthHandler.class);
    public static final AuthHandler INSTANCE=new AuthHandler();

    private AuthHandler()
    {
    }


    @Override
    public void channelRead (ChannelHandlerContext ctx, Object msg) throws Exception
    {
        if(!SessionUtil.hasLogin(ctx.channel()))
        {
            ctx.channel().close();
        } else
        {
            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
        }
    }
}
