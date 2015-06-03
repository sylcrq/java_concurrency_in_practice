package com.syl.concurrency;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 一个线程安全的车辆追踪类
 * 
 * 将线程安全性委托给底层变量
 * 
 * @author shenyunlong
 *
 */
public class DelegatingVehicleTracker {
	
	private final ConcurrentHashMap<String, Point> locations;
	private final Map<String, Point> unmodifiableMap;
	
	public DelegatingVehicleTracker(Map<String, Point> points) {
		this.locations = new ConcurrentHashMap<String, Point>(points);
		this.unmodifiableMap = Collections.unmodifiableMap(locations);
	}
	
	public Map<String, Point> getLocations() {
		return unmodifiableMap;
	}
	
	public Point getLocationById(String id) {		
		return locations.get(id);
	}
	
	public void setLocationById(String id, int x, int y) {
		if(locations.replace(id, new Point(x, y)) == null) {
			throw new IllegalArgumentException("invalid vehicle name: "+id);
		}
	}
}
