package com.github.AllenDuke.nettyMemoryLeakTest;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;

public class MyClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        ByteBuf buffer = Unpooled.copiedBuffer("server start sending ", Charset.forName("utf-8"));
        ctx.writeAndFlush(buffer);

    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        //不释放msg
        System.out.println(msg);
    }

}
//最终OOM
//public class MyClientHandler extends ChannelInboundHandlerAdapter {
//
//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//
//        ByteBuf buffer = Unpooled.copiedBuffer("server start sending ", Charset.forName("utf-8"));
//        ctx.writeAndFlush(buffer);
//
//    }
//
//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        //不释放msg
//        System.out.println(msg);
//    }
//
//}