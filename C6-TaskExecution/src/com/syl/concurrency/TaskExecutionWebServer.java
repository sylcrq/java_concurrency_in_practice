package com.syl.concurrency;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Executor框架
 * 
 * @author shenyunlong
 *
 */
public class TaskExecutionWebServer {
	private static final int NTHREADS = 10;
	private static final Executor executor = Executors.newFixedThreadPool(NTHREADS);
	
	/**
	 * Main函数
	 * @param args
	 */
	public static void main(String[] args) {
		for(int i=0; i<18; i++) {
			Runnable runnable = new Runnable() {
				
				@Override
				public void run() {
					System.out.println("Thread "+Thread.currentThread().getId()+": Start");
					
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						//e.printStackTrace();
						Thread.currentThread().interrupt();
					}
					
					System.out.println("Thread "+Thread.currentThread().getId()+": End");
				}
			};
			
			executor.execute(runnable);
		}
	}
}
