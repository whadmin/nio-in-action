package com.xiaoxiao.nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class SelectorClientTest {

	public static void main(String[] args) throws Exception {
		// ���һ��ͨ��������
		Selector selector = Selector.open();

		// ���һ��Socketͨ��
		SocketChannel channel = SocketChannel.open();
		// ����ͨ��Ϊ������
		channel.configureBlocking(false);
		// ��ͨ���������͸�ͨ���󶨣���Ϊ��ͨ��ע��SelectionKey.OP_ACCEPT�¼�,ע����¼���
		// �����¼�����ʱ��selector.select()�᷵�أ�������¼�û����selector.select()��һֱ������
		int interestSet = SelectionKey.OP_READ | SelectionKey.OP_WRITE | SelectionKey.OP_CONNECT;

		System.out.println("==========================================================================");
		SelectionKey key=channel.register(selector, interestSet); // ����selectionKey����
		interestSet = key.interestOps();
		boolean isInterestedInAccept = (interestSet & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT;
		boolean isInterestedInConnect = (interestSet & SelectionKey.OP_CONNECT) == SelectionKey.OP_CONNECT;
		boolean isInterestedInRead = (interestSet & SelectionKey.OP_READ) == SelectionKey.OP_READ;
		boolean isInterestedInWrite = (interestSet & SelectionKey.OP_WRITE) == SelectionKey.OP_WRITE;
		System.out.println(isInterestedInAccept);
		System.out.println(isInterestedInConnect);
		System.out.println(isInterestedInRead);
		System.out.println(isInterestedInWrite);
		

			
	}

}
