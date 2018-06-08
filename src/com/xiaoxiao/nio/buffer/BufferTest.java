package com.xiaoxiao.nio.buffer;

import java.nio.ByteBuffer;

public class BufferTest {

    public static void main(String[] args) throws Exception {
        testrewind();
        testmark();
        testcompact();
    }

    /**
     * capacity&position&limit解析 
     * 
     * capacity 表示buff 容量 下面例子中是10 （读写模式一致） 
     * position 表示起始位置 
     * limit 表示限定位置 
     * flip() 切换模式规则   position=0 limit=position clear() 清理buf变为初始 position=0 limit=容量 capacity=容量
     */
    public static void testBuffer() {
        /** 初始化为 position 0 limit 10 capacity 10 **/
        ByteBuffer bufwrite = ByteBuffer.allocate(10);
        /** 进入写模式(写入模式一) position 4 limit 10 capacity 10 写入4个字节 最大不能超过limit 10 **/
        bufwrite.put("test".getBytes());
        /** 切换读模式 position 0 limit 4 capacity 10 读模式只能读取buf以写入的数据（即test） limit值作为限定是写入模式中position值，position值清0 **/
        bufwrite.flip();
        /** 切换读模式 position 0 limit 0 capacity 10 按照切换模式规则 由于上次没有读取position=0 所以切换后 limit=0 所以也不能再写入 **/
        bufwrite.flip();
        /** 出错 **/
        // bufwrite.put("s".getBytes());

        bufwrite.clear();

        /** 进入写模式(写入模式一) position 4 limit 10 capacity 10 写入4个字节 最大不能超过limit 10 **/
        bufwrite.put("test".getBytes());
        /** 切换读模式 position 0 limit 4 capacity 10 读模式只能读取buf以写入的数据（即test） limit值作为限定是写入模式中position值，position值清0 **/
        bufwrite.flip();
        /** 读取一个字节 position 1 limit 4 capacity 10 **/
        System.out.println(bufwrite.get());
        /** 切换读模式 position 0 limit 1 capacity 10 按照切换模式规则 由于上次没有读取position=0 所以切换后 limit=0 所以也不能再写入 **/
        bufwrite.flip();
        /** 写入一个字节 超过limit字节数报错BufferOverflowException **/
        bufwrite.put("s".getBytes());
    }

    /**
     * Buffer.rewind()将position设回0，所以你可以重读Buffer中的所有数据。limit保持不变，仍然表示能从Buffer中读取多少个元素（byte、char等）。
     */
    public static void testrewind() {

        /** Bufeer rewind()重复读取 **/
        ByteBuffer buf = ByteBuffer.allocate(1000);
        buf.put("A".getBytes());
        buf.put("B".getBytes());
        buf.put("C".getBytes());
        buf.put("D".getBytes());
        buf.put("E".getBytes());
        buf.flip();

        while (buf.hasRemaining()) {
            System.out.println((char) buf.get());
        }
        buf.rewind();
        while (buf.hasRemaining()) {
            System.out.println((char) buf.get());
        }

        buf.clear();
        System.out.println("===================testrewind===================");
    }

    /**
     * 通过调用Buffer.mark()方法，可以标记Buffer中的一个特定position。之后可以通过调用Buffer.reset()方法恢复到这个position
     */
    public static void testmark() {

        /** Bufeer rewind()重复读取 **/
        ByteBuffer buf = ByteBuffer.allocate(1000);
        buf.put("A".getBytes());
        buf.put("B".getBytes());
        buf.put("C".getBytes());
        buf.put("D".getBytes());
        buf.put("E".getBytes());

        buf.flip();
        buf.mark();
        while (buf.hasRemaining()) {
            System.out.println((char) buf.get());
        }
        buf.reset();
        while (buf.hasRemaining()) {
            System.out.println((char) buf.get());
        }

        buf.clear();
        System.out.println("===================testmark===================");
    }

    /**
     * compact()方法将所有未读的数据拷贝到Buffer起始处。然后将position设到最后一个未读元素正后面。limit属性依然像clear()方法一样，设置成capacity。现在Buffer准备好写数据了
     */
    public static void testcompact() {
        /** Bufeer rewind()重复读取 **/
        ByteBuffer buf = ByteBuffer.allocate(1000);
        buf.put("A".getBytes());
        buf.put("B".getBytes());
        buf.put("C".getBytes());
        buf.put("D".getBytes());
        buf.put("E".getBytes());
        
        buf.flip();
        System.out.println((char) buf.get());
        System.out.println((char) buf.get());
        System.out.println(buf);
        buf.compact();
        System.out.println(buf);
        
    }

}
