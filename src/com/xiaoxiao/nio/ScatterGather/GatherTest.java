package com.xiaoxiao.nio.ScatterGather;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class GatherTest {

    /**
     * ע��buffer���ȱ����뵽���飬Ȼ���ٽ�������Ϊchannel.read()
     * �����������read()��������buffer�������е�˳�򽫴�channel�ж�ȡ������д�뵽buffer����һ��buffer��д����channel����������һ��buffer��д�� Scattering
     * Reads���ƶ���һ��bufferǰ������������ǰ��buffer����Ҳ��ζ�����������ڶ�̬��Ϣ(����ע����Ϣ��С���̶�)�����仰˵�����������Ϣͷ����Ϣ�壬��Ϣͷ���������䣨���� 128byte����Scattering
     * Reads������������
     * 
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        RandomAccessFile writeFile = new RandomAccessFile("writedata.txt", "rw");
        FileChannel writeChannel = writeFile.getChannel();

        ByteBuffer header = ByteBuffer.allocate(10);
        ByteBuffer body = ByteBuffer.allocate(20);

        header.put("1234567890".getBytes());
        body.put("abcdefg".getBytes());

        header.flip();
        body.flip();

        ByteBuffer[] bufferArray = { header, body };

        writeChannel.write(bufferArray);

        writeChannel.close();
        writeFile.close();
    }

}
