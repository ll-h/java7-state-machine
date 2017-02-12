package com.lhl.java7.utils;

import java.util.Collection;
import java.util.LinkedList;

/**
 * A state machine that reacts to state changes by running collections of
 * functors.
 * <p>
 * It needs a factory that can instantiate such collections so that when the
 * user adds its first functor for a given state, a new collection is created
 * with that functor as first element.
 * </p>
 * 
 * @author LÊ-HEBRARD Laurent
 *
 * @param <S> the type that defines the state of the machine.
 */
public abstract class ObservableStateMachine<S> extends StateMachine<S> {
	/**
	 * The factory that can instantiates the collections of functors that will
	 * be run when the machine changes of state.
	 */
	protected BehaviorCollectionFactory collectionFactory;
	/**
	 * The default <code>BahaviorCollectionFactory</code>: it instantiates a
	 * {@link java.util.LinkedList}.
	 */
	public static final BehaviorCollectionFactory DEFAULT_BEHAVIOR_COLLECTION_FACTORY =
			new BehaviorCollectionFactory() {
		public Collection<Runnable> create() {
			return new LinkedList<Runnable>();
		}
	};

	/**
	 * Creates an observable state machine that operates with {@link
	 * java.util.LinkedList}.
	 * @param initialState the initial state of the state machine.
	 */
	public ObservableStateMachine(S initialState) {
		super(initialState);
		this.collectionFactory = DEFAULT_BEHAVIOR_COLLECTION_FACTORY;
	}
	
	/**
	 * Creates an observable state machine that operates with the factory
	 * provided as argument.
	 * @param initialState the initial state of the state machine.
	 * @param factory the factory that will be used to instantiate the
	 * collection of functors.
	 * @see #collectionFactory
	 */
	public ObservableStateMachine(S initialState, BehaviorCollectionFactory factory) {
		super(initialState);
		this.collectionFactory = factory;
	}

	/**
	 * 
	 * @return <code>this.</code>{@link #collectionFactory}
	 */
	public BehaviorCollectionFactory getCollectionFactory() {
		return collectionFactory;
	}

	/**
	 * @param collectionFactory the new value of<code>this.</code>
	 * {@link #collectionFactory}.
	 */
	public void setCollectionFactory(BehaviorCollectionFactory collectionFactory) {
		this.collectionFactory = collectionFactory;
	}
}
