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
 * NIO�����
 * @author С·
 */
public class NIOServer2 {
	//ͨ��������
	private Selector selector;

	private ServerSocketChannel serverChannel;

	/**
	 * ���һ��ServerSocketͨ�������Ը�ͨ����һЩ��ʼ���Ĺ���
	 * @param port  �󶨵Ķ˿ں�
	 * @throws IOException
	 */
	public void initServer(int port) throws IOException {
		// ���һ��ServerSocketͨ��
		serverChannel = ServerSocketChannel.open();
		System.out.println("����ͨ��");
		 
		// ����ͨ��Ϊ������
		serverChannel.configureBlocking(true);
		System.out.println("����Ϊ������");
		
		// ����ͨ����Ӧ��ServerSocket�󶨵�port�˿�
		serverChannel.socket().bind(new InetSocketAddress(port));
		System.out.println("�󶨶˿�");

		// ���һ��ͨ��������
		this.selector = Selector.open();
		System.out.println("��ȡͨ��");
	}


	/**
	 * ������ѯ�ķ�ʽ����selector���Ƿ�����Ҫ������¼�������У�����д���SelectableChannel
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void listen() throws Exception {
		System.out.println("����������ɹ���");
		// ��ѯ����selector
		while (true) {
			//��ע����¼�����ʱ���������أ�����,�÷�����һֱ����
			selector.select();
			System.out.println("star");
			// ���selector��ѡ�е���ĵ�������ѡ�е���Ϊע����¼�
			Iterator ite = this.selector.selectedKeys().iterator();
			while (ite.hasNext()) {
				SelectionKey key = (SelectionKey) ite.next();
				// ɾ����ѡ��key,�Է��ظ�����
				ite.remove();
				// �ͻ������������¼�
				if (key.isReadable()) {
					Thread.sleep(1000);
					 System.out.println("����˶�");
				     read(key);
				} else if (key.isWritable()){
					System.out.println("�����д");
				}

			}

		}
	}
	/**
	 * �����ȡ�ͻ��˷�������Ϣ ���¼�
	 * @param key
	 * @throws IOException 
	 */
	public void read(SelectionKey key) throws IOException{
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
	
	/**
	 * ��������˲���
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
				System.out.println("acceptors��star");

				try {
					SocketChannel channel = serverChannel.accept();
					// ���óɷ�����
					channel.configureBlocking(false);

					//��������Ը��ͻ��˷�����ϢŶ
					channel.write(ByteBuffer.wrap(new String("server").getBytes()));
					//�ںͿͻ������ӳɹ�֮��Ϊ�˿��Խ��յ��ͻ��˵���Ϣ����Ҫ��ͨ�����ö���Ȩ�ޡ�
					channel.register(this.selector, SelectionKey.OP_READ);
					this.listen();
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("acceptors��end");

			}
		}).start();
	}

}

