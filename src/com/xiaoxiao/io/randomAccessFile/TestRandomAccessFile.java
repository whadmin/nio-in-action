package com.xiaoxiao.io.randomAccessFile;

import java.io.IOException;  
import java.io.RandomAccessFile;  

/**
 * http://blog.csdn.net/akon_vm/article/details/7429245
 * @author xu.jianguo
 */
public class TestRandomAccessFile {  
    public static void main(String[] args) throws IOException {  
        RandomAccessFile rf = new RandomAccessFile("rtest.dat", "rw");  
        for (int i = 0; i < 10; i++) {  
            //д���������double����  
            rf.writeDouble(i * 1.414);  
        }  
        rf.close();  
        rf = new RandomAccessFile("rtest.dat", "rw");  
        //ֱ�ӽ��ļ�ָ���Ƶ���5��double���ݺ���  
        rf.seek(5 * 8);  
        //���ǵ�6��double����  
        rf.writeDouble(47.0001);  
        rf.close();  
        rf = new RandomAccessFile("rtest.dat", "r");  
        for (int i = 0; i < 10; i++) {  
            System.out.println("Value " + i + ": " + rf.readDouble());  
        }  
        rf.close();  
    }  
}   
