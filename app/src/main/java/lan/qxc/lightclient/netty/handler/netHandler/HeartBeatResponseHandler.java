package lan.qxc.lightclient.netty.handler.netHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lan.qxc.lightclient.netty.protocol.response.netResponse.HeartBeatResponsePacket;


public class HeartBeatResponseHandler extends SimpleChannelInboundHandler<HeartBeatResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HeartBeatResponsePacket heartBeatResponsePacket) throws Exception {

    }

}
