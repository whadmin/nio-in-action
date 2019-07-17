package com.xiaoxiao.nio.channel;

import org.testng.annotations.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.stream.IntStream;

public class ReadAndWriteMethodTest {

    /**
     * write(ByteBuffer buffer) 从给定缓冲区向此通道写入一个字节序列。（同步）
     */
    @Test
    public void test_write() throws Exception {
        RandomAccessFile readFile = new RandomAccessFile("write1.txt", "rw");
        FileChannel fileChannel = readFile.getChannel();
        /** 设置文件写入位置 **/
        fileChannel.position(0);
        try{
            /** 写入文件字节缓冲区数据 **/
            ByteBuffer buffer=ByteBuffer.wrap("abcd".getBytes());
            buffer.position(1);
            buffer.limit(2);
            /** 写入文件之前的fileChannel.position()位置 **/
            System.out.println ("fileChannel.position= " + fileChannel.position()) ;
            /** 将缓冲区中position->limist 数据写入到文件设置position位置 **/
            System.out.println ("write() size= " + fileChannel.write(buffer)) ;
            /** 写入文件之后的fileChannel.position()位置 **/
            System.out.println ("fileChannel.position= " + fileChannel.position()) ;
            System.out.println();
            /** 重置缓冲区 **/
            buffer.clear();
            System.out.println ("A fileChannel.position ()= " + fileChannel.position()) ;
            /** 将缓冲区中position->limist 数据写入到文件设置position位置 **/
            System.out.println ("write() size= " + fileChannel.write(buffer)) ;
            System.out.println ("A fileChannel.position ()= " + fileChannel.position()) ;
        }finally {
            readFile.close();
            fileChannel.close();
        }
    }

