package com.abee.smartplant.acceptor;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {

	public static void main(String[] args) throws InterruptedException {
		//创建两个线程组 bossGroup、workerGroup
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			//创建服务端的启动对象，设置参数
			ServerBootstrap bootstrap = new ServerBootstrap();
			//设置两个线程组boosGroup和workerGroup
			bootstrap.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.option(ChannelOption.SO_BACKLOG, 128)
					.childOption(ChannelOption.SO_KEEPALIVE, true)
					//使用匿名内部类的形式初始化通道对象
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel socketChannel) throws Exception {
							//给pipeline管道设置处理器
							socketChannel.pipeline()
									.addLast(new PlantHandler());
						}
					});
			//绑定端口号，启动服务端
			ChannelFuture channelFuture = bootstrap.bind(8888).sync();
			//对关闭通道进行监听
			channelFuture.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}
