package com.syl.concurrency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;


public class ContainerExample {

	/**
	 * ArrayList 非同步, 非线程安全
	 */
	public static void MyArrayListTest() {
		ArrayList<String> arrayList = new ArrayList<>();
		
		for(int i=0; i<10; i++) {
			arrayList.add(""+i);
		}
		
		for(int i=0; i<arrayList.size(); i++) {
			System.out.println("ArrayList: "+arrayList.get(i));
		}
	}
	
	/**
	 * LinkedList 非同步, 非线程安全
	 */
	public static void MyLinkedListTest() {
		LinkedList<String> linkedList = new LinkedList<>();
		
		for(int i=0; i<10; i++) {
			linkedList.add(""+i);
		}
		
		for(int i=0; i<linkedList.size(); i++) {
			System.out.println("LinkedList: "+linkedList.get(i));
		}
	}
	
	/**
	 * Vector 同步, 线程安全
	 */
	public static void MyVectorTest() {
		Vector<String> vector = new Vector<>();
		
		for(int i=0; i<10; i++) {
			vector.add(""+i);
		}
		
		for(int i=0; i<vector.size(); i++) {
			System.out.println("Vector: "+vector.get(i));
		}
	}
	
	/**
	 * HashSet 非线程安全
	 */
	public static void MyHashSetTest() {
		HashSet<String> hashSet = new HashSet<>();
		
		hashSet.add("1");
		hashSet.add("2");
		hashSet.add("3");
		hashSet.add("1");
		hashSet.add("2");
		hashSet.add("3");
		
		for(String s : hashSet) {
			System.out.println("HashSet: "+s);
		}
	}
	
	/**
	 * HashMap 非同步
	 */
	public static void MyHashMapTest() {
		HashMap<String, String> hashMap = new HashMap<>();
		
		hashMap.put("python", "python");
		hashMap.put("go", "go");
		hashMap.put("java", "java");
		hashMap.put("c/c++", "c/c++");
		
		Iterator<Entry<String, String>> iterator = hashMap.entrySet().iterator();
		while(iterator.hasNext()) {
			Map.Entry<String, String> pair = iterator.next();
			
			System.out.println("key="+pair.getKey()+", value="+pair.getValue());
		}
	}
	
	/**
	 * HashTable 线程安全
	 */
	public static void MyHashTableTest() {
		Hashtable<String, String> hashtable = new Hashtable<>();
		
		hashtable.put("android", "android");
		hashtable.put("iphone", "iphone");
		hashtable.put("pc", "pc");
		hashtable.put("html5", "html5");
		hashtable.put("winphone", "winphone");
		
		Iterator<String> iterator = hashtable.keySet().iterator();
		while(iterator.hasNext()) {
			String key = iterator.next();
			
			System.out.println("**key="+key+", value="+hashtable.get(key));
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MyArrayListTest();
		MyLinkedListTest();
		MyVectorTest();
		MyHashSetTest();
		MyHashMapTest();
		MyHashTableTest();
	}

}
