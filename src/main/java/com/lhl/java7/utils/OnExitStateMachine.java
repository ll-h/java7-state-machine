package com.lhl.java7.utils;

import java.util.Map;

/**
 * A <code>StateMachine</code> that runs a <code>Runnable</code> when it
 * leaves its corresponding state. 
 * 
 * @author LÊ-HEBRARD Laurent
 *
 * @param <S> The type that defines the state of the machine.
 */
public class OnExitStateMachine<S> extends PerStateReactingStateMachine<S> {

	/**
	 * @param initialState the initial state of the state machine.
	 * @param behaviorsMap a <code>Map</code> that links states to at
	 * most one behavior.
	 * 
	 * @see PerStateReactingStateMachine#PerStateReactingStateMachine(Object, Map)
	 */
	public OnExitStateMachine(S initialState, Map<S, Runnable> behaviorsMap) {
		super(initialState, behaviorsMap);
	}

	/**
	 * @param initialState the initial state of the state machine.
	 * @see PerStateReactingStateMachine#PerStateReactingStateMachine(Object)
	 */
	public OnExitStateMachine(S initialState) {
		super(initialState);
	}

	/**
	 * Runs the behavior defined for the current state if the state
	 * provided as argument is a <b>different</b> state.
	 * Nothing is done otherwise or if no behavior has been defined
	 * the current state or if the one provided was null.
	 * <p>
	 * The behavior is executed <b>before</b> the current state of
	 * the machine is updated to the new one.
	 * </p> 
	 * <p>
	 * Note that the difference is tested with <code>equals</code>.
	 * </p>
	 */
	@Override
	public void setState(S state) {
		if(state != null && !state.equals(this.state)) {
			Runnable toRun = this.behaviors.get(this.state);
			if(toRun != null)
				toRun.run();
			this.state = state;
		}
	}
}
