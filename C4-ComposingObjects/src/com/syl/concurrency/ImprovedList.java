package com.syl.concurrency;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 * 在现有的线程安全类中添加功能
 * 组合(Composition)
 * 
 * @author shenyunlong
 *
 * @param <E>
 */
public class ImprovedList<E> implements List<E>{

	private final List<E> list;
	
	public ImprovedList(List<E> list) {
		this.list = list;
	}
	
	/**
	 * 新增的功能
	 * @param x
	 */
	public synchronized boolean putIfAbsent(E x) {
//		if(!list.contains(x)) {
//			list.add(x);
//			return true;
//		}
//		
//		return false;
		
		boolean contains = list.contains(x);
		
		if(!contains) {
			list.add(x);
		}
		
		return !contains;
	}
	
	/**
	 * 委托List的其他方法(+同步)
	 */
	@Override
	public synchronized Stream<E> parallelStream() {
		return list.parallelStream();
	}

	@Override
	public synchronized boolean removeIf(Predicate<? super E> arg0) {
		return list.removeIf(arg0);
	}

	@Override
	public synchronized Stream<E> stream() {
		return list.stream();
	}

	@Override
	public synchronized void forEach(Consumer<? super E> arg0) {
		list.forEach(arg0);
	}

	@Override
	public synchronized boolean add(E arg0) {
		return list.add(arg0);
	}

	@Override
	public synchronized void add(int arg0, E arg1) {
		list.add(arg0, arg1);
	}

	@Override
	public synchronized boolean addAll(Collection<? extends E> arg0) {
		return list.addAll(arg0);
	}

	@Override
	public synchronized boolean addAll(int arg0, Collection<? extends E> arg1) {
		return list.addAll(arg0, arg1);
	}

	@Override
	public synchronized void clear() {
		list.clear();
	}

	@Override
	public synchronized boolean contains(Object arg0) {
		return list.contains(arg0);
	}

	@Override
	public synchronized boolean containsAll(Collection<?> arg0) {
		return list.containsAll(arg0);
	}

	@Override
	public synchronized E get(int arg0) {
		return list.get(arg0);
	}

	@Override
	public synchronized int indexOf(Object arg0) {
		return list.indexOf(arg0);
	}

	@Override
	public synchronized boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public synchronized Iterator<E> iterator() {
		return list.iterator();
	}

	@Override
	public synchronized int lastIndexOf(Object arg0) {
		return list.lastIndexOf(arg0);
	}

	@Override
	public synchronized ListIterator<E> listIterator() {
		return list.listIterator();
	}

	@Override
	public synchronized ListIterator<E> listIterator(int arg0) {
		return list.listIterator(arg0);
	}

	@Override
	public synchronized boolean remove(Object arg0) {
		return list.remove(arg0);
	}

	@Override
	public synchronized E remove(int arg0) {
		return list.remove(arg0);
	}

	@Override
	public synchronized boolean removeAll(Collection<?> arg0) {
		return list.removeAll(arg0);
	}

	@Override
	public synchronized void replaceAll(UnaryOperator<E> arg0) {
		list.replaceAll(arg0);
	}

	@Override
	public synchronized boolean retainAll(Collection<?> arg0) {
		return list.retainAll(arg0);
	}

	@Override
	public synchronized E set(int arg0, E arg1) {
		return list.set(arg0, arg1);
	}

	@Override
	public synchronized int size() {
		return list.size();
	}

	@Override
	public synchronized void sort(Comparator<? super E> arg0) {
		list.sort(arg0);
	}

	@Override
	public synchronized Spliterator<E> spliterator() {
		return list.spliterator();
	}

	@Override
	public synchronized List<E> subList(int arg0, int arg1) {
		return list.subList(arg0, arg1);
	}

	@Override
	public synchronized Object[] toArray() {
		return list.toArray();
	}

	@Override
	public synchronized <T> T[] toArray(T[] arg0) {
		return list.toArray(arg0);
	}
	
}
