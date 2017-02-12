package com.lhl.java7.utils;

import java.util.Map;

/**
 * A <code>StateMachine</code> that runs a <code>Runnable</code> when it
 * enters its corresponding state. 
 * 
 * @author LÊ-HEBRARD Laurent
 *
 * @param <S> the type that defines the state of the machine.
 */
public class OnEntryStateMachine<S> extends PerStateReactingStateMachine<S> {

	/**
	 * @param initialState the initial state of the state machine.
	 * @param behaviorsMap a <code>Map</code> that links states to at
	 * most one behavior.
	 * @see PerStateReactingStateMachine#PerStateReactingStateMachine(Object)
	 */
	public OnEntryStateMachine(S initialState, Map<S, Runnable> behaviorsMap) {
		super(initialState, behaviorsMap);
	}

	/**
	 * @param initialState the initial state of the state machine.
	 * @see PerStateReactingStateMachine#PerStateReactingStateMachine(Object)
	 */
	public OnEntryStateMachine(S initialState) {
		super(initialState);
	}

	/**
	 * Runs the behavior defined for the state provided as argument
	 * if it is a <b>different</b> state than the current one.
	 * Nothing is done otherwise or if no behavior has been defined
	 * this state or if the one provided was null.
	 * <p>
	 * The behavior is executed <b>after</b> the current state of
	 * the machine is updated to the new one.
	 * </p> 
	 * <p>
	 * Note that the difference is tested with <code>equals</code>.
	 * </p>
	 */
	@Override
	public void setState(S state) {
		if(state != null && !state.equals(this.state)) {
			this.state = state;
			Runnable toRun = this.behaviors.get(state);
			if(toRun != null)
				toRun.run();
		}
	}
}
