package com.im.my.server.handler;


import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;


/*
* 放在逻辑链的最前边，防止发生意外后事件无法向后传播
* */
public class IMIdleStateHandler extends IdleStateHandler
{
    private static final int READER_IDLE_TIME = 15;

    public IMIdleStateHandler ()
    {
        super(READER_IDLE_TIME, 0, 0, TimeUnit.SECONDS);
    }


    @Override
    protected void channelIdle (ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception
    {
        System.out.println(READER_IDLE_TIME + "秒内未读到数据，关闭连接");
        ctx.channel().close();
    }
}
