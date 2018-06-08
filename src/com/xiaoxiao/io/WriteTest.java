package com.xiaoxiao.io;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class WriteTest {
	
	public static void main(String[] args) throws Exception {
		String temp = "≤‚ ‘–¥»Î";  
		OutputStream Ouput = new FileOutputStream(new File("data.txt"));
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(Ouput)); 
		for (int i=0;i<10;i++) {  
			writer.write(temp);  
			writer.newLine();  
			//writer.flush();  
        }  
		writer.close();  
        System.out.println("–¥»Îok");  
	}

}
