package com.xiaoxiao.nio.buffer;

import org.testng.annotations.Test;

import java.nio.*;

public class BufferTest {




    @Test
    public void testBuffer() {
        //申请一块内存
        ByteBuffer allocate = ByteBuffer.allocate(10);
        System.out.println(allocate);
        //写入数据
        allocate.put((byte) 1);
        allocate.put((byte) 2);
        System.out.println(allocate);
        //切换读模式
        allocate.flip();
        //读取数据
        System.out.println(allocate);
        System.out.println(allocate.get());
        System.out.println(allocate.get());
    }

    @Test
    public void testBuffer_wrap() {

        byte[] byteArray = new byte[]{1, 2, 3};
        short[] shortArray = new short[]{1, 2, 3, 4};
        int[] intArray = new int[]{1, 2, 3, 4, 5};
        long[] longArray = new long[]{1, 2, 3, 4, 5, 6};
        float[] floatArray = new float[]{1, 2, 3, 4, 5, 6, 7};
        double[] doubleArray = new double[]{1, 2, 3, 4, 5, 6, 7, 8};
        char[] charArray = new char[]{'a', 'b', 'c', 'd'};

        /** *Buffer.wrap提供了Heap*Buffer的工厂方法  **/
        ByteBuffer byteBuffer = ByteBuffer.wrap(byteArray);
        ByteBuffer byteBuffer2 = ByteBuffer.wrap(byteArray,1,2);
        ShortBuffer shortBuffer = ShortBuffer.wrap(shortArray);
        IntBuffer intBuffer = IntBuffer.wrap(intArray);
        LongBuffer longBuffer = LongBuffer.wrap(longArray);
        FloatBuffer floatBuffer = FloatBuffer.wrap(floatArray);
        DoubleBuffer doubleBuffer = DoubleBuffer.wrap(doubleArray);
        CharBuffer charBuffer = CharBuffer.wrap(charArray);

        System.out.println("byteBuffer=" + byteBuffer.getClass().getName());
        System.out.println("shortBuffer=" + shortBuffer.getClass().getName());
        System.out.println("intBuffer=" + intBuffer.getClass().getName());
        System.out.println("longBuffer=" + longBuffer.getClass().getName());
        System.out.println("floatBuffer=" + floatBuffer.getClass().getName());
        System.out.println("doubleBuffer=" + doubleBuffer.getClass().getName());
        System.out.println("charBuffer=" + charBuffer.getClass().getName());

        System.out.println();

        System.out.println("byteBuffer.=" + byteBuffer);
        System.out.println("byteBuffer2.=" + byteBuffer2);
        System.out.println("shortBuffer.=" + shortBuffer);
        System.out.println("intBuffer.=" + intBuffer);
        System.out.println("longBuffer.=" + longBuffer);
        System.out.println("floatBuffer.=" + floatBuffer);
        System.out.println("doubleBuffer.=" + doubleBuffer);
        System.out.println("charBuffer.=" + charBuffer);
    }

    @Test
    public void testBuffer_limit() {
        ByteBuffer buffer = ByteBuffer.allocate(6);
        System.out.println(buffer);
        buffer.limit(3);
        System.out.println();
        System.out.println(buffer);
        buffer.put((byte) 1);
        buffer.put((byte) 2);
        buffer.put((byte) 3);
        buffer.put((byte) 4);  //BufferOverflowException


        byte[] byteArray = new byte[]{1,2,3,4,5,6};
        ByteBuffer buffer2 = ByteBuffer.wrap(byteArray);
        System.out.println(buffer2);
        buffer2.limit(3);
        System.out.println();
        System.out.println(buffer2);
        System.out.println(buffer2.get());
        System.out.println(buffer2.get());
        System.out.println(buffer2.get());
        System.out.println(buffer2.get()); //BufferUnderflowException
    }

    @Test
    public void testBuffer_position() {
        /** 写入 **/
        ByteBuffer buffer = ByteBuffer.allocate(6);
        buffer.put((byte) 1);
        buffer.put((byte) 2);
        System.out.println(buffer);
        buffer.position(0);
        System.out.println(buffer);
        buffer.put((byte) 3);
        buffer.put((byte) 4);  //BufferOverflowException
        for (int i = 0; i < buffer.array().length; i++) {
            System.out.println(buffer.array()[i] + " ");
        }
        System.out.println("-------------------------");
        /** 读取 **/
        byte[] byteArray = new byte[]{1,2,3,4,5,6};
        ByteBuffer buffer2 = ByteBuffer.wrap(byteArray);
        System.out.println(buffer2.get());
        System.out.println(buffer2.get());
        System.out.println(buffer2);
        buffer.position(0);
        System.out.println(buffer2);
        System.out.println(buffer2.get());
        System.out.println(buffer2.get());
    }


