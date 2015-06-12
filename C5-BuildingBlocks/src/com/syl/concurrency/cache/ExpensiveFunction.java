package com.syl.concurrency.cache;

import java.math.BigInteger;

/**
 * 实现某种耗时操作
 * 
 * @author shenyunlong
 *
 */
public class ExpensiveFunction implements Computable<String, BigInteger> {

	@Override
	public BigInteger compute(String s) {
		return new BigInteger(s);
	}
	
}
