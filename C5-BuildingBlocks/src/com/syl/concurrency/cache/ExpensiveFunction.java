package com.syl.concurrency.cache;

import java.math.BigInteger;

/**
 * 实现某种耗时操作
 * 
 * @author shenyunlong
 *
 */
public class ExpensiveFunction implements Computable<String, BigInteger> {

	@Override
	public BigInteger compute(String s) {
		System.out.println("Thread "+Thread.currentThread().getId()+": expensive function "+s);
		
		try {
			Thread.sleep(5000);  // 5s
		} catch (InterruptedException e) {
			//e.printStackTrace();
			Thread.currentThread().interrupt();
		}
		
		return new BigInteger(s);
	}
	
}