    @Test
    public void testBuffer_remaining() {
        /** 写入 **/
        ByteBuffer buffer = ByteBuffer.allocate(6);
        buffer.put((byte) 1);
        System.out.println(buffer);
        System.out.println(buffer.remaining());
        buffer.put((byte) 2);
        System.out.println(buffer);
        System.out.println(buffer.remaining());

        System.out.println("-------------------------");
        /** 读取 **/
        byte[] byteArray = new byte[]{1,2,3,4,5,6};
        ByteBuffer buffer2 = ByteBuffer.wrap(byteArray);
        buffer2.get();
        System.out.println(buffer2);
        System.out.println(buffer2.remaining());
        buffer2.get();
        System.out.println(buffer2);
        System.out.println(buffer2.remaining());
    }


    @Test
    public void testBuffer_mark() {
        /** 写入 **/
        ByteBuffer buffer = ByteBuffer.allocate(6);
        buffer.mark();
        buffer.put((byte) 1);
        buffer.put((byte) 2);
        System.out.println(buffer);
        buffer.reset();
        buffer.put((byte) 3);
        buffer.put((byte) 4);
        for (int i = 0; i < buffer.array().length; i++) {
            System.out.println(buffer.array()[i] + " ");
        }

        System.out.println("-------------------------");
        /** 读取 **/
        byte[] byteArray = new byte[]{1,2,3,4,5,6};
        ByteBuffer buffer2 = ByteBuffer.wrap(byteArray);
        buffer2.mark();
        System.out.println(buffer2.get());
        System.out.println(buffer2.get());
        System.out.println(buffer2);
        buffer2.reset();
        System.out.println(buffer2);
        System.out.println(buffer2.get());
        System.out.println(buffer2.get());
    }

    @Test
    public void testBuffer_isRead() {
        byte[] byteArray = new byte[]{1, 2, 3};
        short[] shortArray = new short[]{1, 2, 3, 4};
        int[] intArray = new int[]{1, 2, 3, 4, 5};
        long[] longArray = new long[]{1, 2, 3, 4, 5, 6};
        float[] floatArray = new float[]{1, 2, 3, 4, 5, 6, 7};
        double[] doubleArray = new double[]{1, 2, 3, 4, 5, 6, 7, 8};
        char[] charArray = new char[]{'a', 'b', 'c', 'd'};

        /** *Buffer.wrap提供了Heap*Buffer的工厂方法  **/
        ByteBuffer byteBuffer = ByteBuffer.wrap(byteArray);
        ShortBuffer shortBuffer = ShortBuffer.wrap(shortArray);
        IntBuffer intBuffer = IntBuffer.wrap(intArray);
        LongBuffer longBuffer = LongBuffer.wrap(longArray);
        FloatBuffer floatBuffer = FloatBuffer.wrap(floatArray);
        DoubleBuffer doubleBuffer = DoubleBuffer.wrap(doubleArray);
        CharBuffer charBuffer = CharBuffer.wrap(charArray);

        System.out.println("byteBuffer=" + byteBuffer.isReadOnly());
        System.out.println("byteBuffer=" + shortBuffer.isReadOnly());
        System.out.println("byteBuffer=" + intBuffer.isReadOnly());
        System.out.println("byteBuffer=" + longBuffer.isReadOnly());
        System.out.println("byteBuffer=" + floatBuffer.isReadOnly());
        System.out.println("byteBuffer=" + doubleBuffer.isReadOnly());
        System.out.println("byteBuffer=" + charBuffer.isReadOnly());
    }

    @Test
    public void testBuffer_isDirect() {
        System.out.println(ByteBuffer.allocate(10).isDirect());
        System.out.println(ByteBuffer.allocateDirect(10).isDirect());
    }



    @Test
    public void testBuffer_clear() {
        /** 读取 **/
        byte[] byteArray = new byte[]{1,2,3,4,5,6};
        ByteBuffer buffer2 = ByteBuffer.wrap(byteArray);
        buffer2.limit(3);
        System.out.println(buffer2);
        System.out.println(buffer2.get());
        buffer2.clear();
        System.out.println(buffer2);
        System.out.println(buffer2.get());
    }



    @Test
    public void testBuffer_hasArray(){
        /** 读取 **/
        byte[] byteArray = new byte[]{1,2,3,4,5,6};
        ByteBuffer buffer2 = ByteBuffer.wrap(byteArray);
        System.out.println(buffer2.hasArray());
    }

    @Test
    public void testBuffer_rewind(){
        /** 读取 **/
        byte[] byteArray = new byte[]{1,2,3,4,5,6};
        ByteBuffer buffer2 = ByteBuffer.wrap(byteArray);
        buffer2.limit(3);
        System.out.println(buffer2);
        System.out.println(buffer2.get());
        buffer2.rewind();
        System.out.println(buffer2);
        System.out.println(buffer2.get());
    }
}
