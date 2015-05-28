package com.syl.concurrency;

public class Main {

	private static NoVisibility visibility = new NoVisibility();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		new Thread(new MyJob()).start();
		new Thread(new MyJob()).start();
		new Thread(new MyJob()).start();
		new Thread(new MyJob()).start();
		new Thread(new MyJob()).start();
		new Thread(new MyJob()).start();
		new Thread(new MyJob()).start();
		new Thread(new MyJob()).start();
		new Thread(new MyJob()).start();
		new Thread(new MyJob()).start();
		
		visibility.num = 1024;
		visibility.num = 31;
		visibility.ready = true;
	}

	
	public static class MyJob implements Runnable {

		@Override
		public void run() {
			
			while(!visibility.ready)
				Thread.yield();
			
			System.out.println("Thread "+Thread.currentThread().getId()+": num = "+visibility.num);
		}
		
	}
}
