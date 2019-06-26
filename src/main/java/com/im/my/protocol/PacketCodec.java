package com.im.my.protocol;

import com.im.my.protocol.command.Command;
import com.im.my.protocol.request.*;
import com.im.my.serialize.Serializer;
import com.im.my.serialize.impl.JsonSerializer;
import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.Map;

/*
* 本类采取单例模式
* 首先将协议的各个部分读取出来
* 根据指令获取对应的请求对象的class
* 再根据序列化算法获取反序列化工具进行反序列化
* */

public class PacketCodec
{
    public static final PacketCodec INSTANCE = new PacketCodec();

    public static final int MAGIC_NUMBER = 0x16130505;
    private final Map<Byte,Class<?extends Packet> > packetTypeMap;
    private final Map<Byte, Serializer> serializerMap;

//  自动添加?? 配置文件??
    private PacketCodec()
    {
        packetTypeMap=new HashMap<>();
        packetTypeMap.put(Command.LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(Command.LOGOUT_REQUEST,LogoutRequestPacket.class);
        packetTypeMap.put(Command.MESSAGE_REQUEST, MessageRequestPacket.class);
        packetTypeMap.put(Command.REGISTER_REQUEST,RegisterRequestPacket.class);
        packetTypeMap.put(Command.SENSOR_REQUEST, SensorDataRequestPacket.class);
        packetTypeMap.put(Command.HEAT_BEAT_REQUEST,HeartBeatRequestPacket.class);
        packetTypeMap.put(Command.JOIN_GROUP_REQUEST,JoinGroupRequestPacket.class);
        packetTypeMap.put(Command.QUIT_GROUP_REQUEST,QuitGroupRequestPacket.class);
        packetTypeMap.put(Command.ADD_FRIEND_REQUEST,AddFriendRequestPacket.class);
        packetTypeMap.put(Command.CREATE_GROUP_REQUEST, CreateGroupRequestPacket.class);
        packetTypeMap.put(Command.DELETE_FRIEND_REQUEST,DeleteFriendRequestPacket.class);
        packetTypeMap.put(Command.SHOW_GROUP_LIST_REQUEST,ShowGroupListRequestPacket.class);
        packetTypeMap.put(Command.SHOW_FRIENDS_LIST_REQUEST,ShowFriendsListRequestPacket.class);
        packetTypeMap.put(Command.SEND_MESSAGE_TO_GROUP_REQUEST, GroupMessageRequestPacket.class);
        packetTypeMap.put(Command.LIST_GROUP_MEMBERS_REQUEST, ListGroupMembersRequestPacket.class);


        serializerMap = new HashMap<>();
        Serializer serializer = new JsonSerializer();
        serializerMap.put(serializer.getSerializerAlgorithm(), serializer);
    }

    public void encode(ByteBuf buf,Packet packet)
    {
        byte[] bytes= Serializer.DEFAULT.serialize(packet);
        buf.writeInt(MAGIC_NUMBER);
        buf.writeByte(packet.getVersion());
        buf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        buf.writeByte(packet.getCommand());
        buf.writeInt(bytes.length);
        buf.writeBytes(bytes);
    }

    public Packet decode(ByteBuf byteBuf)
    {
        byteBuf.skipBytes(4);
        byteBuf.skipBytes(1);

        byte serializeAlgorithm = byteBuf.readByte();
        byte command = byteBuf.readByte();
        int length = byteBuf.readInt();
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);

        if (requestType != null && serializer != null)
        {
            return serializer.deserialize(requestType, bytes);
        }
        return null;
    }

    private Serializer getSerializer(byte serializeAlgorithm)
    {
        return serializerMap.get(serializeAlgorithm);
    }

    private Class<? extends Packet> getRequestType(byte command)
    {
        return packetTypeMap.get(command);
    }
}
