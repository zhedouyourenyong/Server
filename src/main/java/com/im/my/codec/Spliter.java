package com.im.my.codec;

import com.im.my.protocol.PacketCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Spliter extends LengthFieldBasedFrameDecoder
{
    private static final int LENGTH_FIELD_OFFSET = 7;
    private static final int LENGTH_FIELD_LENGTH = 4;

    private static final Logger logger= LoggerFactory.getLogger(Spliter.class);

    public Spliter()
    {
        super(Integer.MAX_VALUE, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH);
    }

    @Override
    protected Object decode (ChannelHandlerContext ctx, ByteBuf in) throws Exception
    {
        if(in.getInt(in.readerIndex())!= PacketCodec.MAGIC_NUMBER)  //检查魔数
        {
            ctx.close();
            logger.error("验证 MAGIC_NUMBER 失败,关闭连接");
            return null;
        }
        return super.decode(ctx, in);
    }
}
