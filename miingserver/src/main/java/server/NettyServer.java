package server;

import config.ConfigEntity;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;


public class NettyServer {
    NioEventLoopGroup boss;
    NioEventLoopGroup work;
    ServerBootstrap bootstrap;

    public void run(int beginPort, int endPort) {
        try {
            boss = new NioEventLoopGroup();
            work = new NioEventLoopGroup();
            bootstrap = new ServerBootstrap();
            bootstrap.group(boss, work)
                    .channel(NioServerSocketChannel.class)
                    .childOption(ChannelOption.SO_REUSEADDR, true)//快速复用端口
                    .childHandler(new NettyServerHandler());
                 /*  .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel c) throws Exception {
                            ChannelPipeline pipeline = c.pipeline();
                            pipeline.addLast(new NettyServerHandler());
                        }
                    });*/
            for (; beginPort < endPort; beginPort++) {
                int port = beginPort;
                //绑定端口，同步阻塞成功
                ChannelFuture fu = bootstrap.bind(port).sync().addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture cf) throws Exception {
                        if(cf.isSuccess()){
                            System.out.println("NettyServer 启动成功，端口号：" + port);
                        }

                    }
                });
            }
//            System.out.println("NettyServer 启动成功，端口号：" + port);
            //等待服务端监听端口关闭

//            System.out.println("服务器启动中~~~~");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ///关闭资源
           /* if (boss != null) {
                boss.shutdownGracefully();
            }
            if (work != null) {
                work.shutdownGracefully();
            }*/
        }
    }
    public static void main(String[] args) {
        new NettyServer().run(ConfigEntity.beginPort, ConfigEntity.endPort);
    }


}
