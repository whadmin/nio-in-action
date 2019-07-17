package com.xiaoxiao.nio.buffer;

import org.testng.annotations.Test;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class ByteBufferTest {

    @Test
    public void testBufferPut_Get1() {
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
    public void testBufferPut_Get2() {
        //申请一块内存
        ByteBuffer allocate = ByteBuffer.allocate(10);
        System.out.println(allocate);
        byte[] srcArray = new byte[]{1, 2, 3};
        byte[] destArray = new byte[3];
        //写入数据
        allocate.put(srcArray,0,srcArray.length);
        System.out.println(allocate);
        //切换读模式
        allocate.flip();
        //读取数据
        System.out.println(allocate.get(destArray,0,srcArray.length));
        System.out.println(destArray.toString());
    }

    @Test
    public void testBufferPut_Get3() {
        /** 写入 **/
        ByteBuffer buffer = ByteBuffer.allocate(6);
        System.out.println(buffer);
        buffer.put(1, (byte) 1);
        buffer.put(2, (byte) 2);
        buffer.put(1, (byte) 3);
        buffer.put(2, (byte) 4);
        System.out.println(buffer);
        for (int i = 0; i < buffer.array().length; i++) {
            System.out.println(buffer.array()[i] + " ");
        }
        System.out.println("-------------------------");
        /** 读取 **/
        byte[] byteArray = new byte[]{1,2,3,4,5,6};
        ByteBuffer buffer2 = ByteBuffer.wrap(byteArray);
        System.out.println(buffer2);
        System.out.println(buffer2.get(1));
        System.out.println(buffer2.get(2));
        System.out.println(buffer2.get(3));
        System.out.println(buffer2.get(4));
        System.out.println(buffer2);
    }

    @Test
    public void testBufferPut_Get4() {
        /** 写入 **/
        ByteBuffer buffer = ByteBuffer.allocate(10);
        byte[] byteArray = new byte[]{1,2,3,4,5,6};
        ByteBuffer buffer2 = ByteBuffer.wrap(byteArray);
        System.out.println(buffer);
        buffer.put(buffer2);
        System.out.println(buffer);

        for (int i = 0; i < buffer.array().length; i++) {
            System.out.println(buffer.array()[i] + " ");
        }
    }

    @Test
    public void testBufferPut_Get5() {
        /** 写入 **/
        ByteBuffer buffer = ByteBuffer.allocate(100);
        buffer.putChar('a');//0-1 char 占用2个字节
        buffer.putChar(2,'b');//2-3

        buffer.position(4);
        buffer.putDouble(1.2);//4-11 double 暂用8个字节
        buffer.putDouble(12,1.3); //12-19

        buffer.position(20);
        buffer.putFloat(2.1f);//20-24 Long占用8个字节
        buffer.putFloat(24,2.2f);//24-27

        buffer.position(28);
        buffer.putInt(1);//28-31 int 占用4字节
        buffer.putInt(32,2);//32-35

        buffer.position(36);
        buffer.putLong(3L);//36-43  Long占用8个字节
        buffer.putLong(44,4L);//44-51

        buffer.position(52);
        buffer.putShort((short) 3);//52-53  short占用2个字节
        buffer.putShort(54, (short) 4);//54-55
        buffer.position(0);
        System.out.println();
        System.out.println(buffer.getChar());
        System.out.println(buffer.getChar());
        System.out.println(buffer.getDouble());
        System.out.println(buffer.getDouble());
        System.out.println(buffer.getFloat());
        System.out.println(buffer.getFloat());
        System.out.println(buffer.getInt());
        System.out.println(buffer.getInt());
        System.out.println(buffer.getLong());
        System.out.println(buffer.getLong());
        System.out.println(buffer.getShort());
        System.out.println(buffer.getShort());
    }


    @Test
    public void testBuffer_slice() {
        byte[] byteArray = new byte[]{1,2,3,4,5,6};
        ByteBuffer buffer = ByteBuffer.wrap(byteArray);
        System.out.println(buffer);
        buffer.get();
        buffer.get();
        System.out.println(buffer);
        ByteBuffer slice = buffer.slice();
        System.out.println(slice);

        /** 共享数组数据 **/
        for (int i = 0; i < slice.array().length; i++) {
            System.out.println(slice.array()[i] + " ");
        }
    }


    @Test
    public void testBuffer_as() {
        System.out.println("你好");
    }

}
