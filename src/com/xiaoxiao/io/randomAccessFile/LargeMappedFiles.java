package com.xiaoxiao.io.randomAccessFile;

import java.io.RandomAccessFile;  
import java.nio.MappedByteBuffer;  
import java.nio.channels.FileChannel;  
  
public class LargeMappedFiles {  
    static int length = 0x8000000; // 128 Mb  
  
    public static void main(String[] args) throws Exception {  
        // Ϊ���Կɶ���д�ķ�ʽ���ļ�������ʹ��RandomAccessFile�������ļ���  
        FileChannel fc = new RandomAccessFile("test.dat", "rw").getChannel();  
        //ע�⣬�ļ�ͨ���Ŀɶ���дҪ�������ļ�������ɶ�д�Ļ���֮��  
        MappedByteBuffer out = fc.map(FileChannel.MapMode.READ_WRITE, 0, length);  
        //д128M������  
        for (int i = 0; i < length; i++) {  
            out.put((byte) 'x');  
        }  
        System.out.println("Finished writing");  
        //��ȡ�ļ��м�6���ֽ�����  
        for (int i = length / 2; i < length / 2 + 6; i++) {  
            System.out.print((char) out.get(i));  
        }  
        fc.close();  
    }  
}  
