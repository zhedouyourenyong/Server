package com.im.my.server;

import com.im.my.codec.PacketCodecHandler;
import com.im.my.codec.Spliter;
import com.im.my.config.NettyConfig;
import com.im.my.protocol.PacketCodec;
import com.im.my.server.handler.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class NettyServer
{
    private final Logger logger= LoggerFactory.getLogger(NettyServer.class);

    private final EventLoopGroup bossGroup =new NioEventLoopGroup();
    private final EventLoopGroup workerGroup=new NioEventLoopGroup();
    private final ServerBootstrap serverBootstrap=new ServerBootstrap();

    @Autowired
    private NettyConfig nettyConfig;

    @PostConstruct
    private void startup()
    {
        try
        {
            serverBootstrap
                    .group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childHandler(new ChannelInitializer<NioSocketChannel>()
                    {
                        @Override
                        protected void initChannel (NioSocketChannel clientChannel) throws Exception
                        {
                            ChannelPipeline cp=clientChannel.pipeline();

                            cp.addLast("IMIdleStateHandler",new IMIdleStateHandler());
                            cp.addLast("Spliter",new Spliter());
                            cp.addLast("PacketCodecHandler", PacketCodecHandler.INSTANCE);

                            cp.addLast("RegisterRequestHandler",RegisterRequestHandler.INSTANCE);
                            cp.addLast("LoginRequestHandler",LoginRequestHandler.INSTANCE);
                            cp.addLast("AuthHandler",AuthHandler.INSTANCE);

                            //平行指令分发
                            cp.addLast("DispatherHandler",DispatherHandler.INSTANCE);
                        }
                    });
            bindPort();
        }catch (Exception e)
        {
            logger.error("NettyServer 开启失败",e);
        }
    }

    private void bindPort() throws Exception
    {
        List<Integer> ports= nettyConfig.getPorts();
        Collection<ChannelFuture> futures=new ArrayList<>(ports.size());
        for(int port:ports)
        {
            ChannelFuture future=serverBootstrap.bind(port).sync();
            futures.add(future);
        }
        for(ChannelFuture cf:futures)
        {
            Channel channel=cf.channel();
            InetSocketAddress socketAddress=(InetSocketAddress)channel.localAddress();
            int port=socketAddress.getPort();
            if(cf.isSuccess())
                logger.info("监听端口: "+port+" 成功");
            else
                logger.info("监听端口: "+port+" 失败");
        }
    }


    @PreDestroy
    private void shutDown()
    {
        try
        {
            bossGroup.shutdownGracefully().syncUninterruptibly();
            workerGroup.shutdownGracefully().syncUninterruptibly();
            logger.info("关闭netty server成功");
        }catch (Exception e)
        {
            logger.error("NettySever shutdown 失败",e);
        }
    }
}
