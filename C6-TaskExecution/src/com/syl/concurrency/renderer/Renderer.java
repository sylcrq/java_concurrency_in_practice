package com.syl.concurrency.renderer;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * CompletionService实现页面渲染器
 * 
 * 将"相互独立"并且"同构"的任务进行并发处理 -> 下载图片
 * 
 * @author shenyunlong
 *
 */
public class Renderer {

	private final ExecutorService executor = Executors.newFixedThreadPool(10);

	private static final long TIME_BUDGET = 1000000;
	private static final Ad DEFAULT_AD = new Ad();
	
	public void renderPage() {
		CompletionService<ImageData> completionService = new ExecutorCompletionService<>(executor);

		int num = 10;
		// 为每个image的下载都创建一个独立任务
		for(int i=0; i<num; i++) {		
			completionService.submit(new Callable<Renderer.ImageData>() {
				
				@Override
				public ImageData call() throws Exception {				
					ImageData data = new ImageData();
					// downloadImage();
					
					return data;
				}
			});
		}
		
		//renderText();

		try {
			for(int i=0; i<num; i++) {
				Future<ImageData> task = completionService.take();
				ImageData imageData = task.get();
				//renderImage();
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} catch (ExecutionException e) {
			throw launderThrowable(e.getCause());
		}

	}

	/**
	 * 限时任务
	 * @throws InterruptedException 
	 */
	public void renderPageWithAd() throws InterruptedException {
		long endNanos = System.nanoTime() + TIME_BUDGET;
		
		Future<Ad> task = executor.submit(new Callable<Ad>() {

			@Override
			public Ad call() throws Exception {
				// downloadAd();
				
				return new Ad();
			}
			
		});
		
		Ad ad;
		
		try {
			long timeLeft = endNanos - System.nanoTime();
			ad = task.get(timeLeft, TimeUnit.NANOSECONDS);
		} catch (ExecutionException e) {
			ad = DEFAULT_AD;
		} catch (TimeoutException e) {
			ad = DEFAULT_AD;
			// 取消任务
			task.cancel(true);
		}
		
		// renderAd(ad);
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
	
	public static class ImageData {
		// TODO
	}
	
	public static class Ad {
		// TODO
	}
}
