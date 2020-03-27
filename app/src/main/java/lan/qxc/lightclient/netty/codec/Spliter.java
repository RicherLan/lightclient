package lan.qxc.lightclient.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lan.qxc.lightclient.netty.netty_client.NettyClient;
import lan.qxc.lightclient.netty.netty_client.NettyListener;
import lan.qxc.lightclient.netty.protocol.PacketCodec;

public class Spliter extends LengthFieldBasedFrameDecoder {
    private static final int LENGTH_FIELD_OFFSET = 10;
    private static final int LENGTH_FIELD_LENGTH = 4;

    public Spliter() {
        super(Integer.MAX_VALUE, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        if (in.getInt(in.readerIndex()) != PacketCodec.MAGIC_NUMBER) {
            ctx.channel().close();
            NettyClient.getInstance().setConnectStatus(false);
            NettyClient.getInstance().getListener().onNettyServiceStatusConnectChanged(NettyListener.STATUS_CONNECT_ERROR);
            NettyClient.getInstance().connect();
            return null;
        }

        return super.decode(ctx, in);
    }
}
