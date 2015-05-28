package com.syl.concurrency;

/**
 * 这是一个immutable class
 * @author shenyunlong
 *
 */
public class ImmutableExample {

	private final String name;
	
	public ImmutableExample(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
