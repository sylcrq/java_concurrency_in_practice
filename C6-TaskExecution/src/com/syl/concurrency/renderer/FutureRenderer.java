package com.syl.concurrency.renderer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 使用Future实现的页面渲染器
 * 
 * 渲染文本（CPU密集型）和下载图片（I/O密集型）任务并发的执行
 * 
 * @author shenyunlong
 *
 */
public class FutureRenderer {

	private final ExecutorService executor = Executors.newFixedThreadPool(10);
	
	public void renderPage() {
		
		Callable<List<ImageData>> task = new Callable<List<ImageData>>() {

			@Override
			public List<ImageData> call() throws Exception {
				List<ImageData> result = new ArrayList<FutureRenderer.ImageData>();				
				// downloadImage();
				
				return result;
			}
			
		};
		
		Future<List<ImageData>> future = executor.submit(task);
		
		// renderText();
		
		try {
			List<ImageData> data = future.get();
			// renderImage();
			
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			// 取消任务
			future.cancel(true);
		} catch (ExecutionException e) {
			
			throw launderThrowable(e.getCause());
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
	
	public static class ImageData {
		// TODO
	}
}
