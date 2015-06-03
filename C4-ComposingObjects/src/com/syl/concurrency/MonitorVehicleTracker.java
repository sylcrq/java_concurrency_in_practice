package com.syl.concurrency;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 一个线程安全的车辆追踪类
 * 
 * Java监视器模式
 * @author shenyunlong
 *
 */
public class MonitorVehicleTracker {

	private final Map<String, MutablePoint> locations;
	
	public MonitorVehicleTracker(Map<String, MutablePoint> locations) {
		this.locations = deepCopy(locations);
	}
	
	/**
	 * 获取所有车辆的位置信息
	 * @return
	 */
	public synchronized Map<String, MutablePoint> getLocations() {
		return deepCopy(locations);
	}
	
	/**
	 * 获取指定车辆的位置信息
	 * @param id
	 * @return
	 */
	public synchronized MutablePoint getLocationById(String id) {
		// 注意Map中不存在id的情况！
		MutablePoint point = locations.get(id);
		
		return (point == null) ? null : new MutablePoint(point);
	}
	
	/**
	 * 更新指定车辆的位置信息
	 * @param id
	 * @param point
	 */
	public synchronized void setLocationById(String id, MutablePoint point) {
		// 注意Map中不存在id的情况！
		if(locations.get(id) == null) {
			//return;
			throw new IllegalArgumentException("No such ID="+id);
		}
		
		locations.put(id, new MutablePoint(point));
	}
	
	private static Map<String, MutablePoint> deepCopy(Map<String, MutablePoint> map) {
		Map<String, MutablePoint> copy = new HashMap<String, MutablePoint>();
		
//		Iterator<Entry<String, MutablePoint>> iterator = map.entrySet().iterator();		
//		while(iterator.hasNext()) {
//			Entry<String, MutablePoint> entry = iterator.next();
//			
//			final String key = entry.getKey();
//			final MutablePoint value = entry.getValue();
//			
//			copy.put(key, new MutablePoint(value));
//		}
		
		for(String id : map.keySet()) {
			copy.put(id, new MutablePoint(map.get(id)));
		}
		
		//注意！
		return Collections.unmodifiableMap(copy);
	}
}
