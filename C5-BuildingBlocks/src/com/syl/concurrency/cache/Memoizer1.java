package com.syl.concurrency.cache;

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
		
		if(hashmap.containsKey(a)) {
			return hashmap.get(a);
		}
		
		V result = computable.compute(a);
		hashmap.put(a, result);
		
		return result;
	}

}
