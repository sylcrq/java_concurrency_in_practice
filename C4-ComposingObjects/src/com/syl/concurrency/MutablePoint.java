package com.syl.concurrency;

public class MutablePoint {

	private int x;
	private int y;
	
	public MutablePoint() {
		this.x = 0;
		this.y = 0;
	}
	
	public MutablePoint(MutablePoint point) {
		this.x = point.x;
		this.y = point.y;
	}
	
}
