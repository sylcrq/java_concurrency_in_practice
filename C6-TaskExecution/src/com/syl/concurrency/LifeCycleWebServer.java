package com.syl.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

/**
 * 增加生命周期的Web Server
 * ExecutorService的生命周期: 运行、关闭、已结束
 * 
 * shutdown: 平缓的关闭。不再接受新的任务，同时等待已经提交的任务执行完成（包括还没有开始执行的任务）
 * shutdownNow: 粗暴的关闭。尝试取消所有运行中的任务，并且不再启动队列中还没有开始执行的任务
 * 
 * @author shenyunlong
 *
 */
public class LifeCycleWebServer {

	private static final int NTHREADS = 10;
	private final ExecutorService executorService = Executors.newFixedThreadPool(NTHREADS);
	
	public void start() throws InterruptedException {
		System.out.println("start");
		
		while(!executorService.isShutdown()) {
			try {
				Runnable runnable = new Runnable() {
				
					@Override
					public void run() {
						System.out.println("Thread "+Thread.currentThread().getId()+": working...");
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							//e.printStackTrace();
							Thread.currentThread().interrupt();
						}
						System.out.println("Thread "+Thread.currentThread().getId()+": finish...");
					}
				};
				executorService.execute(runnable);
				
				Thread.sleep(1000);
			} catch(RejectedExecutionException e) {
				System.out.println("task submission rejected");
			}
		}
	}

	public void stop() {
		System.out.println("stop");
		executorService.shutdown();
	}
	
	/**
	 * Main 函数
	 * @param args
	 */
	public static void main(String[] args) {
		final LifeCycleWebServer server = new LifeCycleWebServer();		
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(1000 * 30);  // 30s
					
					server.stop();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
		
		try {
			server.start();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
