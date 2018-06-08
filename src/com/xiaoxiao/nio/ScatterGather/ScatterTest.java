package com.xiaoxiao.nio.ScatterGather;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * ע��buffer���ȱ����뵽���飬Ȼ���ٽ�������Ϊchannel.read()
 * �����������read()��������buffer�������е�˳�򽫴�channel�ж�ȡ������д�뵽buffer����һ��buffer��д����channel����������һ��buffer��д�� Scattering
 * Reads���ƶ���һ��bufferǰ������������ǰ��buffer����Ҳ��ζ�����������ڶ�̬��Ϣ(����ע����Ϣ��С���̶�)�����仰˵�����������Ϣͷ����Ϣ�壬��Ϣͷ���������䣨���� 128byte����Scattering
 * Reads��������������
 */
public class ScatterTest {

    public static void main(String[] args) throws Exception {

        RandomAccessFile readFile = new RandomAccessFile("readdata.txt", "rw");
        RandomAccessFile writeFile = new RandomAccessFile("writedata.txt", "rw");

        FileChannel readChannel = readFile.getChannel();
        FileChannel writeChannel = writeFile.getChannel();

        ByteBuffer header = ByteBuffer.allocate(10);
        ByteBuffer body = ByteBuffer.allocate(10);

        ByteBuffer[] bufferArray = { header, body };

        long bytesRead;
        bytesRead = readChannel.read(bufferArray, 0, 10);
        header.flip();
        writeChannel.write(header);
        header.clear();

        body.flip();
        writeChannel.write(body);
        body.clear();

        readChannel.close();
        writeChannel.close();
        readFile.close();
        writeFile.close();
    }

}
