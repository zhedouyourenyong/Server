package com.im.my.codec;

import com.im.my.protocol.Packet;
import com.im.my.protocol.PacketCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/*
* 单例模式
* */
@ChannelHandler.Sharable
public class PacketCodecHandler extends MessageToMessageCodec<ByteBuf, Packet>
{
    public static final Logger logget= LoggerFactory.getLogger(PacketCodecHandler.class);
    public static final PacketCodecHandler INSTANCE=new PacketCodecHandler();

    private PacketCodecHandler()
    {
    }

    @Override
    protected void encode (ChannelHandlerContext channelHandlerContext, Packet packet, List<Object> list) throws Exception
    {
        ByteBuf buf=channelHandlerContext.alloc().ioBuffer();
        PacketCodec.INSTANCE.encode(buf,packet);
        list.add(buf);
    }

    @Override
    protected void decode (ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception
    {
        list.add(PacketCodec.INSTANCE.decode(byteBuf));
    }
}
