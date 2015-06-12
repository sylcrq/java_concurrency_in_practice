package com.syl.concurrency;

import java.util.concurrent.CountDownLatch;


/**
 * 同步工具类 - 闭锁
 * CountDownLatch
 * 
 * @author shenyunlong
 *
 */
public class TestHarness {
	
	public long timeTasks(int num, final Runnable task) throws InterruptedException {
		
		// 启动门 -> 主线程同时释放所有工作线程
		final CountDownLatch startGate = new CountDownLatch(1);
		// 结束门 -> 主线程等待最后一个线程执行完成
		final CountDownLatch endGate = new CountDownLatch(num);
		
		for(int i=0; i<num; i++) {			
			Thread thread = new Thread() {

				@Override
				public void run() {
					try {
						startGate.await();  // 等待启动
						
						System.out.println("Thread: "+Thread.currentThread().getId()+" start");
						task.run();
						System.out.println("Thread: "+Thread.currentThread().getId()+" end");
						
						endGate.countDown();						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			};
			
			thread.start();
		}
		
		long start = System.nanoTime();
		System.out.println("GO!!!");
		startGate.countDown();
		
		endGate.await();  // 等待所有线程完成
		long end = System.nanoTime();
		System.out.println("FINISH!!!");
		
		return end-start;
	}
	
	/**
	 * Main函数
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		TestHarness test = new TestHarness();
		
		Runnable task = new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					//e.printStackTrace();
					Thread.currentThread().interrupt();
				}
			}
		};
		
		try {
			long time = test.timeTasks(10, task);
			
			System.out.println("total time = "+time+"ns");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
