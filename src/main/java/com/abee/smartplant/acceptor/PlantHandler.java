package com.abee.smartplant.acceptor;

import com.abee.smartplant.container.PlantContainer;
import com.abee.smartplant.entity.Plant;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PlantHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf = (ByteBuf) msg;
		CharSequence cs = buf.readCharSequence(buf.readableBytes(), Charset.defaultCharset());

		String recv = cs.toString();
		if (recv.length() > 1 && recv.charAt(0) == '{' && recv.charAt(recv.length() - 1) == '}') {
			String[] values = recv.substring(1, recv.length() - 1).split(";");
			process(values, ctx);
		}

		System.out.println("received: " + cs.toString());

		super.channelRead(ctx, msg);
	}

	private void process(String[] values, ChannelHandlerContext ctx) {
		Plant.State state = new Plant.State();
		Date d = new Date();
		System.out.println(d);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateNowStr = sdf.format(d);
		state.setTime(dateNowStr);
		Plant p = new Plant();
		for (String v : values) {
			String[] kv = v.split(":");
			if ("n".equals(kv[0])) {
				p.setName(kv[1]);
				InetSocketAddress a = (InetSocketAddress) ctx.channel().remoteAddress();
				p.setIp(a.getHostString());
				p.setPort(a.getPort());
			}
			if ("t".equals(kv[0])) {
				state.setTemperature(Float.parseFloat(kv[1]));
			}
			if ("h".equals(kv[0])) {
				state.setHumidity(Float.parseFloat(kv[1]));
			}
		}
		PlantContainer.put(p, state);
	}
}
