package com.syl.concurrency;

import java.util.Vector;

public class VectorTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 同步容器Vector
		Vector<String> strArray = new Vector<>();
		
		for(int i=0; i<100; i++) {
			strArray.add(""+i);
		}
		
		new Thread(new MyJob(strArray)).start();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		strArray.remove(67);  // ConcurrentModificationException
	}

	public static class MyJob implements Runnable {

		private Vector<String> strArray;
		
		public MyJob(Vector<String> strArray) {
			this.strArray = strArray;
		}
		
		@Override
		public void run() {
			// 迭代器遍历
			for(String str : strArray) {
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
