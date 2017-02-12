package com.lhl.java7.utils;

import java.util.Collection;

/**
 * Instantiates a collection of functors.
 * 
 * @author LÊ-HEBRARD Laurent
 */
public interface BehaviorCollectionFactory {
	public Collection<Runnable> create();
}
