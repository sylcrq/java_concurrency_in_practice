package com.syl.concurrency.cache;

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
		if(hashmap.containsKey(a)) {
			return hashmap.get(a);
		}
		
		V result = computable.compute(a);
		hashmap.put(a, result);
		return result;
	}

}
