package com.syl.concurrency.cache;

import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 2 - 并发缓存实现
 * ThreadSafe
 * 
 * @author shenyunlong
 *
 * @param <A>
 * @param <V>
 */
public class Memoizer2<A, V> implements Computable<A, V> {

	private final Computable<A, V> computable;
	private final Map<A, V> hashmap = new ConcurrentHashMap<A, V>();  // 并发容器
	
	public Memoizer2(Computable<A, V> computable) {
		this.computable = computable;
	}
	
	/**
	 * 优点：并发性能比Memoizer1好
	 * 
	 * 缺点：有可能会重复计算
	 * @throws InterruptedException 
	 */
	@Override
	public V compute(A a) throws InterruptedException {
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
		Memoizer2<String, BigInteger> memo = new Memoizer2<>(comp);
		
		for(int i=0; i<5; i++) {
			new Thread(new MyJob(memo)).start();
		}
	}
	
	
	private static class MyJob implements Runnable {
		private final Memoizer2<String, BigInteger> memo;
		
		public MyJob(Memoizer2<String, BigInteger> memo) {
			this.memo = memo;
		}
		
		@Override
		public void run() {
			int num = (int)(Math.random() * (5+1));
			try {
				memo.compute(""+num);
				System.out.println("Thread "+Thread.currentThread().getId()+": end "+num);
			} catch (InterruptedException e) {
				//e.printStackTrace();
				Thread.currentThread().interrupt();
			}
		}
		
	}
}
