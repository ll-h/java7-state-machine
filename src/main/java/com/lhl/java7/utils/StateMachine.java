package com.lhl.java7.utils;

import javax.naming.spi.StateFactory;

/**
 * This class provide an abstraction for a state machine.
 * It takes as parameter a type that defines the state
 * of the machine.
 * 
 * @author LÊ-HEBRARD Laurent
 *
 * @param <S> the type that defines the state of the machine.
 */
public abstract class StateMachine<S> {
	/**
	 * The current state of this <code>StateMachine</code>.
	 */
	protected S state;

	/**
	 * Creates a state machine with an initial state.
	 * @param initialState the initial state of the state machine.
	 */
	public StateMachine(S initialState) {
		this.state = initialState;
	}

	/**
	 * @return the current state of this <code>StateMachine</code>.
	 */
	public S getState() {
		return state;
	}

	/**
	 * Change the state of this <code>StateMachine</code> to the
	 * state provided as argument.
	 * <p>
	 * This method is meant to trigger behaviors defined by subclasses
	 * of <code>StateMachine</code>.
	 * </p>
	 * @param state The new state that the machine will take.
	 */
	public abstract void setState(S state);

	/**
	 * Checks whether <code>this</code> state machine is in the state provided
	 * as argument.
	 * <p>
	 * This implementation simply tests its equality with the state returned
	 * by {@link #getState()}. Subclasses may override this method to define
	 * more subtle relationships between states such as inheritance.
	 * </p>
	 * 
	 * @param state the state tested against the value returned by <code>
	 * getState()</code>.
	 * @return <code>true</code> if this state machine is in the state provided
	 * as argument.
	 */
	public boolean isInState(S state) {
		S stateOfThis = getState();
		if(stateOfThis == null)
			return state == null;
		else
			return stateOfThis.equals(state);
	}
}
