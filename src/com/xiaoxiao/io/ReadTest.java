package com.xiaoxiao.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ReadTest {

	public static void main(String[] args) throws Exception {
		
		InputStream input = new FileInputStream(new File("data.txt"));
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));  
		String nameLine   = reader.readLine();  
		String ageLine    = reader.readLine();  
		String emailLine  = reader.readLine();  
		String phoneLine  = reader.readLine();
		System.out.println(nameLine);
		System.out.println(ageLine);
		System.out.println(emailLine);
		System.out.println(phoneLine);

	}

}
