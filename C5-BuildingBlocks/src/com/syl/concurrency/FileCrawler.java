package com.syl.concurrency;

import java.io.File;
import java.io.FileFilter;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * 生产者 - 遍历指定目录
 * 
 * @author shenyunlong
 *
 */
public class FileCrawler implements Runnable {

	private final BlockingQueue<File> queue;
	private final File root;
	private final FileFilter filter;
	
	public FileCrawler(BlockingQueue<File> queue, File root, FileFilter filter) {
		this.queue = queue;
		this.root = root;
		this.filter = filter;
	}
	
	@Override
	public void run() {
		// 遍历目录（非递归）
		List<File> dirs = new LinkedList<File>();
		
		try {
			if(root.isFile()) {
				System.out.println(root.getAbsolutePath());
				queue.put(root);
			} else if(root.isDirectory()) {
				dirs.add(root);
			}
			
			while(!dirs.isEmpty()) {
				File dir = dirs.get(0);
				dirs.remove(0);
				
				File[] files = dir.listFiles();
				for(File file : files) {
					if(file.isFile()) {
						System.out.println(file.getAbsolutePath());
						queue.put(file);
					} else if(file.isDirectory()) {
						dirs.add(file);
					}
				}
			}
		} catch(InterruptedException e) {
			//e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}


}
