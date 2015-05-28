package com.syl.concurrency;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Foo {

	private static final ThreadLocal<SimpleDateFormat> formatter = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyyMMdd HHmm");
		}
	};

	public String format(Date date) {
		return formatter.get().format(date);
	}
}
