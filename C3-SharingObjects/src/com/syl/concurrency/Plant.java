package com.syl.concurrency;

import java.util.Date;



/**
 * 一个Immutable对象的例子
 * final class
 * @author shenyunlong
 *
 * http://www.javapractices.com/topic/TopicAction.do?Id=29
 */
public final class Plant {

	// private final 数据成员
	private final double mass;
	private final String name;
	private final Date date;
	
	
	public Plant(double mass, String name, Date date) {
		this.mass = mass;
		this.name = name;
		// 对传入的date进行copy
		this.date = new Date(date.getTime());
	}
	
	/**
	 * 返回基本数据类型
	 * @return
	 */
	public double getMass() {
		return mass;
	}
	
	/**
	 * 返回immutable对象(String)
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 不能直接返回date,因为Date是mutable对象,date可能会被调用者修改
	 * 
	 * 返回mutable对象的defensive copy
	 * @return
	 */
	public Date getDate() {
		return new Date(date.getTime());
	}
}
