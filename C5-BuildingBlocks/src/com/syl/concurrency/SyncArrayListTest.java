package com.syl.concurrency;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SyncArrayListTest {
	
	/**
	 * 会抛出ConcurrentModificationException异常
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// 同步ArrayList
		List<String> strList = Collections.synchronizedList(new ArrayList<String>());
		for(int i=0; i<100; i++) {
			strList.add(""+i);
		}
		
		new Thread(new MyJob(strList)).start();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		strList.remove(10);  // ConcurrentModificationException
	}

	public static class MyJob implements Runnable {

		private List<String> strList;
		
		public MyJob(List<String> strList) {
			this.strList = strList;
		}
		
		@Override
		public void run() {
			// 迭代器遍历
			for(String str : strList) {
				System.out.println(str);
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
}
