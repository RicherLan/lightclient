package lan.qxc.lightclient.netty.handler.user_handler;

import android.content.Intent;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lan.qxc.lightclient.config.ContextActionStr;
import lan.qxc.lightclient.config.friends_config.FriendCatcheUtil;
import lan.qxc.lightclient.config.mseeage_config.MessageCacheUtil;
import lan.qxc.lightclient.entity.PersonalInfo;
import lan.qxc.lightclient.entity.message.FriendMsgVO;
import lan.qxc.lightclient.netty.protocol.packet.friend_msg_packet.FriendMsgPacket;
import lan.qxc.lightclient.netty.protocol.response.user_response.LoginResponsePacket;
import lan.qxc.lightclient.service.NettyService;
import lan.qxc.lightclient.util.GlobalInfoUtil;
import lan.qxc.lightclient.util.JsonUtils;
import lan.qxc.lightclient.util.SharePerferenceUtil;

public class FriendMsgResponseHandler extends SimpleChannelInboundHandler<FriendMsgPacket> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FriendMsgPacket packet) throws Exception {

        FriendMsgVO friendMsgVO =    packet.getFriendMsgVO();

        //取关   ui不显示     朋友列表界面要刷新
        if(friendMsgVO.getMsgtype().equals(2)){
            FriendCatcheUtil.userDelGuanzhu(friendMsgVO.getUserid());

            Intent intent=new Intent(ContextActionStr.notification_msg_frag_action);
            intent.putExtra("type","freshadapter");
            NettyService.nettyService.sendCast(intent);

            return;
        }

        List<FriendMsgVO> list = new ArrayList<>();
        list.add(friendMsgVO);
        MessageCacheUtil.updateFriendMsgs(list);

        Intent intent=new Intent(ContextActionStr.notification_msg_frag_action);
        intent.putExtra("type","freshadapter");
        NettyService.nettyService.sendCast(intent);

    }

}
