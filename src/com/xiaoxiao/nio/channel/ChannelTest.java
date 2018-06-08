package com.xiaoxiao.nio.channel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * http://ifeve.com/channels/
 * 
 * @author xu.jianguo Java NIO的通道类似流，但又有些不同： 既可以从通道中读取数据，又可以写数据到通道。但流的读写通常是单向的。 通道可以异步地读写。
 * 通道中的数据总是要先读到一个Buffer，或者总是要从一个Buffer中写入。 Channel的实现 这些是Java NIO中最重要的通道的实现： FileChannel DatagramChannel SocketChannel
 * ServerSocketChannel FileChannel 从文件中读写数据。 DatagramChannel 能通过UDP读写网络中的数据。 SocketChannel 能通过TCP读写网络中的数据。
 * ServerSocketChannel可以监听新进来的TCP连接，像Web服务器那样。对每一个新进来的连接都会创建一个SocketChannel。
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
        /** 获得文件读写的通道 **/
        FileChannel inChannel = readFile.getChannel();
        FileChannel otherChannel = otherFile.getChannel();
        
        /** 获取文件大小 **/
        long fileSize = inChannel.size();
        System.out.println("size: " + fileSize);
        
        /**删除文件 大于10个的支付 **/
        inChannel.truncate(10);
        
        
        
        
        
        /** 创建ByteBuffer **/
        ByteBuffer buf = ByteBuffer.allocate(48);
        /** 读取文件中信息写入到ByteBuffer，返回写入的数据大小 **/
        int bytesRead = inChannel.read(buf);
        /** 在循环中不断读取文件数据写入到ByteBuffer,只有当文件的数据读取完毕 bytesRead==-1 跳出循环 每次最大读取48 由ByteBuffer大小决定 **/
        while (bytesRead != -1) {
            /** 获得本次文件写入到 ByteBuffer 的大小 **/
            System.out.println("Read " + bytesRead);
            /** 切换ByteBuffer 到读模式 **/
            buf.flip();
            /** 询问判断ByteBuffer中是否有可读取的数据 当读取ByteBuffer数据完毕buf.hasRemaining() 为false **/
            while (buf.hasRemaining()) {
                System.out.println((char) buf.get());
            }
            /** 清理buf 方便重写读取文件写入ByteBuffer **/
            buf.clear();
            bytesRead = inChannel.read(buf);
        }
        readFile.close();
        System.out.println("===================readFile===================");
    }

    public static void writeFile() throws Exception {
        RandomAccessFile readFile = new RandomAccessFile("readdata.txt", "rw");
        RandomAccessFile writeFile = new RandomAccessFile("writedata.txt", "rw");
        /** 获得文件读写的通道 **/
        FileChannel inChannel = readFile.getChannel();
        FileChannel writeChannel = writeFile.getChannel();

        /** 创建ByteBuffer **/
        ByteBuffer buf = ByteBuffer.allocate(48);
        /** 读取文件中信息写入到ByteBuffer，返回写入的数据大小 **/
        int bytesRead = inChannel.read(buf);
        /** 在循环中不断读取文件数据写入到ByteBuffer,只有当文件的数据读取完毕 bytesRead==-1 跳出循环 每次最大读取48 由ByteBuffer大小决定 **/
        while (bytesRead != -1) {
            /** 获得本次文件写入到 ByteBuffer 的大小 **/
            System.out.println("Read " + bytesRead);
            /** 切换ByteBuffer 到读模式 **/
            buf.flip();
            /** 读取buf数据 写入到 文件中 **/
            writeChannel.write(buf);
            /** 清理buf 方便重写读取文件写入ByteBuffer **/
            buf.clear();
            bytesRead = inChannel.read(buf);
        }
        readFile.close();
        writeFile.close();
        System.out.println("===================writeFile===================");
    }

    /**
     * 方法的输入参数position表示从position处开始向目标文件写入数据，count表示最多传输的字节数。如果源通道的剩余空间小于 count 个字节，则所传输的字节数要小于请求的字节数。
     * 在SoketChannel的实现中，SocketChannel只会传输此刻准备好的数据（可能不足count字节）。因此，SocketChannel可能不会将请求的所有数据(count个字节)全部传输到FileChannel中。
     * 文件复制
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
     * 功能和transferFrom()一样
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
