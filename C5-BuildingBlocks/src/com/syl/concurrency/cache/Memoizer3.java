package com.syl.concurrency.cache;

import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 3 - 并发缓存实现
 * ThreadSafe
 * 
 * @author shenyunlong
 *
 * @param <A>
 * @param <V>
 */
public class Memoizer3<A, V> implements Computable<A, V>{

	private final Computable<A, V> computable;
	private final Map<A, FutureTask<V>> cache = new ConcurrentHashMap<>();  // 并发容器 + FutureTask
	
	public Memoizer3(Computable<A, V> computable) {
		this.computable = computable;
	}
	
	/**
	 * 优点：并发性能比Memoizer2好
	 * 
	 * 缺点：特定情况下仍然有重复计算的可能性，虽然比Memoizer2低很多
	 */
	@Override
	public V compute(final A a) throws InterruptedException {
		System.out.println("Thread "+Thread.currentThread().getId()+": compute "+a);
		
		FutureTask<V> value = cache.get(a);
		
		if(value == null) {
			FutureTask<V> task = new FutureTask<>(new Callable<V>() {

				@Override
				public V call() throws InterruptedException {
					return computable.compute(a);
				}
			});
			
			value = task;
			cache.put(a, task);
			//cache.putIfAbsent(a, task);
			task.run();  // 注意
		}
		
		try {
			return value.get();
		} catch (ExecutionException e) {
			//e.printStackTrace();
			throw launderThrowable(e.getCause());  // 注意异常的处理
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
	 * Main 函数
	 * @param args
	 */
	public static void main(String[] args) {
		Computable<String, BigInteger> comp = new ExpensiveFunction();
		Memoizer3<String, BigInteger> memo = new Memoizer3<>(comp);
		
		for(int i=0; i<5; i++) {
			new Thread(new MyJob(memo)).start();
		}
	}
	
	private static class MyJob implements Runnable {
		private final Memoizer3<String, BigInteger> memo;
		
		public MyJob(Memoizer3<String, BigInteger> memo) {
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
