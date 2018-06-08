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
		// ���һ��ͨ��������
		Selector selector = Selector.open();

		ServerSocketChannel serverChannel = ServerSocketChannel.open();
		serverChannel.configureBlocking(false);
		SelectionKey key1 = serverChannel.register(selector,
				SelectionKey.OP_ACCEPT); // ����selectionKey����
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
		System.out.println("�󶨶˿�");

		while (true) {
			// ��ע����¼�����ʱ���������أ�����,�÷�����һֱ����
			selector.select();
			System.out.println("star");
			// ���selector��ѡ�е���ĵ�������ѡ�е���Ϊע����¼�
			Iterator ite = selector.selectedKeys().iterator();
			
			System.out.println(selector.selectedKeys().size());
			
			while (ite.hasNext()) {
				SelectionKey key = (SelectionKey) ite.next();

				// ɾ����ѡ��key,�Է��ظ�����
				ite.remove();
				System.out.println(selector.selectedKeys().size());
				
				// �ͻ������������¼�
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

					// ��úͿͻ������ӵ�ͨ��
					SocketChannel channel = server.accept();
					// ���óɷ�����
					channel.configureBlocking(false);
					// ��������Ը��ͻ��˷�����ϢŶ
					channel.write(ByteBuffer.wrap(new String("server")
							.getBytes()));
					// �ںͿͻ������ӳɹ�֮��Ϊ�˿��Խ��յ��ͻ��˵���Ϣ����Ҫ��ͨ�����ö���Ȩ�ޡ�
					channel.register(selector, SelectionKey.OP_READ);
				} else if (key.isReadable()) {
					Thread.sleep(1000);
					System.out.println("����˶�");
					read(key);
				}

			}

		}

	}
	
	public static void read(SelectionKey key) throws IOException{
		// �������ɶ�ȡ��Ϣ:�õ��¼�������Socketͨ��
		SocketChannel channel = (SocketChannel) key.channel();
		// ������ȡ�Ļ�����
		ByteBuffer buffer = ByteBuffer.allocate(10);
		channel.read(buffer);
		byte[] data = buffer.array();
		String msg = new String(data).trim();
		System.out.println("������յ���Ϣ��"+msg);
		ByteBuffer outBuffer = ByteBuffer.wrap(msg.getBytes());
		channel.write(outBuffer);// ����Ϣ���͸��ͻ���
	}

}
