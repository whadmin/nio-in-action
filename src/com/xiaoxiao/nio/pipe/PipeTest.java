package com.xiaoxiao.nio.pipe;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

public class PipeTest {

	public static void main(String[] args) throws Exception {
		//创建一个管道
		Pipe pipe = Pipe.open();
		String newData = "etrtrtr";

		Pipe.SinkChannel sinkChannel = pipe.sink();
		ByteBuffer buf = ByteBuffer.allocate(48);
		buf.put(newData.getBytes());
		buf.flip();

		while (buf.hasRemaining()) {
			sinkChannel.write(buf);
		}

		Pipe.SourceChannel sourceChannel = pipe.source();
		buf = ByteBuffer.allocate(48);
		sourceChannel.read(buf);
		buf.flip();
		
		
		System.out.println((char)buf.get());
		System.out.println((char)buf.get());
		System.out.println((char)buf.get());
		
		
	}

}
