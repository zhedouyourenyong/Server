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

@ChannelHandler.Sharable
public class PacketCodecHandler extends MessageToMessageCodec<ByteBuf, Packet>
{
    public static final Logger logger= LoggerFactory.getLogger(PacketCodecHandler.class);
    public static final PacketCodecHandler INSTANCE=new PacketCodecHandler();

    private PacketCodecHandler ()
    {
    }

    @Override
    protected void encode (ChannelHandlerContext channelHandlerContext, Packet packet, List<Object> list)
    {
        ByteBuf buf=channelHandlerContext.alloc().ioBuffer();
        PacketCodec.INSTANCE.encode(buf,packet);
        list.add(buf);
    }

    @Override
    protected void decode (ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list)
    {
        Packet requestPacket= PacketCodec.INSTANCE.decode(byteBuf);
        if(requestPacket==null)
            throw new NullPointerException("解码失败");
        list.add(requestPacket);
    }

    @Override
    public void exceptionCaught (ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        logger.error(cause.getMessage());
    }
}