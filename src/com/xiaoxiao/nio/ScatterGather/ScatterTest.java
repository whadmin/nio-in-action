package com.xiaoxiao.nio.ScatterGather;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 注意buffer首先被插入到数组，然后再将数组作为channel.read()
 * 的输入参数。read()方法按照buffer在数组中的顺序将从channel中读取的数据写入到buffer，当一个buffer被写满后，channel紧接着向另一个buffer中写。 Scattering
 * Reads在移动下一个buffer前，必须填满当前的buffer，这也意味着它不适用于动态消息(译者注：消息大小不固定)。换句话说，如果存在消息头和消息体，消息头必须完成填充（例如 128byte），Scattering
 * Reads才能正常工作。
 */
public class ScatterTest {

    public static void main(String[] args) throws Exception {

        RandomAccessFile readFile = new RandomAccessFile("readdata.txt", "rw");
        RandomAccessFile writeFile = new RandomAccessFile("writedata.txt", "rw");

        FileChannel readChannel = readFile.getChannel();
        FileChannel writeChannel = writeFile.getChannel();

        ByteBuffer header = ByteBuffer.allocate(10);
        ByteBuffer body = ByteBuffer.allocate(10);

        ByteBuffer[] bufferArray = { header, body };

        long bytesRead;
        bytesRead = readChannel.read(bufferArray, 0, 10);
        header.flip();
        writeChannel.write(header);
        header.clear();

        body.flip();
        writeChannel.write(body);
        body.clear();

        readChannel.close();
        writeChannel.close();
        readFile.close();
        writeFile.close();
    }

}
