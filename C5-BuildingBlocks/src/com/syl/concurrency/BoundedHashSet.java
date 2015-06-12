package com.syl.concurrency;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * 同步工具类 - 信号量
 * 利用Semaphore实现一个有界阻塞容器
 * 
 * @author shenyunlong
 *
 * @param <T>
 */
public class BoundedHashSet<T> {

	private final Set<T> set;
	private final Semaphore sem;
	
	public BoundedHashSet(int bound) {
		this.set = Collections.synchronizedSet(new HashSet<T>());
		this.sem = new Semaphore(bound);
	}
	
	public boolean add(T t) throws InterruptedException {
		sem.acquire();
		
		boolean added = false;
		try {
			added = set.add(t);  // add操作可能会抛出异常
			return added;
		} finally {
			if(!added) {
				sem.release();
			}
		}
	}
	
	public boolean remove(Object o) {
		boolean removed = set.remove(o);  // remove操作可能会抛出异常
		
		if(removed) {
			sem.release();
		}
		
		return removed;
	}
	
	/**
	 * Main函数
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		final BoundedHashSet<String> hashset = new BoundedHashSet<>(5);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(2000);  // 2s
					
					for(int i=0; i<4; i++) {
						hashset.remove(""+i);
						System.out.println("Remove: "+i);
					}
				} catch (InterruptedException e) {
					//e.printStackTrace();
					Thread.currentThread().interrupt();
				}
				
			}
		}).start();
		
		try {
			for(int i=0; i<7; i++) {
				hashset.add(""+i);
				System.out.println("Add: "+i);
			}
		} catch (InterruptedException e) {
			//e.printStackTrace();
			Thread.currentThread().interrupt();
		}

	}

}
