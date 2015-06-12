package com.syl.concurrency;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 同步工具类 - 栅栏
 * CyclicBarrier
 * 
 * @author shenyunlong
 *
 */
public class CellularAutomata {

	private final CyclicBarrier barrier;
	private final MyJob[] jobs;
	
	public CellularAutomata() {
		int count = Runtime.getRuntime().availableProcessors();  // CPU数量
		
		this.barrier = new CyclicBarrier(count, new Runnable() {
			
			@Override
			public void run() {
				System.out.println("done");
			}
		});
		
		this.jobs = new MyJob[count];
		for(int i=0; i<count; i++) {
			jobs[i] = new MyJob();
		}
	}
	
	public void start() {
		System.out.println("Count= "+jobs.length);
		
		for(MyJob job : jobs) {
			new Thread(job).start();
		}
	}
	
	private class MyJob implements Runnable {

		@Override
		public void run() {
			try {
				System.out.println("Thread "+Thread.currentThread().getId()+": do my job");
				Thread.sleep(2000);  // 2s
				
				barrier.await();
			} catch (InterruptedException e) {
				//e.printStackTrace();
				Thread.currentThread().interrupt();
			} catch (BrokenBarrierException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CellularAutomata test = new CellularAutomata();		
		test.start();
	}

}
