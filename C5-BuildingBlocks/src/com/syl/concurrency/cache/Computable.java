package com.syl.concurrency.cache;

/**
 * Computable接口
 * @author shenyunlong
 *
 * @param <A>
 * @param <V>
 */
public interface Computable<A, V> {
	V compute(A a) throws InterruptedException;
}
