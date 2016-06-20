package com.supermy.geo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * 简单聊天服务器-客户端
 *
 * @author moyong
 */
@Component
@Qualifier("chatclient")
@PropertySource("classpath:application.properties")
public class SimpleChatClient {



    @Value("${im.server.host}")
    private String imServerHost;

    @Value("${im.server.port}")
    private String imServerPort;


    static final String HOST = System.getProperty("host", "127.0.0.1");
    static final int PORT = Integer.parseInt(System.getProperty("port", "8090"));

    public static void main(String[] args) throws Exception {
//        new SimpleChatClient("127.0.0.1", 8090).runtest();
//       new SimpleChatClient("127.0.0.1", 8090).run("g::18610586586;1462870826752;106.72;26.57");


        new SimpleChatClient("127.0.0.1", 8090).run();

//        Thread.sleep(600000);

    }

    private volatile boolean closed = false;
    private volatile EventLoopGroup workerGroup;
    private volatile Bootstrap bootstrap;

    private ExecutorService executorService;
    private Channel channel = null;

    @Value("${im.server.host}")
    private  String host;

    @Value("${im.server.port}")
    private  int port;

    public SimpleChatClient() {
    }

    public SimpleChatClient(@Value("${im.server.host}") String host, @Value("${im.server.port}") int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * 给司机发送订单信息
     *
     * @param msg
     * @throws Exception
     */
    public void send(String msg)  {
        //自动构造数据交互 g::18610586586;1462870826752;106.72;26.57


        workerGroup = new NioEventLoopGroup();
        try {
            SslContext sslCtx = SslContext.newClientContext(InsecureTrustManagerFactory.INSTANCE);

            bootstrap = new Bootstrap()
                    .group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new SimpleChatClientInitializer(sslCtx));

            channel = bootstrap.connect(host, port).sync().channel();

            //司机
            channel.writeAndFlush(msg+ "\r\n");

            //键盘输入交互
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                channel.writeAndFlush(in.readLine() + "\r\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }


    }

    public void run() throws Exception {
        //自动构造数据交互 g::18610586586;1462870826752;106.72;26.57

        final SslContext sslCtx = SslContext.newClientContext(InsecureTrustManagerFactory.INSTANCE);

        workerGroup = new NioEventLoopGroup();
        try {
            bootstrap = new Bootstrap()
                    .group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new SimpleChatClientInitializer(sslCtx));

            channel = bootstrap.connect(host, port).sync().channel();
//            executorService = Executors.newFixedThreadPool(200);

//            executorService.execute(new Runnable() {
//                @Override
//                public void run() {
//                    channel.writeAndFlush("g::18610586586;1462870826752;106.72;26.57" + "\r\n");
//                }
//            });

            //定时执行
//            Channel ch = null; // Get reference to channel
//            ScheduledFuture<?> future = channel.eventLoop().scheduleAtFixedRate(
//                    new Runnable() {
//                        @Override
//                        public void run() {
//                            //todo 获取经纬度数据
//                            channel.writeAndFlush("g::18610586586;1462870826752;106.72;26.57" + "\r\n");
//
//                            System.out.println("Run every 3 seconds");
//
//                        }
//                    }, 2, 3, TimeUnit.SECONDS);

            //司机
            //channel.writeAndFlush("g::18610586586;1462870826752;106.72;26.57" + "\r\n");

            //LBS-Server
            channel.writeAndFlush("p::18610586586;张山;15510325588;1462870826752;106.72;26.57" + "\r\n");


//                    键盘输入交互
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                channel.writeAndFlush(in.readLine() + "\r\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }


    }


    public void runtest() throws Exception {
        final SslContext sslCtx = SslContext.newClientContext(InsecureTrustManagerFactory.INSTANCE);

//        EventLoopGroup
        workerGroup = new NioEventLoopGroup();
        try {
            bootstrap = new Bootstrap()
                    .group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new SimpleChatClientInitializer(sslCtx));

            //bootstrap.connect().addListener(new ConnectionListener(this));


            //insertData(channel);
//            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
//                @Override
//                public void initChannel(SocketChannel ch) throws Exception {
//                    ChannelPipeline pipeline = ch.pipeline();
//                    pipeline.addFirst(new ChannelInboundHandlerAdapter() {
//                        @Override
//                        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//                            super.channelInactive(ctx);
//                            ctx.channel().eventLoop().schedule(new Runnable() {
//                                @Override
//                                public void run() {
//                                    doConnect();
//                                }
//                            }, 3, TimeUnit.SECONDS);
//
//                        }
//                    });
//
//                    //todo: add more handler
//                }
//            });


            final Channel channel = bootstrap.connect(host, port).sync().channel();


            //自动构造数据交互 g::18610586586;1462870826752;106.72;26.57
            ExecutorService executorService = Executors.newFixedThreadPool(200);

            //始终使用同一个链接
            for (int i = 0; i < 1; i++) {
                //模拟用户 压力测试mongodb
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        insertData(channel);
                    }
                });
            }

            //           doConnect(); //自动重连

            //键盘输入交互
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                channel.writeAndFlush(in.readLine() + "\r\n");
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }

    }


    public void doConnect() {
        if (closed) {
            return;
        }

        ChannelFuture future = bootstrap.connect(new InetSocketAddress(host, port));

        future.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture f) throws Exception {
                if (f.isSuccess()) {
                    System.out.println("Started Tcp Client: " + getServerInfo());
                } else {
                    System.out.println("Started Tcp Client Failed: " + getServerInfo());
                    f.channel().eventLoop().schedule(new Runnable() {
                        @Override
                        public void run() {
                            doConnect();
                        }
                    }, 3, TimeUnit.SECONDS);
                }
            }
        });
    }

    private String getServerInfo() {
        return String.format("RemoteHost=%s RemotePort=%d",
                host,
                port);
    }

    /**
     * 模拟测试数据,插入进行压力测试.
     *
     * @param channel
     * @throws InterruptedException
     */
    private void insertData(Channel channel) {
        for (int i = 0; i < 800; i++) {
            double x = i % 7;
            double y = Math.floor(i / 10) / 10;
            StringBuffer sb = new StringBuffer();
            sb.append("g::");
            sb.append(Thread.currentThread().getName() + i).append(";");
            sb.append(new Date().getTime()).append(";");
            sb.append(x).append(";").append(y);

            System.out.println(Thread.currentThread().getName() + "places added: " + sb.toString());

            channel.writeAndFlush(sb.toString() + "\r\n");

            try {
                Thread.sleep(10);//根据网络的质量调整此参数
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(Thread.currentThread().getName() + "All places added");
    }


    public void close() {
        closed = true;
        workerGroup.shutdownGracefully();
        System.out.println("Stopped Tcp Client: " + getServerInfo());
    }

}
