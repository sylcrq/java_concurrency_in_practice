package com.syl.concurrency;

public class ThreadLocalTest {
	
	/**
	 * Runnable 接口实现
	 * @author shenyunlong
	 *
	 */
	public static class MyRunnable implements Runnable {

		private ThreadLocal<String> mThreadLocal = new ThreadLocal<String>() {
			@Override
			protected String initialValue() {
				//return super.initialValue();
				return "1024";
			}
		};
		
		@Override
		public void run() {
			mThreadLocal.set(""+Math.random());
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("Thread "+Thread.currentThread().getId()+", "+mThreadLocal.get());
		}
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Runnable sharedRunnable = new MyRunnable();
		
		new Thread(sharedRunnable).start();
		new Thread(sharedRunnable).start();
		new Thread(sharedRunnable).start();
		new Thread(sharedRunnable).start();
		new Thread(sharedRunnable).start();
	}

}
