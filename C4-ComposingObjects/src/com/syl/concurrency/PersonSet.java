package com.syl.concurrency;

import java.util.HashSet;
import java.util.Set;

import javax.xml.ws.soap.MTOM;

/**
 * 关于实例封闭
 * 
 * Java监视器模式:
 * 将对象的所有可变状态（mySet）都封装起来，并由对象自己的内置锁来保护
 * 
 * @author shenyunlong
 *
 */
public class PersonSet {

	// private final
	private final Set<Person> mySet = new HashSet<>();
	
	public synchronized void add(Person person) {
		mySet.add(person);
	}
	
	public synchronized boolean contains(Person person) {
		return mySet.contains(person);
	}
}
