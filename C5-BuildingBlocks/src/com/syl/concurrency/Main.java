package com.syl.concurrency;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 同步容器: Vector, Hashtable, Collections.synchronizedXxx
 * 
 * 并发容器: ConcurrentHashMap, CopyOnWriteArrayList, ...
 * 
 * @author shenyunlong
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String pathName = "F:\\WDJDownload";
		File file = new File(pathName);
		FileFilter filter = new FileFilter() {
			
			@Override
			public boolean accept(File pathname) {
				return true;
			}
		};
		
		BlockingQueue<File> queue = new LinkedBlockingQueue<File>();
		
		new Thread(new FileCrawler(queue, file, filter)).start();
		new Thread(new Indexer(queue)).start();
	}

}
