package com.im.my.server.handler;

import com.im.my.protocol.request.LoginRequestPacket;
import com.im.my.protocol.response.LoginResponsePacket;
import com.im.my.session.Session;
import com.im.my.utils.IdUtil;
import com.im.my.utils.SessionUtil;
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

    private LoginRequestHandler()
    {
    }

    @Override
    protected void channelRead0 (ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) throws Exception
    {
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(loginRequestPacket.getVersion());
        loginResponsePacket.setUserName(loginRequestPacket.getUserName());

        if(valid(loginRequestPacket))
        {
            loginResponsePacket.setSuccess(true);
            String userId = IdUtil.randomId();
            loginResponsePacket.setUserId(userId);
            System.out.println("[" + loginRequestPacket.getUserName() + "]登录成功");
            Session session=new Session(userId,loginRequestPacket.getUserName());
            SessionUtil.bindSession(session,ctx.channel());
        }
        else
        {
            loginResponsePacket.setReason("账号密码校验失败");
            loginResponsePacket.setSuccess(false);
            System.out.println(new Date() + ": 登录失败!");
        }

        ctx.writeAndFlush(loginResponsePacket);
    }

    private boolean valid(LoginRequestPacket packet)
    {
        //在这里加入验证机制
        return true;
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
