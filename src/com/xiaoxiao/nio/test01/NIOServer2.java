package com.xiaoxiao.nio.test01;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * NIO服务端
 * @author 小路
 */
public class NIOServer2 {
	//通道管理器
	private Selector selector;

	private ServerSocketChannel serverChannel;

	/**
	 * 获得一个ServerSocket通道，并对该通道做一些初始化的工作
	 * @param port  绑定的端口号
	 * @throws IOException
	 */
	public void initServer(int port) throws IOException {
		// 获得一个ServerSocket通道
		serverChannel = ServerSocketChannel.open();
		System.out.println("开启通道");
		 
		// 设置通道为非阻塞
		serverChannel.configureBlocking(true);
		System.out.println("设置为非阻塞");
		
		// 将该通道对应的ServerSocket绑定到port端口
		serverChannel.socket().bind(new InetSocketAddress(port));
		System.out.println("绑定端口");

		// 获得一个通道管理器
		this.selector = Selector.open();
		System.out.println("获取通道");
	}


	/**
	 * 采用轮询的方式监听selector上是否有需要处理的事件，如果有，则进行处理SelectableChannel
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void listen() throws Exception {
		System.out.println("服务端启动成功！");
		// 轮询访问selector
		while (true) {
			//当注册的事件到达时，方法返回；否则,该方法会一直阻塞
			selector.select();
			System.out.println("star");
			// 获得selector中选中的项的迭代器，选中的项为注册的事件
			Iterator ite = this.selector.selectedKeys().iterator();
			while (ite.hasNext()) {
				SelectionKey key = (SelectionKey) ite.next();
				// 删除已选的key,以防重复处理
				ite.remove();
				// 客户端请求连接事件
				if (key.isReadable()) {
					Thread.sleep(1000);
					 System.out.println("服务端读");
				     read(key);
				} else if (key.isWritable()){
					System.out.println("服务端写");
				}

			}

		}
	}
	/**
	 * 处理读取客户端发来的信息 的事件
	 * @param key
	 * @throws IOException 
	 */
	public void read(SelectionKey key) throws IOException{
		// 服务器可读取消息:得到事件发生的Socket通道
		SocketChannel channel = (SocketChannel) key.channel();
		// 创建读取的缓冲区
		ByteBuffer buffer = ByteBuffer.allocate(10);
		channel.read(buffer);
		byte[] data = buffer.array();
		String msg = new String(data).trim();
		System.out.println("服务端收到信息："+msg);

		ByteBuffer outBuffer = ByteBuffer.wrap(msg.getBytes());
		channel.write(outBuffer);// 将消息回送给客户端
	}
	
	/**
	 * 启动服务端测试
	 * @throws IOException 
	 */
	public static void main(String[] args) throws Exception {
		NIOServer2 server = new NIOServer2();
		server.initServer(8000);
//		server.initServer(6000);
		server.acceptors();
	
	}

	public void acceptors() {
		new Thread(()->{
			while (true) {
				System.out.println("acceptors：star");

				try {
					SocketChannel channel = serverChannel.accept();
					// 设置成非阻塞
					channel.configureBlocking(false);

					//在这里可以给客户端发送信息哦
					channel.write(ByteBuffer.wrap(new String("server").getBytes()));
					//在和客户端连接成功之后，为了可以接收到客户端的信息，需要给通道设置读的权限。
					channel.register(this.selector, SelectionKey.OP_READ);
					this.listen();
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("acceptors：end");

			}
		}).start();
	}

}

