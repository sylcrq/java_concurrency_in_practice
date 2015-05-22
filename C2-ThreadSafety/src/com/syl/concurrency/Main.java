package com.syl.concurrency;

import java.math.BigInteger;
import java.util.Random;

public class Main {

	private CachingFactorizer factorizer = new CachingFactorizer();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
		Main main = new Main();
		
		new Thread(main.new MyJob()).start();
		new Thread(main.new MyJob()).start();
		new Thread(main.new MyJob()).start();
		new Thread(main.new MyJob()).start();
		new Thread(main.new MyJob()).start();
		new Thread(main.new MyJob()).start();
		new Thread(main.new MyJob()).start();
		new Thread(main.new MyJob()).start();
		new Thread(main.new MyJob()).start();
		new Thread(main.new MyJob()).start();
	}

	
	public class MyJob implements Runnable {

		@Override
		public void run() {
			// [0, 100]之间的随机数
			// http://stackoverflow.com/questions/363681/generating-random-integers-in-a-range-with-java
			BigInteger[] result = factorizer.service(BigInteger.valueOf(new Random().nextInt(101)));
			//BigInteger[] result = factorizer.service(BigInteger.valueOf(8));
			
			//BigInteger[] result = factorizer.service_better(BigInteger.valueOf(new Random().nextInt(101)));
			
			System.out.println("Thread "+Thread.currentThread().getId()+": "+result);
		}
		
	}
}
