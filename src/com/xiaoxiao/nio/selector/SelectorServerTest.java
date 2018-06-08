package com.xiaoxiao.nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class SelectorServerTest {

	public static void main(String[] args) throws Exception {
		// 获得一个通道管理器
		Selector selector = Selector.open();

		ServerSocketChannel serverChannel = ServerSocketChannel.open();
		serverChannel.configureBlocking(false);
		SelectionKey key1 = serverChannel.register(selector,
				SelectionKey.OP_ACCEPT); // 返回selectionKey类型
		int interestSet = key1.interestOps();
		boolean isInterestedInAccept = (interestSet & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT;
		boolean isInterestedInConnect = (interestSet & SelectionKey.OP_CONNECT) == SelectionKey.OP_CONNECT;
		boolean isInterestedInRead = (interestSet & SelectionKey.OP_READ) == SelectionKey.OP_READ;
		boolean isInterestedInWrite = (interestSet & SelectionKey.OP_WRITE) == SelectionKey.OP_WRITE;
		System.out.println(isInterestedInAccept);
		System.out.println(isInterestedInConnect);
		System.out.println(isInterestedInRead);
		System.out.println(isInterestedInWrite);

		serverChannel.socket().bind(new InetSocketAddress(8001));
		System.out.println("绑定端口");

		while (true) {
			// 当注册的事件到达时，方法返回；否则,该方法会一直阻塞
			selector.select();
			System.out.println("star");
			// 获得selector中选中的项的迭代器，选中的项为注册的事件
			Iterator ite = selector.selectedKeys().iterator();
			
			System.out.println(selector.selectedKeys().size());
			
			while (ite.hasNext()) {
				SelectionKey key = (SelectionKey) ite.next();

				// 删除已选的key,以防重复处理
				ite.remove();
				System.out.println(selector.selectedKeys().size());
				
				// 客户端请求连接事件
				if (key.isAcceptable()) {
					ServerSocketChannel server = (ServerSocketChannel) key
							.channel();
					System.out.println(key1.equals(key));
					int readySet = key.readyOps();
					isInterestedInAccept = (readySet & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT;
					isInterestedInConnect = (readySet & SelectionKey.OP_CONNECT) == SelectionKey.OP_CONNECT;
					isInterestedInRead = (readySet & SelectionKey.OP_READ) == SelectionKey.OP_READ;
					isInterestedInWrite = (readySet & SelectionKey.OP_WRITE) == SelectionKey.OP_WRITE;
					System.out.println(isInterestedInAccept);
					System.out.println(isInterestedInConnect);
					System.out.println(isInterestedInRead);
					System.out.println(isInterestedInWrite);

					// 获得和客户端连接的通道
					SocketChannel channel = server.accept();
					// 设置成非阻塞
					channel.configureBlocking(false);
					// 在这里可以给客户端发送信息哦
					channel.write(ByteBuffer.wrap(new String("server")
							.getBytes()));
					// 在和客户端连接成功之后，为了可以接收到客户端的信息，需要给通道设置读的权限。
					channel.register(selector, SelectionKey.OP_READ);
				} else if (key.isReadable()) {
					Thread.sleep(1000);
					System.out.println("服务端读");
					read(key);
				}

			}

		}

	}
	
	public static void read(SelectionKey key) throws IOException{
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

}
