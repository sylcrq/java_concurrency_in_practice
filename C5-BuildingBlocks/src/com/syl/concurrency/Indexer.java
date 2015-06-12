package com.syl.concurrency;

import java.io.File;
import java.util.concurrent.BlockingQueue;

/**
 * 消费者 - 生成索引
 * 
 * @author shenyunlong
 *
 */
public class Indexer implements Runnable {

	private final BlockingQueue<File> queue;
	
	public Indexer(BlockingQueue<File> queue) {
		this.queue = queue;
	}
	
	@Override
	public void run() {
		
		try {
			while(true) {
				File file = queue.take();
				
				System.out.println("*** "+file.getName()+" -> "+file.getAbsolutePath()+" ***");
			}
			
		} catch (InterruptedException e) {
			//e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}

}
