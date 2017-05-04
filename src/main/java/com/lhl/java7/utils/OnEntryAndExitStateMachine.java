package com.lhl.java7.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * A <code>StateMachine</code> that runs a <code>Runnable</code> when it
 * enters and leaves its corresponding state. 
 * 
 * @author LÊ-HEBRARD Laurent
 *
 * @param <S> The type that defines the state of the machine.
 */
public class OnEntryAndExitStateMachine<S> extends ObservableStateMachine<S> {
	/**
	 * The <code>Map</code> that links states to the behaviors run when this
	 * enters their corresponding state.
	 */
	protected Map<S, Collection<Runnable>> entryBehaviorsMap;
	/**
	 * The <code>Map</code> that links states to the behaviors run when this
	 * leaves their corresponding state.
	 */
	protected Map<S, Collection<Runnable>> exitBehaviorsMap;

	/**
	 * @param initialState the initial state of the state machine.
	 * @param entryBehaviorsMap a <code>Map</code> that links states to
	 * the behaviors run when this enters their corresponding state.
	 * @param exitBehaviorsMap a <code>Map</code> that links states to
	 * the behaviors run when this leaves their corresponding state.
	 * 
	 * @see PerStateReactingStateMachine#PerStateReactingStateMachine(Object, Map)
	 * @see ObservableStateMachine#ObservableStateMachine(Object, BehaviorCollectionFactory)
	 */
	public OnEntryAndExitStateMachine(
			S initialState,
			BehaviorCollectionFactory behaviorsFactory,
			Map<S, Collection<Runnable>> entryBehaviorsMap,
			Map<S, Collection<Runnable>> exitBehaviorsMap)
	{
		super(initialState, behaviorsFactory);
	}

	/**
	 * @param initialState the initial state of the state machine.
	 * @see PerStateReactingStateMachine#PerStateReactingStateMachine(Object)
	 * @see ObservableStateMachine#ObservableStateMachine(Object)
	 */
	public OnEntryAndExitStateMachine(S initialState) {
		super(initialState);
		this.entryBehaviorsMap = new HashMap<S, Collection<Runnable>>();
		this.exitBehaviorsMap = new HashMap<S, Collection<Runnable>>();
	}

	@Override
	public void setState(S state) {
		if(state != null && !state.equals(this.state)) {
			// Trigger exit behaviors
			Collection<Runnable> toRun = this.exitBehaviorsMap.get(this.state);
			if(toRun != null)
				for(Runnable functor : toRun)
					functor.run();

			// Assign the new state
			this.state = state;

			// Trigger entry behaviors
			toRun = this.entryBehaviorsMap.get(this.state);
			if(toRun != null)
				for(Runnable functor : toRun)
					functor.run();
		}
	}

	/**
	 * Adds a behavior for the given state on the given <code>Map</code>.
	 * @param state the state for which the given behavior will be run.
	 * @param behaviorType the <code>Map</code> in which to store the given
	 * behavior.
	 * @param behavior the functor that will be run when this state machine
	 * changes of state to the given state.
	 */
	protected void addBehavior(S state, Map<S, Collection<Runnable>> behaviorType, Runnable behavior) {
		Collection<Runnable> behaviors = behaviorType.get(state);
		if(behaviors == null) {
			behaviors = this.collectionFactory.create();
			behaviorType.put(state, behaviors);
		}

		behaviors.add(behavior);
	}

	/**
	 * Adds a functor that will be run when this state machine enters the state
	 * provided as argument.
	 * 
	 * @param state the state for which the given behavior will be run.
	 * @param behavior the functor that will be run when this state machine
	 * enters the given state.
	 */
	public void addEntryBehavior(S state, Runnable behavior) {
		addBehavior(state, this.entryBehaviorsMap, behavior);
	}

	/**
	 * Adds a functor that will be run when this state machine leaves the state
	 * provided as argument.
	 * 
	 * @param state the state for which the given behavior will be run.
	 * @param behavior the functor that will be run when this state machine
	 * leaves the given state.
	 */
	public void addExitBehavior(S state, Runnable behavior) {
		addBehavior(state, this.exitBehaviorsMap, behavior);
	}
}
