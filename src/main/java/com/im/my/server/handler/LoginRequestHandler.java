package com.im.my.server.handler;

import com.alibaba.fastjson.JSONObject;
import com.im.my.service.UserService;
import com.im.my.protocol.request.LoginRequestPacket;
import com.im.my.protocol.response.LoginResponsePacket;
import com.im.my.model.Session;
import com.im.my.utils.SessionUtil;
import com.im.my.utils.SpringBeanFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;


@ChannelHandler.Sharable
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket>
{
    private static final Logger logger= LoggerFactory.getLogger(LoginRequestHandler.class);
    public static final LoginRequestHandler INSTANCE=new LoginRequestHandler();

    private UserService userService;

    private LoginRequestHandler()
    {
        userService= SpringBeanFactory.getBean(UserService.class);
    }

    @Override
    protected void channelRead0 (ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) throws Exception
    {
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(loginRequestPacket.getVersion());
        loginResponsePacket.setUserName(loginRequestPacket.getUserName());

        JSONObject validResp=userService.valid(loginRequestPacket.getUserName(),loginRequestPacket.getPassword());

        if(validResp.get("error")==null)
        {
            loginResponsePacket.setSuccess(true);
            String userId = String.valueOf( validResp.get("userId"));
            loginResponsePacket.setUserId(userId);
            System.out.println("[" + loginRequestPacket.getUserName() + "]登录成功");
            Session session=new Session(userId,loginRequestPacket.getUserName());
            SessionUtil.bindSession(session,ctx.channel());
        }
        else
        {
            loginResponsePacket.setReason((String) validResp.get("error"));
            loginResponsePacket.setSuccess(false);
            System.out.println(new Date() +" "+ loginRequestPacket.getUserName()+": 登录失败!");
        }

        ctx.writeAndFlush(loginResponsePacket);
    }


    @Override
    public void channelInactive (ChannelHandlerContext ctx) throws Exception
    {
        SessionUtil.unBindSession(ctx.channel());
    }

    @Override
    public void exceptionCaught (ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        ctx.close();
        logger.error(cause.getMessage(),cause);
    }
}
