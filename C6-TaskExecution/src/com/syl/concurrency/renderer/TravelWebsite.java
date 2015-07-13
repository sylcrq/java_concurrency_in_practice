package com.syl.concurrency.renderer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 旅行预订门户网站
 * 
 * @author shenyunlong
 *
 */
public class TravelWebsite {

	private final ExecutorService executor = Executors.newFixedThreadPool(10);
	
	/**
	 * 从公司获取报价的任务
	 * 
	 * @author shenyunlong
	 *
	 */
	private class QuoteTask implements Callable<TravelQuote> {

		@Override
		public TravelQuote call() throws Exception {
			// solicitQuote();
			
			return new TravelQuote();
		}
		
	}
	
	public List<TravelQuote> getRankedTravelQuotes(long timeout, TimeUnit unit) throws InterruptedException {
		List<QuoteTask> tasks = new ArrayList<>();
		
		for(int i=0; i<10; i++) {
			tasks.add(new QuoteTask());
		}
		
		// 支持限时的invokeAll
		// invokeAll按照Callable任务集合中迭代器的顺序将所有的Future添加到返回的集合中
		List<Future<TravelQuote>> futures = executor.invokeAll(tasks, timeout, unit);
		
		List<TravelQuote> quotes = new ArrayList<>();
		Iterator<QuoteTask> taskIter = tasks.iterator();
		
		for(Future<TravelQuote> f : futures) {
			// Future与Callable关联起来
			QuoteTask task = taskIter.next();
			
			try {
				quotes.add(f.get());
			} catch (ExecutionException e) {
				// quotes.add(task.getFailureQuote(e.getCause()));
			} catch (CancellationException e) {
				// 任务取消
				// quotes.add(task.getTimeoutQuote(e));
			}
		}
		
		return quotes;
	}
	
	/**
	 * 旅行报价信息
	 * 
	 * @author shenyunlong
	 *
	 */
	private static class TravelQuote {
		// TODO
	}
}
