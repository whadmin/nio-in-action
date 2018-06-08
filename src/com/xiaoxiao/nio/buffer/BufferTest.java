package com.xiaoxiao.nio.buffer;

import java.nio.ByteBuffer;

public class BufferTest {

    public static void main(String[] args) throws Exception {
        testrewind();
        testmark();
        testcompact();
    }

    /**
     * capacity&position&limit���� 
     * 
     * capacity ��ʾbuff ���� ������������10 ����дģʽһ�£� 
     * position ��ʾ��ʼλ�� 
     * limit ��ʾ�޶�λ�� 
     * flip() �л�ģʽ����   position=0 limit=position clear() ����buf��Ϊ��ʼ position=0 limit=���� capacity=����
     */
    public static void testBuffer() {
        /** ��ʼ��Ϊ position 0 limit 10 capacity 10 **/
        ByteBuffer bufwrite = ByteBuffer.allocate(10);
        /** ����дģʽ(д��ģʽһ) position 4 limit 10 capacity 10 д��4���ֽ� ����ܳ���limit 10 **/
        bufwrite.put("test".getBytes());
        /** �л���ģʽ position 0 limit 4 capacity 10 ��ģʽֻ�ܶ�ȡbuf��д������ݣ���test�� limitֵ��Ϊ�޶���д��ģʽ��positionֵ��positionֵ��0 **/
        bufwrite.flip();
        /** �л���ģʽ position 0 limit 0 capacity 10 �����л�ģʽ���� �����ϴ�û�ж�ȡposition=0 �����л��� limit=0 ����Ҳ������д�� **/
        bufwrite.flip();
        /** ���� **/
        // bufwrite.put("s".getBytes());

        bufwrite.clear();

        /** ����дģʽ(д��ģʽһ) position 4 limit 10 capacity 10 д��4���ֽ� ����ܳ���limit 10 **/
        bufwrite.put("test".getBytes());
        /** �л���ģʽ position 0 limit 4 capacity 10 ��ģʽֻ�ܶ�ȡbuf��д������ݣ���test�� limitֵ��Ϊ�޶���д��ģʽ��positionֵ��positionֵ��0 **/
        bufwrite.flip();
        /** ��ȡһ���ֽ� position 1 limit 4 capacity 10 **/
        System.out.println(bufwrite.get());
        /** �л���ģʽ position 0 limit 1 capacity 10 �����л�ģʽ���� �����ϴ�û�ж�ȡposition=0 �����л��� limit=0 ����Ҳ������д�� **/
        bufwrite.flip();
        /** д��һ���ֽ� ����limit�ֽ�������BufferOverflowException **/
        bufwrite.put("s".getBytes());
    }

    /**
     * Buffer.rewind()��position���0������������ض�Buffer�е��������ݡ�limit���ֲ��䣬��Ȼ��ʾ�ܴ�Buffer�ж�ȡ���ٸ�Ԫ�أ�byte��char�ȣ���
     */
    public static void testrewind() {

        /** Bufeer rewind()�ظ���ȡ **/
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
     * ͨ������Buffer.mark()���������Ա��Buffer�е�һ���ض�position��֮�����ͨ������Buffer.reset()�����ָ������position
     */
    public static void testmark() {

        /** Bufeer rewind()�ظ���ȡ **/
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
     * compact()����������δ�������ݿ�����Buffer��ʼ����Ȼ��position�赽���һ��δ��Ԫ�������档limit������Ȼ��clear()����һ�������ó�capacity������Buffer׼����д������
     */
    public static void testcompact() {
        /** Bufeer rewind()�ظ���ȡ **/
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
