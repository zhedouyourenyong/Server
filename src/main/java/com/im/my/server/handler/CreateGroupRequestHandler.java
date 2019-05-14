package com.im.my.server.handler;

import com.im.my.protocol.request.CreateGroupRequestPacket;
import com.im.my.protocol.response.CreateGroupResponsePacket;
import com.im.my.session.Session;
import com.im.my.utils.IdUtil;
import com.im.my.utils.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


@ChannelHandler.Sharable
public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket>
{
    private static final Logger logger= LoggerFactory.getLogger(CreateGroupRequestHandler.class);

    public static final CreateGroupRequestHandler INSTANCE=new CreateGroupRequestHandler();

    private CreateGroupRequestHandler()
    {
    }

    @Override
    protected void channelRead0 (ChannelHandlerContext ctx, CreateGroupRequestPacket msg) throws Exception
    {
        List<String> userIdList=msg.getUserIdList();
        List<String> userNameList=new ArrayList<>();
        ChannelGroup channelGroup=new DefaultChannelGroup(ctx.executor());

        //将本身添加进群组
        Session myself =SessionUtil.getSession(ctx.channel());
        userIdList.add(myself.getUserId());

        for (String userId : userIdList)
        {
            Channel channel = SessionUtil.getChannel(userId);
            if(channel != null)
            {
                channelGroup.add(channel);
                userNameList.add(SessionUtil.getSession(channel).getUserName());
            }
        }

        String groupId= IdUtil.randomId();
        CreateGroupResponsePacket resp = new CreateGroupResponsePacket();
        resp.setGroupId(groupId);
        resp.setSuccess(true);
        resp.setUserNameList(userNameList);

        channelGroup.writeAndFlush(resp);

        System.out.print("群创建成功，id 为 " + resp.getGroupId() + ", ");
        System.out.println("群里面有：" + resp.getUserNameList());

        SessionUtil.bindChannelGroup(groupId, channelGroup);
    }

    @Override
    public void exceptionCaught (ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        ctx.close();
        logger.error(cause.getMessage(),cause);
    }
}
