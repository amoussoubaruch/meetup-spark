package com.capgemini.analytics.samples.singleton;

import org.apache.log4j.Logger;
import java.lang.management.ManagementFactory;

public class ThreadLocalSimpleClassHolder {

    private static final ThreadLocal<SimpleClassHolder> HOLDER = new ThreadLocal<SimpleClassHolder>() {
		@Override
		protected SimpleClassHolder initialValue() {
			Logger logger_ = Logger.getLogger(ThreadLocalSimpleClassHolder.class.getName());
			String vmid_ = ManagementFactory.getRuntimeMXBean().getName();
			logger_.info(String.format("initialValue() SimpleClassHolder - [%s] on %s", Thread.currentThread().getName(), vmid_));
			return new SimpleClassHolder();
		}
    };

    // Returns the current thread's unique ID, assigning it if necessary
    public static SimpleClassHolder get() {
	return HOLDER.get();
    }
    
}