package com.lhl.java7.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * This state machine stores a <code>Runnable</code> for some of the
 * possible states it can take. It is up to the subclasses of this
 * class to define when they will be executed.
 * 
 * @author LÊ-HEBRARD Laurent
 * @param <S> the type that defines the state of the machine.
 */
public abstract class PerStateReactingStateMachine<S> extends StateMachine<S> {
	/**
	 * The methods to run for each state.
	 */
	protected final Map<S, Runnable> behaviors;

	/**
	 * This constructor allows the user to specify the type of <code>
	 * Map</code> this state machine will use internally. It may be
	 * empty, but cannot be <code>null</code>.
	 * 
	 * @param initialState the initial state of the state machine.
	 * @param behaviorsMap a <code>Map</code> that links states to at
	 * most one behavior.
	 */
	public PerStateReactingStateMachine(S initialState, Map<S, Runnable> behaviorsMap) {
		super(initialState);
		this.behaviors = behaviorsMap;
	}
	
	/**
	 * This constructor creates a <code>PerStateReactingStateMachine
	 * </code> that uses a {@link java.util.HashMap} to store and
	 * retrieves the behaviors for each state.
	 * 
	 * @param initialState the initial state of the state machine.
	 */
	public PerStateReactingStateMachine(S initialState) {
		this(initialState, new HashMap<S, Runnable>());
	}
	
	/**
	 * Sets the behavior that will be used for the state provided as 
	 * argument to the <code>Runnable</code> provided as argument.
	 * 
	 * @param state the state that will determine when to execute the
	 * provided behavior.
	 * @param behavior the new <code>Runnable</code> executed for
	 * the provided state.
	 */
	public final void setBehaviorForState(S state, Runnable behavior) {
		this.behaviors.put(state, behavior);
	}
}
