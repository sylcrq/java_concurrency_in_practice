package com.syl.concurrency;

import java.util.Date;

public class FooTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Runnable job = new MyRunnable();
		
		new Thread(job).start();
		new Thread(job).start();
		new Thread(job).start();
		new Thread(job).start();
		new Thread(job).start();
	}

	
	public static class MyRunnable implements Runnable {

		private Foo foo = new Foo();
		
		@Override
		public void run() {
			Date date = new Date();
			
			System.out.println("Thread "+Thread.currentThread().getId()+", "+foo.format(date));
		}
		
	}
}
