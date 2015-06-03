package com.syl.concurrency;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Main {

	public static void test1() {
		List<String> list = new ArrayList<>();
		
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		list.add("5");
		
		System.out.println(list);
		
		// 同步List
		List<String> syncList = Collections.synchronizedList(list);
		
		// 遍历同步list也必须加锁
		synchronized(syncList) {
			Iterator<String> i = syncList.iterator();
			while(i.hasNext()) {
				System.out.println(i.next());
			}
		}
	}
	
	public static void test2() {
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("key1", "value1");
		map.put("key2", "value2");
		map.put("key3", "value3");
		
		System.out.println(map);
		
		// 只读Map
		Map<String, String> unModifiedMap = Collections.unmodifiableMap(map);		
		System.out.println(unModifiedMap);
		
//		unModifiedMap.put("key4", "value5");  // Error
	}
	
	public static void test3() {
		ConcurrentHashMap<String, String> map = new ConcurrentHashMap<String, String>();
		
		map.put("key1", "value1");
		map.put("key2", "value2");
		map.put("key3", "value3");
		map.put("key4", "value4");
		
		System.out.println(map);
		
		Map<String, String> unmodifyMap = Collections.unmodifiableMap(map);		
		System.out.println(unmodifyMap);
		
		System.out.println("修改ConcurrentHashMap");
		map.replace("key1", "hello");
		System.out.println(map);
		System.out.println(unmodifyMap);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		test1();
		test2();
		test3();
	}

}
