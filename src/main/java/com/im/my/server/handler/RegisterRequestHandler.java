package com.im.my.server.handler;


import com.alibaba.fastjson.JSONObject;
import com.im.my.service.UserService;
import com.im.my.model.Session;
import com.im.my.protocol.request.RegisterRequestPacket;
import com.im.my.protocol.response.RegisterResponsePacket;
import com.im.my.utils.SessionUtil;
import com.im.my.utils.SpringBeanFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ChannelHandler.Sharable
public class RegisterRequestHandler extends SimpleChannelInboundHandler<RegisterRequestPacket>
{
    private static final Logger logger= LoggerFactory.getLogger(RegisterRequestHandler.class);
    public static final RegisterRequestHandler INSTANCE=new RegisterRequestHandler();

    private UserService userService;

    private RegisterRequestHandler()
    {
        userService= SpringBeanFactory.getBean(UserService.class);
    }

    @Override
    protected void channelRead0 (ChannelHandlerContext ctx, RegisterRequestPacket packet) throws Exception
    {
        RegisterResponsePacket resp=new RegisterResponsePacket();

        JSONObject registerResp=userService.register(packet.getName(),packet.getPassword());
        if(registerResp.get("error")!=null)
        {
            resp.setSuccess(false);
            resp.setReason((String) registerResp.get("error"));
            System.out.println("注册失败:"+registerResp.get("error"));
            ctx.writeAndFlush(resp);
        }
        else
        {
            String id=String.valueOf(registerResp.get("userId"));
            SessionUtil.bindSession(new Session(id,packet.getName()),ctx.channel());

            resp.setSuccess(true);
            resp.setUserId(id);
            resp.setUserName(packet.getName());

            ctx.writeAndFlush(resp);
            System.out.println(packet.getName()+" 注册成功");
        }
    }
}
