package com.syl.concurrency;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest implements Runnable {

	private static final int THREAD_POOL_SIZE = 3;
	private static final int MAX_THREAD_POOL_SIZE = 5;
	private static final long KEEP_ALIVE_TIME = 10;
	private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
		ThreadPoolExecutor executor = new ThreadPoolExecutor(THREAD_POOL_SIZE, 
				MAX_THREAD_POOL_SIZE, 
				KEEP_ALIVE_TIME, 
				KEEP_ALIVE_TIME_UNIT, 
				queue);
		
		for(int i=0; i<10; i++) {
			executor.execute(new ThreadPoolTest());
			System.out.println("线程队列大小: "+queue.size());
		}
	}

	@Override
	public void run() {		
		try {
			System.out.println(Thread.currentThread().getName());
			Thread.sleep(5000);  // 5s
			System.out.println(Thread.currentThread().getName()+" End");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
