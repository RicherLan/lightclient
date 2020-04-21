package lan.qxc.lightclient.netty.handler.user_handler;

import android.content.Intent;

import java.util.HashMap;
import java.util.Map;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lan.qxc.lightclient.config.ContextActionStr;
import lan.qxc.lightclient.entity.PersonalInfo;
import lan.qxc.lightclient.entity.User;
import lan.qxc.lightclient.netty.config.NettyConfig;
import lan.qxc.lightclient.netty.protocol.response.user_response.LoginResponsePacket;
import lan.qxc.lightclient.service.NettyService;
import lan.qxc.lightclient.ui.activity.home.HomeActivity;
import lan.qxc.lightclient.ui.activity.user_activitys.LoginActivity;
import lan.qxc.lightclient.util.GlobalInfoUtil;
import lan.qxc.lightclient.util.JsonUtils;
import lan.qxc.lightclient.util.SharePerferenceUtil;

public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket packet) throws Exception {

        String rs = packet.getRs();
        PersonalInfo personalInfo = packet.getPersonalInfo();

        Intent intent=new Intent(ContextActionStr.login_activity_action);
        intent.putExtra("type","loginrs");
        intent.putExtra("rs",rs);
        NettyService.nettyService.sendCast(intent);

        if(rs.equals("SUCCESS")){
            NettyConfig.isLogined = true;
            if(NettyService.loginTimer!=null){
                NettyService.loginTimer.cancel();
            }

            GlobalInfoUtil.personalInfo = personalInfo;

            GlobalInfoUtil.personalInfo.setPassword(personalInfo.getPassword());
            Map<String ,String > map = new HashMap<>();
            map.put(SharePerferenceUtil.sh_login_username,personalInfo.getPhone());
            map.put(SharePerferenceUtil.sh_login_password,personalInfo.getPassword());
            map.put(SharePerferenceUtil.sh_personal_headicon,GlobalInfoUtil.personalInfo.getIcon());

            map.put(SharePerferenceUtil.sh_kip_login,"Y");
            map.put(SharePerferenceUtil.sh_personal_info,JsonUtils.objToJson(GlobalInfoUtil.personalInfo));

            SharePerferenceUtil.save_User_SP(NettyService.nettyService,map);
        }else{
            Map<String ,String > map = new HashMap<>();
            map.put(SharePerferenceUtil.sh_login_username,personalInfo.getPhone());
            map.put(SharePerferenceUtil.sh_login_password,"");
            map.put(SharePerferenceUtil.sh_kip_login,"N");

            SharePerferenceUtil.save_User_SP(NettyService.nettyService,map);
        }


    }
}
