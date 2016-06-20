package com.supermy.geo;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.ssl.SslContext;


/**
 * 客户端 ChannelInitializer
 * 
 * @author moyong
 */
public class SimpleChatClientInitializer extends ChannelInitializer<SocketChannel> {

    private final SslContext sslCtx;

    public SimpleChatClientInitializer(SslContext sslCtx) {
        this.sslCtx = sslCtx;
    }

	@Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(sslCtx.newHandler(ch.alloc(), SimpleChatClient.HOST, SimpleChatClient.PORT));

        //8192/4096/1024
        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(2048, Delimiters.lineDelimiter()));
        pipeline.addLast("decoder", new StringDecoder());
        pipeline.addLast("encoder", new StringEncoder());
        pipeline.addLast("handler", new SimpleChatClientHandler());
    }
}
