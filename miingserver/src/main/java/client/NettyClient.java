package client;

import config.ConfigEntity;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;


public class NettyClient {

    public static void main(String[] args) {
        new NettyClient().run(ConfigEntity.beginPort, ConfigEntity.endPort);
    }

    private void run(int beginPort, int endPort) {
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventExecutors).channel(NioSocketChannel.class)
                .option(ChannelOption.SO_REUSEADDR,true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {

                    }
                });

        int index=0;
        int finalPort;
        while (true){
            finalPort=beginPort+index;
            try {
                bootstrap.connect(ConfigEntity.host,finalPort).addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture future) throws Exception {
                        if(!future.isSuccess()){
                            System.out.println("链接失败");
                        }else{
                            System.out.println("链接成功");
                        }
                    }
                }).get();

                ++index;
                if(endPort-beginPort==index){
                    index=0;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


}
