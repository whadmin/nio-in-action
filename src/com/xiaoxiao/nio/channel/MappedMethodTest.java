package com.xiaoxiao.nio.channel;

import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.stream.IntStream;

public class MappedMethodTest {





    @Test
    public void test_truncate() throws Exception {
        RandomAccessFile readFile = new RandomAccessFile("write11.txt", "rw");
        RandomAccessFile writeFile = new RandomAccessFile("write11.txt", "rw");
        FileChannel fileChannel = readFile.getChannel();
        FileChannel fileChannel2 = writeFile.getChannel();
        try{
            /** 写入文件字节缓冲区数据 **/
            ByteBuffer buffer=ByteBuffer.wrap("12345678".getBytes());
            System.out.println ("write() size= " + fileChannel.write(buffer)) ;
            System.out.println(fileChannel.size()+":"+fileChannel.position());

            fileChannel.truncate(3);
            System.out.println(fileChannel.size()+":"+fileChannel.position());

            ByteBuffer allocate = ByteBuffer.allocate(100);
            fileChannel2.read(allocate);
            System.out.println(new String(allocate.array()));
        }finally {
            readFile.close();
            fileChannel.close();
        }


    }







    @Test
    public void test_map_readOnly() throws Exception {
        RandomAccessFile readFile = new RandomAccessFile("map_read.txt", "rw");
        FileChannel fileChannel = readFile.getChannel();
        try{
            MappedByteBuffer map = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size());
            System.out.println((char) map.get());
            System.out.println((char) map.get());
            System.out.println((char) map.get());
            System.out.println((char) map.get());
            System.out.println((char) map.get());
            System.out.println((char) map.get());
            System.out.println((char) map.get());
            System.out.println((char) map.get());
            System.out.println((char) map.get());
            System.out.println(map);
            byte[] destArray = new byte[3];
            map.get(destArray,0,3);
            System.out.println(new String(destArray, Charset.forName("UTF-8")));


            map = fileChannel.map(FileChannel.MapMode.READ_ONLY, 2, 2);
            System.out.println((char) map.get());
            System.out.println((char) map.get());

            //map.putChar('a');


        }finally {
            readFile.close();
            fileChannel.close();
        }
    }


    @Test
    public void test_map_readAndWrite() throws Exception {
        RandomAccessFile readFile = new RandomAccessFile("map_write.txt", "rw");
        FileChannel fileChannel = readFile.getChannel();
        try{
            MappedByteBuffer map = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
            System.out.println( (char) map.get());
            System.out.println( (char)map.get());
            System.out.println( (char)map.get());
            System.out.println( (char)map.get());
            System.out.println( (char)map.get());


            map.position(0);
            map.put((byte) 'o');
            map.put((byte) 'p');
            map.put((byte) 'd');
            map.put((byte) 'r');
            map.put((byte) 's');

            ByteBuffer allocate = ByteBuffer.allocate(5);
            fileChannel.read(allocate);
            String s = new String(allocate.array());
            System.out.println(s);
        }finally {
            readFile.close();
            fileChannel.close();
        }
    }

    @Test
    public void test_map_private() throws Exception {
        RandomAccessFile readFile = new RandomAccessFile("map_write.txt", "rw");
        FileChannel fileChannel = readFile.getChannel();
        try{
            MappedByteBuffer map = fileChannel.map(FileChannel.MapMode.PRIVATE, 0, 5);


            map.position(0);
            map.put((byte) 'k');
            map.put((byte) 'k');
            map.put((byte) 'k');
            map.put((byte) 'k');
            map.put((byte) 'k');

            map.flip();

            System.out.println( (char) map.get());
            System.out.println( (char)map.get());
            System.out.println( (char)map.get());
            System.out.println( (char)map.get());
            System.out.println( (char)map.get());

            ByteBuffer allocate = ByteBuffer.allocate(5);
            fileChannel.read(allocate);
            String s = new String(allocate.array());
            System.out.println(s);
        }finally {
            readFile.close();
            fileChannel.close();
        }
    }

    @Test
    public void test_map_force() throws Exception {
        RandomAccessFile writeFile = new RandomAccessFile("map_write2.txt", "rw");
        FileChannel fileChannel = writeFile.getChannel();
        MappedByteBuffer map = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 5000);
        try{
            /** 写入文件字节缓冲区数据 **/
            ByteBuffer buffer=ByteBuffer.wrap("11111".getBytes());
            long begin = System.currentTimeMillis();
            for (int i=0;i<=100;i++){
                map.put(buffer);
                buffer.clear();
            }
            long end = System.currentTimeMillis();
            System.out.println(end-begin);


            ByteBuffer buffer1=ByteBuffer.wrap("22222".getBytes());
            begin = System.currentTimeMillis();
            for (int i=0;i<=100;i++){
                map.put(buffer1);
                map.force();
                buffer1.clear();
            }
            end = System.currentTimeMillis();
            System.out.println(end-begin);


        }finally {
            writeFile.close();
            fileChannel.close();
        }

    }

    @Test
    public void test_map_Load() throws Exception {
        RandomAccessFile writeFile = new RandomAccessFile("map_write2.txt", "rw");
        FileChannel fileChannel = writeFile.getChannel();
        MappedByteBuffer map = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 5000);
        System.out.println(map.isLoaded());
        map.load();
        System.out.println(map.isLoaded());
    }
}