    /**
     * 测试write(ByteBuffer buffer)多线程同步
     */
    @Test
    public void test_write2() throws Exception {
        RandomAccessFile readFile = new RandomAccessFile("write2.txt", "rw");
        FileChannel fileChannel = readFile.getChannel();
        try{
            IntStream.range(0,10).forEach(i->{
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ByteBuffer buffer=ByteBuffer.wrap("abcd\r\n".getBytes());
                            System.out.println ("write() size= " + fileChannel.write(buffer)) ;
                        } catch (IOException e) {
                        }
                    }
                });
                thread.start();
                Thread thread2 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ByteBuffer buffer=ByteBuffer.wrap("1234\r\n".getBytes());
                            System.out.println ("write() size= " + fileChannel.write(buffer)) ;
                        } catch (IOException e) {
                        }
                    }
                });
                thread2.start();
            });
        }finally {

        }
        Thread.sleep(3000);
        readFile.close();
        fileChannel.close();
    }


    /**
     * write(ByteBuffer buffer,position) 从给定缓冲区向此通道指定位置写入一个字节序列。（同步）
     */
    @Test
    public void test_write3() throws Exception {
        RandomAccessFile readFile = new RandomAccessFile("write7.txt", "rw");
        RandomAccessFile writeFile = new RandomAccessFile("write7.txt", "rw");
        FileChannel fileChannel = readFile.getChannel();
        FileChannel fileChannel2 = writeFile.getChannel();
        try{
            /** 写入文件字节缓冲区数据 **/
            ByteBuffer buffer=ByteBuffer.wrap("--abcd".getBytes());
            buffer.position(2);
            /** 将缓冲区中position->limist 数据写入到文件设置position位置 **/
            System.out.println ("write() size= " + fileChannel.write(buffer,0)) ;
            System.out.println();
            /** 重置缓冲区 **/
            buffer.position(2);
            /** 将缓冲区中position->limist 数据写入到文件设置position位置 **/
            System.out.println ("write() size= " + fileChannel.write(buffer,4)) ;

            ByteBuffer allocate = ByteBuffer.allocate(8);
            fileChannel2.read(allocate);
            System.out.println(new String(allocate.array()));

        }finally {
            readFile.close();
            fileChannel.close();
        }
    }

    /**
     * write(ByteBuffer buffer,position) 多线程同步
     */
    @Test
    public void test_write4() throws Exception {
        RandomAccessFile readFile = new RandomAccessFile("write8.txt", "rw");
        FileChannel fileChannel = readFile.getChannel();
        try{
            IntStream.range(0,10).forEach(i->{
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ByteBuffer buffer=ByteBuffer.wrap("abcd".getBytes());
                            System.out.println(fileChannel.position());
                            System.out.println ("write() size= " + fileChannel.write(buffer,fileChannel.position())) ;
                        } catch (IOException e) {
                        }
                    }
                });
                thread.start();
                Thread thread2 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ByteBuffer buffer=ByteBuffer.wrap("1234".getBytes());
                            System.out.println(fileChannel.position());
                            System.out.println ("write() size= " + fileChannel.write(buffer,fileChannel.position())) ;
                        } catch (IOException e) {
                        }
                    }
                });
                thread2.start();
            });
        }finally {

        }
        Thread.sleep(3000);
        readFile.close();
        fileChannel.close();
    }




    /**
     * read(ByteBuffer buffer)将一系列字节从这个通道读取到给定的缓冲区中。（同步）
     */
    @Test
    public void test_read() throws Exception {
        RandomAccessFile readFile = new RandomAccessFile("write1.txt", "rw");
        FileChannel fileChannel = readFile.getChannel();
        /** 从文件指定位置读取 **/
        fileChannel.position(1);
        try{
            ByteBuffer buffer=ByteBuffer.allocate(5);
            /** 从缓冲区指定位置写入 **/
            buffer.position(1);
            /** 从缓冲区写入限制位置 **/
            buffer.limit(5);
            /** 从通道position位置开始读取buffer.remaining大小字节，从缓冲区position位置开始写入 **/
            System.out.println ("A fileChannel.readLength= " + fileChannel.read(buffer)) ;
            System.out.println (buffer) ;
            System.out.println (new String(buffer.array())) ;
            buffer.clear();
            System.out.println ("A fileChannel.readLength= " + fileChannel.read(buffer)) ;
        }finally {
            readFile.close();
            fileChannel.close();
        }
    }

    /**
     * 测试read(ByteBuffer buffer)多线程同步
     *
     * aaaalaaaa2aaaa3aaaa4aaaa5aaaa6aaaa7aaaa8aaaa9bbbblbbbb2bbbb3bbbb4bbbb5bbbb6bbb
     * b7bbbb8bbbb9cccclcccc2cccc3cccc4cccc5cccc6cccc7cccc8cccc9
     */
    @Test
    public void test_read2() throws Exception {
        RandomAccessFile readFile = new RandomAccessFile("read.txt", "rw");
        FileChannel fileChannel = readFile.getChannel();
        try{
            IntStream.range(0,3).forEach(i->{
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ByteBuffer buffer=ByteBuffer.allocate(5);
                            int readLength = fileChannel.read(buffer);
                            while (readLength!=-1){
                                System.out.println(new String(buffer.array(),0,readLength));
                                buffer.clear();
                                readLength = fileChannel.read(buffer);
                            }
                        } catch (IOException e) {
                        }
                    }
                });
                thread.start();
            });
        }finally {

        }
        Thread.sleep(3000);
        readFile.close();
        fileChannel.close();
    }


    /**
     * read(ByteBuffer buffer,Long position)将一系列字节从这个通道某个位置开始读取到给定的缓冲区中。（同步）
     */
    @Test
    public void test_read3() throws Exception {
        RandomAccessFile readFile = new RandomAccessFile("write7.txt", "rw");
        FileChannel fileChannel = readFile.getChannel();
        try{
            ByteBuffer buffer=ByteBuffer.allocate(5);
            buffer.position(1);
            buffer.limit(5);
            /** 从通道position位置开始读取buffer.remaining大小字节，从缓冲区position位置开始写入 **/
            System.out.println ("A fileChannel.readLength= " + fileChannel.read(buffer,4)) ;
            System.out.println (buffer) ;
            System.out.println (new String(buffer.array())) ;

        }finally {
            readFile.close();
            fileChannel.close();
        }
    }



    /**
     * 从给定缓冲区数组向此通道写入一个字节序列。
     */
    @Test
    public void test_batchwrite() throws Exception {
        RandomAccessFile readFile = new RandomAccessFile("write3.txt", "rw");
        FileChannel fileChannel = readFile.getChannel();
        RandomAccessFile writeFile = new RandomAccessFile("write3.txt", "rw");
        FileChannel fileChannel2 = writeFile.getChannel();
        /** 从文件指定位置写入 **/
        fileChannel.position(0);
        try{
            ByteBuffer buffer1=ByteBuffer.wrap("000001".getBytes());
            ByteBuffer buffer2=ByteBuffer.wrap("00002".getBytes());
            buffer1.position(2);
            buffer1.limit(6);
            buffer2.position(2);
            buffer2.limit(5);
            /** 将缓冲区数组中 每个缓冲区中position->limist 数据写入到文件设置position位置 **/
            ByteBuffer[] buffers=new ByteBuffer[]{buffer1,buffer2};
            System.out.println ("A fileChannel.position ()= " + fileChannel.position());
            Long writeLen = fileChannel.write(buffers);
            System.out.println ("A fileChannel.position ()= " + fileChannel.position());

            ByteBuffer allocate = ByteBuffer.allocate(writeLen.intValue());
            fileChannel2.read(allocate);
            System.out.println(new String(allocate.array()));
        }finally {
            readFile.close();
            fileChannel.close();
        }
    }

    /**
     * 测试write(ByteBuffer[] srcs)多线程同步
     */
    @Test
    public void test_batchwrite2() throws Exception {
        RandomAccessFile readFile = new RandomAccessFile("write4.txt", "rw");
        FileChannel fileChannel = readFile.getChannel();
        fileChannel.position(0);
        try{
            IntStream.range(0,10).forEach(i->{
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ByteBuffer buffer1=ByteBuffer.wrap("00001\r\n".getBytes());
                            ByteBuffer buffer2=ByteBuffer.wrap("00002\r\n".getBytes());
                            buffer1.position(2);
                            buffer2.position(2);
                            ByteBuffer[] buffers=new ByteBuffer[]{buffer1,buffer2};
                            System.out.println ("write() size= " + fileChannel.write(buffers)) ;
                        } catch (IOException e) {
                        }
                    }
                });
                thread.start();
                Thread thread2 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ByteBuffer buffer1=ByteBuffer.wrap("0000a\r\n".getBytes());
                            ByteBuffer buffer2=ByteBuffer.wrap("0000b\r\n".getBytes());
                            buffer1.position(2);
                            buffer2.position(2);
                            ByteBuffer[] buffers=new ByteBuffer[]{buffer1,buffer2};
                            System.out.println ("write() size= " + fileChannel.write(buffers)) ;
                        } catch (IOException e) {
                        }
                    }
                });
                thread2.start();
            });
        }finally {

        }
        Thread.sleep(3000);
        readFile.close();
        fileChannel.close();
    }


    /**
     * 从给定缓冲区数组的子序列向此通道写入字节序列。（多线程同步）
     * @param  srcs
     *         写入通道字节缓冲区数组
     * @param  offset
     *         第一个写入通道的缓冲区，在数组的位置
     * @param  length
     *         从offset开始数组中多少个缓冲区写入通道
     * @return  写入的字节数
     */
    @Test
    public void test_batchwrite3() throws Exception {

        RandomAccessFile readFile = new RandomAccessFile("write5.txt", "rw");
        FileChannel fileChannel = readFile.getChannel();
        RandomAccessFile writeFile = new RandomAccessFile("write5.txt", "rw");
        FileChannel fileChannel2 = writeFile.getChannel();
        /** 从文件指定位置写入 **/
        fileChannel.position(0);
        try{
            ByteBuffer buffer1=ByteBuffer.wrap("abcd".getBytes());
            ByteBuffer buffer2=ByteBuffer.wrap("-1234x".getBytes());
            buffer2.position(1);
            buffer2.limit(5);
            ByteBuffer buffer3=ByteBuffer.wrap("5678".getBytes());
            ByteBuffer buffer4=ByteBuffer.wrap("efch".getBytes());
            /** 将缓冲区数组中 每个缓冲区中position->limist 数据写入到文件设置position位置 **/
            ByteBuffer[] buffers=new ByteBuffer[]{buffer1,buffer2,buffer3,buffer4};
            System.out.println ("A fileChannel.position ()= " + fileChannel.position());
            Long writeLen = fileChannel.write(buffers,1,3);
            System.out.println ("A fileChannel.position ()= " + fileChannel.position());

            ByteBuffer allocate = ByteBuffer.allocate(writeLen.intValue());
            fileChannel2.read(allocate);
            System.out.println(new String(allocate.array()));
        }finally {
            readFile.close();
            fileChannel.close();
        }
    }


    public static ByteBuffer[] getByteBufferArray(String str1,String str2){
        ByteBuffer buffer1=ByteBuffer.wrap(str1.getBytes());
        ByteBuffer buffer2=ByteBuffer.wrap(str2.getBytes());
        ByteBuffer[] buffers=new ByteBuffer[]{buffer1,buffer2};
        return  buffers;
    }

    @Test
    public void test_batchwrite4() throws Exception {
        RandomAccessFile readFile = new RandomAccessFile("write6.txt", "rw");
        FileChannel fileChannel = readFile.getChannel();
        fileChannel.position(0);
        try{
            IntStream.range(0,10).forEach(i->{
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ByteBuffer[] buffers=getByteBufferArray("aaaa","bbbb");
                            System.out.println ("write() size= " + fileChannel.write(buffers,0,2)) ;
                        } catch (IOException e) {
                        }
                    }
                });
                thread.start();
                Thread thread2 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ByteBuffer[] buffers=getByteBufferArray("xxxx","yyyy");
                            System.out.println ("write() size= " + fileChannel.write(buffers,0,2)) ;
                        } catch (IOException e) {
                        }
                    }
                });
                thread2.start();
            });
        }finally {

        }
        Thread.sleep(3000);
        readFile.close();
        fileChannel.close();
    }


    /**c
     * 将此通道中的字节序列读取到给定缓冲区数组中。
     */
    @Test
    public void test_batchread() throws Exception {
        RandomAccessFile readFile = new RandomAccessFile("write3.txt", "rw");
        FileChannel fileChannel = readFile.getChannel();
        /** 从文件指定位置读取 **/
        fileChannel.position(0);
        try{
            ByteBuffer buffer1=ByteBuffer.allocate(5);
            ByteBuffer buffer2=ByteBuffer.allocate(5);
            /** 从缓冲区指定位置写入 **/
            buffer1.position(1);
            /** 从缓冲区指定位置写入 **/
            buffer2.position(2);
            ByteBuffer[] buffers=new ByteBuffer[]{buffer1,buffer2};

            System.out.println ("A fileChannel.read= " +  fileChannel.read(buffers));
            System.out.println(buffer1);
            System.out.println(new String(buffer1.array()));
            System.out.println(buffer2);
            System.out.println(new String(buffer2.array()));
            buffer1.clear();
            buffer2.clear();

            System.out.println ("A fileChannel.read= " +  fileChannel.read(buffers));

        }finally {
            readFile.close();
            fileChannel.close();
        }
    }


    /**
     * 测试read(ByteBuffer[] srcs)多线程同步
     *
     * 001/r/n
     * 002/r/n
     *
     * 一行5个字节
     */
    @Test
    public void test_batchread2() throws Exception {
        RandomAccessFile readFile = new RandomAccessFile("write4.txt", "rw");
        FileChannel fileChannel = readFile.getChannel();
        try{
            IntStream.range(0,5).forEach(i->{
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ByteBuffer buffer1=ByteBuffer.allocate(5);
                            ByteBuffer buffer2=ByteBuffer.allocate(5);
                            ByteBuffer[] buffers=new ByteBuffer[]{buffer1,buffer2};
                            long readLength = fileChannel.read(buffers);
                            while (readLength!=-1){
                                synchronized (ReadAndWriteMethodTest.class ){
                                    for (int i = 0; i < buffers.length; i++) {
                                        System.out.println(new String(buffers[i].array()));
                                    }

                                }
                                buffer1.clear();
                                buffer2.clear();
                                readLength = fileChannel.read(buffers);
                            }
                        } catch (IOException e) {
                        }
                    }
                });
                thread.start();
            });
        }finally {
        }
        Thread.sleep(3000);
        readFile.close();
        fileChannel.close();
    }


    /**
     * 从给定缓冲区数组的子序列向此通道写入字节序列。
     *
     * @param  srcs
     *         写入通道字节缓冲区数组
     *
     * @param  offset
     *         第一个写入通道的缓冲区，在数组的位置
     *
     * @param  length
     *         从offset开始数组中多少个缓冲区写入通道
     *
     * @return  写入的字节数
     *
     * 文件内容  12345678efch
     */
    @Test
    public void test_batchread3() throws Exception {
        RandomAccessFile readFile = new RandomAccessFile("write5.txt", "rw");
        FileChannel fileChannel = readFile.getChannel();
        /** 从文件指定位置读取 **/
        fileChannel.position(0);
        try{
            ByteBuffer buffer0=ByteBuffer.allocate(5);
            ByteBuffer buffer1=ByteBuffer.allocate(5);
            ByteBuffer buffer2=ByteBuffer.allocate(5);
            ByteBuffer buffer3=ByteBuffer.allocate(5);
            /** 从缓冲区指定位置写入 **/
            buffer1.position(1);
            /** 从缓冲区指定位置写入 **/
            buffer2.position(1);
            /** 从缓冲区指定位置写入 **/
            buffer3.position(2);
            ByteBuffer[] buffers=new ByteBuffer[]{buffer0,buffer1,buffer2,buffer3};

            System.out.println ("A fileChannel.read= " +  fileChannel.read(buffers,1,3));
            System.out.println(buffer1);
            System.out.println(new String(buffer1.array()));
            System.out.println(buffer2);
            System.out.println(new String(buffer2.array()));
            System.out.println(buffer3);
            System.out.println(new String(buffer3.array()));
            buffer1.clear();
            buffer2.clear();
            buffer3.clear();

            System.out.println ("A fileChannel.read= " +  fileChannel.read(buffers,1,3));
            System.out.println(buffer1);
            System.out.println(new String(buffer1.array()));

        }finally {
            readFile.close();
            fileChannel.close();
        }
    }


    @Test
    public void test_batchread4() throws Exception {
        RandomAccessFile readFile = new RandomAccessFile("write6.txt", "rw");
        FileChannel fileChannel = readFile.getChannel();
        try{
            IntStream.range(0,5).forEach(i->{
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ByteBuffer buffer1=ByteBuffer.allocate(4);
                            ByteBuffer buffer2=ByteBuffer.allocate(4);
                            ByteBuffer[] buffers=new ByteBuffer[]{buffer1,buffer2};
                            long readLength = fileChannel.read(buffers,0,2);
                            while (readLength!=-1){
                                synchronized (ReadAndWriteMethodTest.class ){
                                    for (int i = 0; i < buffers.length; i++) {
                                        System.out.println(new String(buffers[i].array()));
                                    }

                                }
                                buffer1.clear();
                                buffer2.clear();
                                readLength = fileChannel.read(buffers);
                            }
                        } catch (IOException e) {
                        }
                    }
                });
                thread.start();
            });
        }finally {
        }
        Thread.sleep(3000);
        readFile.close();
        fileChannel.close();
    }

    /**
     * position 表示写入读取通道起始位置，write(ByteBuffer buffer)，read(ByteBuffer buffer)
     * 操作时position会移动
     * size 表示写入通道总数据
     */
    @Test
    public void test_position() throws Exception {
        RandomAccessFile readFile = new RandomAccessFile("write9.txt", "rw");
        RandomAccessFile writeFile = new RandomAccessFile("write9.txt", "rw");
        FileChannel fileChannel = readFile.getChannel();
        FileChannel fileChannel2 = writeFile.getChannel();
        try{
            /** 写入文件字节缓冲区数据 **/
            ByteBuffer buffer=ByteBuffer.wrap("--abcd".getBytes());
            buffer.position(2);
            /** 将缓冲区中position->limist 数据写入到文件设置position位置 **/
            System.out.println ("write() size= " + fileChannel.write(buffer)) ;
            System.out.println(fileChannel.position());
            System.out.println(fileChannel.size());
            /** 重置缓冲区 **/
            buffer.position(2);
            /** 将缓冲区中position->limist 数据写入到文件设置position位置 **/
            System.out.println ("write() size= " + fileChannel.write(buffer)) ;
            System.out.println(fileChannel.position());
            System.out.println(fileChannel.size());

            ByteBuffer allocate = ByteBuffer.allocate(8);
            fileChannel2.read(allocate);
            System.out.println(new String(allocate.array()));

        }finally {
            readFile.close();
            fileChannel.close();
        }
    }



    /**
     * position 表示写入读取通道起始位置，write(ByteBuffer buffer, long position)，read(ByteBuffer buffer, long position)
     * 操作时position不会移动
     * size 表示写入通道总数据
     */
    @Test
    public void test_position2() throws Exception {
        RandomAccessFile readFile = new RandomAccessFile("write10.txt", "rw");
        RandomAccessFile writeFile = new RandomAccessFile("write10.txt", "rw");
        FileChannel fileChannel = readFile.getChannel();
        FileChannel fileChannel2 = writeFile.getChannel();
        try{
            /** 写入文件字节缓冲区数据 **/
            ByteBuffer buffer=ByteBuffer.wrap("--abcd".getBytes());
            buffer.position(2);
            /** 将缓冲区中position->limist 数据写入到文件设置position位置 **/
            System.out.println ("write() size= " + fileChannel.write(buffer,fileChannel.position())) ;
            System.out.println(fileChannel.position());
            System.out.println(fileChannel.size());
            /** 重置缓冲区 **/
            buffer.position(2);
            /** 将缓冲区中position->limist 数据写入到文件设置position位置 **/
            System.out.println ("write() size= " + fileChannel.write(buffer,fileChannel.position())) ;
            System.out.println(fileChannel.position());
            System.out.println(fileChannel.size());

            ByteBuffer allocate = ByteBuffer.allocate(8);
            fileChannel2.read(allocate);
            System.out.println(new String(allocate.array()));

        }finally {
            readFile.close();
            fileChannel.close();
        }
    }

    @Test
    public void test_force() throws Exception {
        RandomAccessFile writeFile = new RandomAccessFile("write12.txt", "rw");
        FileChannel fileChannel = writeFile.getChannel();
        try{
            /** 写入文件字节缓冲区数据 **/
            ByteBuffer buffer=ByteBuffer.wrap("1111111111".getBytes());
            long begin = System.currentTimeMillis();
            for (int i=0;i<=5000;i++){
                fileChannel.write(buffer);
                fileChannel.force(true);
                buffer.clear();
            }
            long end = System.currentTimeMillis();
            System.out.println(end-begin);


            ByteBuffer buffer1=ByteBuffer.wrap("22222222222".getBytes());
            begin = System.currentTimeMillis();
            for (int i=0;i<=5000;i++){
                fileChannel.write(buffer1);
                buffer1.clear();
            }
            end = System.currentTimeMillis();
            System.out.println(end-begin);


        }finally {
            writeFile.close();
            fileChannel.close();
        }
    }
}
