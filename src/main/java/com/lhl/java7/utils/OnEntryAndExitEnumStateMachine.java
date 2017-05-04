package com.lhl.java7.utils;

import java.util.Collection;
import java.util.EnumMap;

/**
 * An <code>OnEntryAndExitStateMachine</code> that uses instances of {@link 
 * java.util.EnumMap} to store its behaviors. This class is provided as a
 * convenient way of instantiating state machines when the states are enums. 
 * 
 * @author LÊ-HEBRARD Laurent
 * @param <E> the type of the states that instances of this class can take. It
 * has to extend {@link java.lang.Enum}.
 */
public class OnEntryAndExitEnumStateMachine<E extends Enum<E>>
extends OnEntryAndExitStateMachine<E>
{
	/**
	 * @param classOfEnum argument forwarded to {@link java.util.EnumMap#EnumMap(Class)}
	 * @param initialState the initial state of this state machine.
	 * @param behaviorsFactory the factory that will be used to instantiate the
	 * collection of functors.
	 */
	public OnEntryAndExitEnumStateMachine(
			Class<E> classOfEnum,
			E initialState,
			BehaviorCollectionFactory behaviorsFactory)
	{
		super(initialState, behaviorsFactory,
				new EnumMap<E, Collection<Runnable>>(classOfEnum),
				new EnumMap<E, Collection<Runnable>>(classOfEnum));
	}

	/**
	 * @param classOfEnum argument forwarded to {@link java.util.EnumMap#EnumMap(Class)}
	 * @param initialState the initial state of this state machine.
	 */
	public OnEntryAndExitEnumStateMachine(Class<E> classOfEnum, E initialState)	{
		this(classOfEnum,
			 initialState,
			 OnEntryAndExitStateMachine.DEFAULT_BEHAVIOR_COLLECTION_FACTORY);
	}
}
