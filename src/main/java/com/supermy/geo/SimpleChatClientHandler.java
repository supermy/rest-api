package com.supermy.geo;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 客户端 channel
 * 
 * @author moyong
 */
public class SimpleChatClientHandler extends SimpleChannelInboundHandler<String> {
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
		Channel incoming = ctx.channel();

		//todo 处理服务器回复的消息
		System.out.println(s);


	}
}
