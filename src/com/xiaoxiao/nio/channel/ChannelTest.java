package com.xiaoxiao.nio.channel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * http://ifeve.com/channels/
 * 
 * @author xu.jianguo Java NIO��ͨ����������������Щ��ͬ�� �ȿ��Դ�ͨ���ж�ȡ���ݣ��ֿ���д���ݵ�ͨ���������Ķ�дͨ���ǵ���ġ� ͨ�������첽�ض�д��
 * ͨ���е���������Ҫ�ȶ���һ��Buffer����������Ҫ��һ��Buffer��д�롣 Channel��ʵ�� ��Щ��Java NIO������Ҫ��ͨ����ʵ�֣� FileChannel DatagramChannel SocketChannel
 * ServerSocketChannel FileChannel ���ļ��ж�д���ݡ� DatagramChannel ��ͨ��UDP��д�����е����ݡ� SocketChannel ��ͨ��TCP��д�����е����ݡ�
 * ServerSocketChannel���Լ����½�����TCP���ӣ���Web��������������ÿһ���½��������Ӷ��ᴴ��һ��SocketChannel��
 */
public class ChannelTest {

    public static void main(String[] args) throws Exception {
         readFile();
//         writeFile();
//        transferFrom();
//        transferTo();
    }

    public static void readFile() throws Exception {
        RandomAccessFile readFile = new RandomAccessFile("readdata.txt", "rw");
        RandomAccessFile otherFile = new RandomAccessFile("otherdata.txt", "rw");
        /** ����ļ���д��ͨ�� **/
        FileChannel inChannel = readFile.getChannel();
        FileChannel otherChannel = otherFile.getChannel();
        
        /** ��ȡ�ļ���С **/
        long fileSize = inChannel.size();
        System.out.println("size: " + fileSize);
        
        /**ɾ���ļ� ����10����֧�� **/
        inChannel.truncate(10);
        
        
        
        
        
        /** ����ByteBuffer **/
        ByteBuffer buf = ByteBuffer.allocate(48);
        /** ��ȡ�ļ�����Ϣд�뵽ByteBuffer������д������ݴ�С **/
        int bytesRead = inChannel.read(buf);
        /** ��ѭ���в��϶�ȡ�ļ�����д�뵽ByteBuffer,ֻ�е��ļ������ݶ�ȡ��� bytesRead==-1 ����ѭ�� ÿ������ȡ48 ��ByteBuffer��С���� **/
        while (bytesRead != -1) {
            /** ��ñ����ļ�д�뵽 ByteBuffer �Ĵ�С **/
            System.out.println("Read " + bytesRead);
            /** �л�ByteBuffer ����ģʽ **/
            buf.flip();
            /** ѯ���ж�ByteBuffer���Ƿ��пɶ�ȡ������ ����ȡByteBuffer�������buf.hasRemaining() Ϊfalse **/
            while (buf.hasRemaining()) {
                System.out.println((char) buf.get());
            }
            /** ����buf ������д��ȡ�ļ�д��ByteBuffer **/
            buf.clear();
            bytesRead = inChannel.read(buf);
        }
        readFile.close();
        System.out.println("===================readFile===================");
    }

    public static void writeFile() throws Exception {
        RandomAccessFile readFile = new RandomAccessFile("readdata.txt", "rw");
        RandomAccessFile writeFile = new RandomAccessFile("writedata.txt", "rw");
        /** ����ļ���д��ͨ�� **/
        FileChannel inChannel = readFile.getChannel();
        FileChannel writeChannel = writeFile.getChannel();

        /** ����ByteBuffer **/
        ByteBuffer buf = ByteBuffer.allocate(48);
        /** ��ȡ�ļ�����Ϣд�뵽ByteBuffer������д������ݴ�С **/
        int bytesRead = inChannel.read(buf);
        /** ��ѭ���в��϶�ȡ�ļ�����д�뵽ByteBuffer,ֻ�е��ļ������ݶ�ȡ��� bytesRead==-1 ����ѭ�� ÿ������ȡ48 ��ByteBuffer��С���� **/
        while (bytesRead != -1) {
            /** ��ñ����ļ�д�뵽 ByteBuffer �Ĵ�С **/
            System.out.println("Read " + bytesRead);
            /** �л�ByteBuffer ����ģʽ **/
            buf.flip();
            /** ��ȡbuf���� д�뵽 �ļ��� **/
            writeChannel.write(buf);
            /** ����buf ������д��ȡ�ļ�д��ByteBuffer **/
            buf.clear();
            bytesRead = inChannel.read(buf);
        }
        readFile.close();
        writeFile.close();
        System.out.println("===================writeFile===================");
    }

    /**
     * �������������position��ʾ��position����ʼ��Ŀ���ļ�д�����ݣ�count��ʾ��ഫ����ֽ��������Դͨ����ʣ��ռ�С�� count ���ֽڣ�����������ֽ���ҪС��������ֽ�����
     * ��SoketChannel��ʵ���У�SocketChannelֻ�ᴫ��˿�׼���õ����ݣ����ܲ���count�ֽڣ�����ˣ�SocketChannel���ܲ��Ὣ�������������(count���ֽ�)ȫ�����䵽FileChannel�С�
     * �ļ�����
     * @throws Exception
     */
    public static void transferFrom() throws Exception {
        RandomAccessFile fromFile = new RandomAccessFile("readdata.txt", "rw");
        FileChannel fromChannel = fromFile.getChannel();

        RandomAccessFile toFile = new RandomAccessFile("writedata.txt", "rw");
        FileChannel toChannel = toFile.getChannel();

        long position = 0;
        long count = fromChannel.size();

        fromChannel.transferTo(position, count, toChannel);

        fromFile.close();
        toFile.close();

    }

    /**
     * ���ܺ�transferFrom()һ��
     * @throws Exception
     */
    public static void transferTo() throws Exception {
        RandomAccessFile fromFile = new RandomAccessFile("readdata.txt", "rw");
        FileChannel fromChannel = fromFile.getChannel();

        RandomAccessFile toFile = new RandomAccessFile("writedata.txt", "rw");
        FileChannel toChannel = toFile.getChannel();

        long position = 0;
        long count = fromChannel.size();

        fromChannel.transferTo(position, count, toChannel);
        fromFile.close();
        toFile.close();

    }

}
