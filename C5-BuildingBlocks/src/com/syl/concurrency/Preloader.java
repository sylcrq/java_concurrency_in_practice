package com.syl.concurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 同步工具类 - FutureTask
 * 
 * @author shenyunlong
 *
 */
public class Preloader {

	private final FutureTask<String> future = new FutureTask<>(new Callable<String>() {

		@Override
		public String call() throws Exception {			
			String data = "hello world";
			
			Thread.sleep(1000);
			
			return data;
		}
		
	});

	private final Thread thread = new Thread(future);
	
	public void start() {
		thread.start();
	}
	
	/**
	 * 注意对于异常的处理
	 * @return
	 * @throws InterruptedException
	 */
	public String get() throws InterruptedException {
		try {
			return future.get();
		} catch(ExecutionException e) {
			Throwable cause = e.getCause();
			
			throw launderThrowable(cause);
		}
	}
	
	public static RuntimeException launderThrowable(Throwable t) {
		
		if(t instanceof RuntimeException) {
			return (RuntimeException) t;
		} else if(t instanceof Error) {
			throw (Error) t;
		} else {
			throw new IllegalStateException("Not unchecked", t);
		}
	}
	
	/**
	 * Main函数
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Preloader test = new Preloader();
		
		System.out.println("START");
		test.start();
		
		
		try {
			System.out.println(test.get());
		} catch (InterruptedException e) {
			//e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}

}
