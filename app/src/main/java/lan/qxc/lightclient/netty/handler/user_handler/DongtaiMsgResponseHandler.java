package lan.qxc.lightclient.netty.handler.user_handler;

import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lan.qxc.lightclient.config.ContextActionStr;
import lan.qxc.lightclient.config.friends_config.FriendCatcheUtil;
import lan.qxc.lightclient.config.mseeage_config.DongtaiMsgCacheUtil;
import lan.qxc.lightclient.config.mseeage_config.MessageCacheUtil;
import lan.qxc.lightclient.entity.Dongtai;
import lan.qxc.lightclient.entity.DongtaiMsg;
import lan.qxc.lightclient.entity.DongtaiMsgVO;
import lan.qxc.lightclient.entity.DongtailVO;
import lan.qxc.lightclient.entity.message.FriendMsgVO;
import lan.qxc.lightclient.netty.protocol.packet.dongtai_msg_packet.DongtaiMsgPacket;
import lan.qxc.lightclient.netty.protocol.packet.friend_msg_packet.FriendMsgPacket;
import lan.qxc.lightclient.service.NettyService;

public class DongtaiMsgResponseHandler extends SimpleChannelInboundHandler<DongtaiMsgPacket> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DongtaiMsgPacket packet) throws Exception {

        DongtailVO dongtai = packet.getDongtailVO();
        DongtaiMsgVO dongtaiMsg = packet.getDongtaiMsgVO();

        DongtaiMsgCacheUtil.addDongtaiMsg(dongtai,dongtaiMsg);
        DongtaiMsgCacheUtil.setMsgNotReadNum(DongtaiMsgCacheUtil.msgNotReadNum+1);


        //刷新消息窗口界面
        Intent intent=new Intent(ContextActionStr.notification_msg_frag_action);
        intent.putExtra("type","freshDongtaiMsg");
        NettyService.nettyService.sendCast(intent);


    }
}
