package server;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


@ChannelHandler.Sharable
public class NettyServerHandler extends ChannelInboundHandlerAdapter {



    private AtomicInteger atomicInteger = new AtomicInteger();


    NettyServerHandler(){
        //线程池，定时执行
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("当前连接数="+atomicInteger.get());
            }
        },0,3, TimeUnit.SECONDS);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        atomicInteger.incrementAndGet();//加一
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        atomicInteger.decrementAndGet();//减一
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
