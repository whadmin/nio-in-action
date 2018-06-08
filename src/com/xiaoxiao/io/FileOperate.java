/*
 * Copyright 2016 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.xiaoxiao.io;


import java.io.BufferedReader;  
import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileReader;  
import java.io.IOException;  
import java.io.InputStream;  
/** 
 * <b>�ļ���ȡ��</b><br /> 
 * 1�����ֽڶ�ȡ�ļ�����<br /> 
 * 2�����ַ���ȡ�ļ�����<br /> 
 * 3�����ж�ȡ�ļ�����<br /> 
 * @author qin_xijuan 
 * 
 */  
public class FileOperate {  
      
    private static final String FILE_PATH = "d:/work/the List of Beautiful Music.txt";  
  
    /** 
     * ���ֽ�Ϊ��λ��ȡ�ļ����� 
     * @param filePath����Ҫ��ȡ���ļ�·�� 
     */  
    public static void readFileByByte(String filePath) {  
        File file = new File(filePath);  
        // InputStream:�˳������Ǳ�ʾ�ֽ���������������ĳ��ࡣ  
        InputStream ins = null ;  
        try{  
            // FileInputStream:���ļ�ϵͳ�е�ĳ���ļ��л�������ֽڡ�  
            ins = new FileInputStream(file);  
            int temp ;  
            // read():���������ж�ȡ���ݵ���һ���ֽڡ�  
            while((temp = ins.read())!=-1){  
                System.out.write(temp);  
            }  
        }catch(Exception e){  
            e.getStackTrace();  
        }finally{  
            if (ins != null){  
                try{  
                    ins.close();  
                }catch(IOException e){  
                    e.getStackTrace();  
                }  
            }  
        }  
    }  
      
    /** 
     * ���ַ�Ϊ��λ��ȡ�ļ����� 
     * @param filePath 
     */  
    public static void readFileByCharacter(String filePath){  
        File file = new File(filePath);  
        // FileReader:������ȡ�ַ��ļ��ı���ࡣ  
        FileReader reader = null;  
        try{  
            reader = new FileReader(file);  
            int temp ;  
            while((temp = reader.read()) != -1){  
                if (((char) temp) != '\r') {  
                    System.out.print((char) temp);  
                }  
            }  
        }catch(IOException e){  
            e.getStackTrace();  
        }finally{  
            if (reader != null){  
                try {  
                    reader.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
    }  
      
    /** 
     * ����Ϊ��λ��ȡ�ļ����� 
     * @param filePath 
     */  
    public static void readFileByLine(String filePath){  
        File file = new File(filePath);  
        // BufferedReader:���ַ��������ж�ȡ�ı�����������ַ����Ӷ�ʵ���ַ���������еĸ�Ч��ȡ��  
        BufferedReader buf = null;  
        try{  
            // FileReader:������ȡ�ַ��ļ��ı���ࡣ  
            buf = new BufferedReader(new FileReader(file));  
            // buf = new BufferedReader(new InputStreamReader(new FileInputStream(file)));  
            String temp = null ;  
            while ((temp = buf.readLine()) != null ){  
                System.out.println(temp);  
            }  
        }catch(Exception e){  
            e.getStackTrace();  
        }finally{  
            if(buf != null){  
                try{  
                    buf.close();  
                } catch (IOException e) {  
                    e.getStackTrace();  
                }  
            }  
        }  
    }  
  
    public static void main(String args[]) {  
        readFileByByte(FILE_PATH);  
        readFileByCharacter(FILE_PATH);  
        readFileByLine(FILE_PATH);  
    }  
}  
