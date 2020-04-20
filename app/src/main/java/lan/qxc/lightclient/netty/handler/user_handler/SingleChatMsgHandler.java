package lan.qxc.lightclient.netty.handler.user_handler;

import android.content.Intent;

import java.util.Date;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lan.qxc.lightclient.config.ContextActionStr;
import lan.qxc.lightclient.config.mseeage_config.MessageCacheUtil;
import lan.qxc.lightclient.entity.message.SingleChatMsg;
import lan.qxc.lightclient.netty.protocol.packet.chat_msg.SingleChatMsgPacket;
import lan.qxc.lightclient.service.NettyService;
import lan.qxc.lightclient.util.MyTimeUtil;

public class SingleChatMsgHandler extends SimpleChannelInboundHandler<SingleChatMsgPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SingleChatMsgPacket packet) throws Exception {

        SingleChatMsg singleChatMsg = packet.getSingleChatMsg();

        String time = MyTimeUtil.getTimeByTimeStamp(singleChatMsg.getCreatetime(),"yyyy-MM-dd HH:MM:ss");
        singleChatMsg.setCreatetime(time);


        //首先  存储消息至未读队列  更新消息界面窗口
        MessageCacheUtil.receiveSingleChatMsg(singleChatMsg);

        //然后通知消息界面
        Intent intent=new Intent(ContextActionStr.notification_msg_frag_action);
        intent.putExtra("type","freshadapter");
        NettyService.nettyService.sendCast(intent);


        //最后通知聊天界面
        Intent intent2=new Intent(ContextActionStr.single_chat_activity_action);
        intent2.putExtra("type","receivemsg");
        intent2.putExtra("userid",singleChatMsg.getSendUid());
        NettyService.nettyService.sendCast(intent2);


    }

}
