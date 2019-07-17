package com.xiaoxiao.nio.channel;

import org.testng.annotations.Test;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

public class TransferMethodTest {

    /**
     * 将源目标通道中指定数据添加到目标通道
     *
     * @param  position
     *         源头通道读取数据位置
     *         如果position> fromChannel.size 相当于没有传输数据
     * @param  count
     *         从position开始读取字节数
     *         一般  count<= fromChannel.size-fromChannel.position
     *         如果  count > fromChannel.size-fromChannel.position?FileChannel.size-FileChannel.position:count
     *
     * @param  target
     *         目标通道
     *
     * @return  来源通道读取字节数
     */
    @Test
    public void test_transferTo() throws Exception {
        RandomAccessFile fromFile = new RandomAccessFile("transfer_read", "rw");
        FileChannel fromChannel = fromFile.getChannel();

        RandomAccessFile toFile = new RandomAccessFile("transfer_write", "rw");
        FileChannel toChannel = toFile.getChannel();


        /** 从目标通道指定位置写入(前提是当前位置由数据才生效)**/
        toChannel.position(2);

        /** 每次传输字节数 **/
        long sendSize = 5;
        long total=0;
        long len = fromChannel.transferTo(0, sendSize, toChannel);
        total+=sendSize;
        while (len!=0){

            System.out.println(fromChannel.position());
            System.out.println(toChannel.position());
            len = fromChannel.transferTo(total, sendSize, toChannel);
            total+=sendSize;
        }


        fromFile.close();
        toFile.close();

    }


    /**
     * 将源目标通道中指定数据添加到目标通道
     *
     * @param  src
     *         来源通道位置
     *
     * @param  position
     *         目标通道写入位置
     *          一般  count<= fromChannel.size-fromChannel.position
     *          如果  count > fromChannel.size-fromChannel.position?FileChannel.size-FileChannel.position:count
     *
     * @param  count
     *         从position开始写入字节数
     *
     * @return  目标通道写入字节数
     */
    @Test
    public void test_transferFrom() throws Exception {
        RandomAccessFile fromFile = new RandomAccessFile("transfer_read", "rw");
        FileChannel fromChannel = fromFile.getChannel();

        RandomAccessFile toFile = new RandomAccessFile("transfer_write", "rw");
        FileChannel toChannel = toFile.getChannel();

        /** 从指定位置读取 **/
        fromChannel.position(2);

        /** 每次传输字节数 **/
        long sendSize = 5;
        long total=0;
        long len = toChannel.transferFrom(fromChannel, 0, sendSize);
        total+=sendSize;
        while (len!=0){
            System.out.println(fromChannel.position());
            System.out.println(toChannel.position());
            len = toChannel.transferFrom(fromChannel, total, sendSize);
            total+=sendSize;
        }
        fromFile.close();
        toFile.close();

    }
}
