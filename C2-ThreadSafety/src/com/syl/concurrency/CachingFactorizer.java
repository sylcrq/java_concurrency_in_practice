package com.syl.concurrency;

import java.math.BigInteger;

public class CachingFactorizer {
	// 缓存数据
	private BigInteger lastNumber;
	private BigInteger[] lastFactors;
	
	public CachingFactorizer() {
		lastNumber = BigInteger.valueOf(0);
		lastFactors = new BigInteger[2];
		lastFactors[0] = BigInteger.valueOf(0);
		lastFactors[1] = BigInteger.valueOf(0);
	}
	
	/**
	 * 同步接口（简单、性能较差）
	 * @param number
	 * @return
	 */
	public synchronized BigInteger[] service(BigInteger number) {

		System.out.println("Thread "+Thread.currentThread().getId()+": service start, "+number.intValue());
		
		if(number.compareTo(lastNumber) == 0) {
			System.out.println("Thread "+Thread.currentThread().getId()+": service end, cache hit, "+
								lastFactors+", "+lastFactors[0].intValue()+" * "+lastFactors[1].intValue());
			
			return lastFactors.clone();  //copy
		}
		
		BigInteger[] result = factor(number);
		
		// update
		lastNumber = number;
		lastFactors = result.clone();  // copy
		
		System.out.println("Thread "+Thread.currentThread().getId()+": service end, "+
							result+", "+result[0].intValue()+" * "+result[1].intValue());
		
		return result;
	}
	
	/**
	 * 对性能进行优化的同步方案
	 * @param number
	 * @return
	 */
//	public BigInteger[] service_better(BigInteger number) {
//		
//		synchronized (this) {
//			if(number.compareTo(lastNumber) == 0) {
//				return lastFactors.clone();
//			}
//		}
//		
//		BigInteger[] result = factor(number);
//		
//		synchronized (this) {
//			lastNumber = number;
//			lastFactors = result.clone();
//		}
//		
//		return result;
//	}
	
	// 模拟因式分解
	private BigInteger[] factor(BigInteger number) {
		BigInteger[] result = new BigInteger[2];
		
		if(0 == number.compareTo(BigInteger.valueOf(0))) {
			result[0] = BigInteger.valueOf(0);
			result[1] = BigInteger.valueOf(0);
		} else {
			result[0] = BigInteger.valueOf(1);
			result[1] = number;
		}
		
		try {
			// 模拟耗时操作, 2s
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
}
