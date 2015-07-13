package com.syl.concurrency.cache;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * 1 - 并发缓存实现
 * ThreadSafe
 * 
 * @author shenyunlong
 *
 */
public class Memoizer1<A, V> implements Computable<A, V>{
	//@GuardedBy("this")
	private final Computable<A, V> computable;
	private final Map<A, V> hashmap = new HashMap<>();
	
	public Memoizer1(Computable<A, V> computable) {
		this.computable = computable;
	}
	
	/**
	 * 优点：实现简单
	 * 
	 * 缺点：可伸缩性问题，并行性能低
	 * 
	 * @throws InterruptedException 
	 * 
	 */
	@Override
	public synchronized V compute(A a) throws InterruptedException {
		System.out.println("Thread "+Thread.currentThread().getId()+": compute "+a);
		
		if(hashmap.containsKey(a)) {
			return hashmap.get(a);
		}
		
		V result = computable.compute(a);
		hashmap.put(a, result);
		
		return result;
	}

	/**
	 * Main 函数
	 * @param args
	 */
	public static void main(String[] args) {
		Computable<String, BigInteger> comp = new ExpensiveFunction();
		Memoizer1<String, BigInteger> memo = new Memoizer1<>(comp);
		
		for(int i=0; i<5; i++) {
			new Thread(new MyJob(memo)).start();
		}
	}
	

	private static class MyJob implements Runnable {

		private final Memoizer1<String, BigInteger> memo;
		
		public MyJob(Memoizer1<String, BigInteger> memo) {
			this.memo = memo;
		}
		
		@Override
		public void run() {
			try {
				int num = (int)(Math.random() * (100+1));
				memo.compute(""+num);
			} catch (InterruptedException e) {
				//e.printStackTrace();
				Thread.currentThread().interrupt();
			}
		}
		
	}
}
