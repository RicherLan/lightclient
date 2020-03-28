package lan.qxc.lightclient.netty.handler.user_handler;

import android.content.Intent;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lan.qxc.lightclient.config.ContextActionStr;
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


//        Intent intent=new Intent(ContextActionStr.login_activity_action);
//        intent.putExtra("type","loginrs");
//        intent.putExtra("rs",rs);
//        NettyService.nettyService.sendCast(intent);

    }

}
