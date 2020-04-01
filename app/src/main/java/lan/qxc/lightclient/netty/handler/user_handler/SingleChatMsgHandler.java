package lan.qxc.lightclient.netty.handler.user_handler;

import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lan.qxc.lightclient.config.ContextActionStr;
import lan.qxc.lightclient.config.friends_config.FriendCatcheUtil;
import lan.qxc.lightclient.config.mseeage_config.MessageCacheUtil;
import lan.qxc.lightclient.entity.message.FriendMsgVO;
import lan.qxc.lightclient.entity.message.SingleChatMsg;
import lan.qxc.lightclient.netty.protocol.packet.chat_msg.SingleChatMsgPacket;
import lan.qxc.lightclient.netty.protocol.packet.friend_msg_packet.FriendMsgPacket;
import lan.qxc.lightclient.service.NettyService;

public class SingleChatMsgHandler extends SimpleChannelInboundHandler<SingleChatMsgPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SingleChatMsgPacket packet) throws Exception {

        SingleChatMsg singleChatMsg = packet.getSingleChatMsg();


//        Intent intent2=new Intent(ContextActionStr.contact_frag_action);
//        intent2.putExtra("type","freshadapter");
//        NettyService.nettyService.sendCast(intent2);

    }

}
