package com.lhl.java7.utils;

import java.util.EnumMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link StateMachine} and its subclasses.
 */
public class StateMachineTests
{
	private AtomicInteger witness = new AtomicInteger(0);
	private AtomicInteger witness2 = new AtomicInteger(0);

	private static class ChangeWitnessToConstant implements Runnable {
		private final int constant;
		private final AtomicInteger witness;

		public ChangeWitnessToConstant(AtomicInteger witness, int constant) {
			this.constant = constant;
			this.witness = witness;
		}

		public void run() {
			witness.set(constant);
		}
	}

	private StateMachine<TestStates> constructPerStateBehaviorEnumStateMachine(
			PerStateReactingStateMachine<TestStates> newMachine)
	{
		newMachine.setBehaviorForState(TestStates.State1, new ChangeWitnessToConstant(witness, 1));
		newMachine.setBehaviorForState(TestStates.State2, new ChangeWitnessToConstant(witness, 2));
		newMachine.setBehaviorForState(TestStates.State3, new ChangeWitnessToConstant(witness, 3));
		// State4 intentionally not set

		return newMachine;
	}

	private static enum TestStates {
		State1, State2, State3, State4
	}

	/**
	 * Tests the {@link OnEntryStateMachine} class with an <code>enum</code>
	 * as possible states.
	 */
	@Test
	public void testOnEntryStateMachineWithEnums()
	{
		StateMachine<TestStates> sm = constructPerStateBehaviorEnumStateMachine(
				new OnEntryStateMachine<TestStates>(
						TestStates.State1,
						new EnumMap<TestStates, Runnable>(TestStates.class)
						)
				);

		sm.setState(TestStates.State2);
		Assert.assertTrue(witness.get() == 2);
		Assert.assertTrue(TestStates.State2.equals(sm.getState()));

		sm.setState(TestStates.State3);
		Assert.assertTrue(witness.get() == 3);
		Assert.assertTrue(TestStates.State3.equals(sm.getState()));

		sm.setState(TestStates.State1);
		Assert.assertTrue(witness.get() == 1);
		Assert.assertTrue(TestStates.State1.equals(sm.getState()));

		// No changes in the witness whereas the state did change.
		sm.setState(TestStates.State4);
		Assert.assertTrue(witness.get() == 1);
		Assert.assertTrue(TestStates.State4.equals(sm.getState()));
	}

	/**
	 * Tests the {@link OnExitStateMachine} class with an <code>enum</code>
	 * as possible states.
	 */
	@Test
	public void testOnExitStateMachineWithEnums()
	{
		StateMachine<TestStates> sm = constructPerStateBehaviorEnumStateMachine(
				new OnExitStateMachine<TestStates>(
						TestStates.State1,
						new EnumMap<TestStates, Runnable>(TestStates.class)
						)
				);

		sm.setState(TestStates.State2);
		Assert.assertTrue(witness.get() == 1);
		Assert.assertTrue(TestStates.State2.equals(sm.getState()));

		sm.setState(TestStates.State3);
		Assert.assertTrue(witness.get() == 2);
		Assert.assertTrue(TestStates.State3.equals(sm.getState()));

		sm.setState(TestStates.State4);
		Assert.assertTrue(witness.get() == 3);
		Assert.assertTrue(TestStates.State4.equals(sm.getState()));

		// No changes in the witness whereas the state did change.
		sm.setState(TestStates.State1);
		Assert.assertTrue(witness.get() == 3);
		Assert.assertTrue(TestStates.State1.equals(sm.getState()));
	}

	// Infinite state machine
	private StateMachine<Integer> constructPerStateBehaviorInfiniteStateMachine(
			PerStateReactingStateMachine<Integer> newMachine)
	{
		newMachine.setBehaviorForState(new Integer(2), new ChangeWitnessToConstant(witness, 2));
		newMachine.setBehaviorForState(new Integer(3), new ChangeWitnessToConstant(witness, 3));
		newMachine.setBehaviorForState(new Integer(5), new ChangeWitnessToConstant(witness, 5));

		return newMachine;
	}

	/**
	 * A constant with no special meaning.
	 */
	private final int UNSET_INTEGER_STATE = -489456;
	/**
	 * Tests the {@link OnExitStateMachine} class with an <code>Integer</code>
	 * as possible states.
	 */
	@Test
	public void testOnExitInfiniteStateMachine()
	{
		StateMachine<Integer> sm = constructPerStateBehaviorInfiniteStateMachine(
				new OnExitStateMachine<Integer>(new Integer(2)));

		sm.setState(3);
		Assert.assertTrue(witness.get() == 2);
		Assert.assertTrue(3 == sm.getState());

		sm.setState(5);
		Assert.assertTrue(witness.get() == 3);
		Assert.assertTrue(5 == sm.getState());

		sm.setState(UNSET_INTEGER_STATE);
		Assert.assertTrue(witness.get() == 5);
		Assert.assertTrue(UNSET_INTEGER_STATE == sm.getState());

		// No changes in the witness whereas the state did change.
		sm.setState(2);
		Assert.assertTrue(witness.get() == 5);
		Assert.assertTrue(2 == sm.getState());
	}

	/**
	 * Tests the {@link OnEntryStateMachine} class with an <code>Integer</code>
	 * as possible states.
	 */
	@Test
	public void testOnEntryInfiniteStateMachine()
	{
		StateMachine<Integer> sm = constructPerStateBehaviorInfiniteStateMachine(
				new OnEntryStateMachine<Integer>(new Integer(2)));

		sm.setState(3);
		Assert.assertTrue(witness.get() == 3);
		Assert.assertTrue(3 == sm.getState());

		sm.setState(5);
		Assert.assertTrue(witness.get() == 5);
		Assert.assertTrue(5 == sm.getState());

		sm.setState(2);
		Assert.assertTrue(witness.get() == 2);
		Assert.assertTrue(2 == sm.getState());

		// No changes in the witness whereas the state did change.
		sm.setState(UNSET_INTEGER_STATE);
		Assert.assertTrue(witness.get() == 2);
		Assert.assertTrue(UNSET_INTEGER_STATE == sm.getState());
	}


	private StateMachine<Integer> constructEntryAndExitBehavioredInfiniteStateMachine(
			OnEntryAndExitStateMachine<Integer> newMachine)
	{
		newMachine.addEntryBehavior(new Integer(2), new ChangeWitnessToConstant(witness, 2));
		newMachine.addEntryBehavior(new Integer(3), new ChangeWitnessToConstant(witness, 3));
		newMachine.addEntryBehavior(new Integer(5), new ChangeWitnessToConstant(witness, 5));
		
		newMachine.addExitBehavior(new Integer(2), new ChangeWitnessToConstant(witness2, -2));
		newMachine.addExitBehavior(new Integer(3), new ChangeWitnessToConstant(witness2, -3));
		newMachine.addExitBehavior(new Integer(5), new ChangeWitnessToConstant(witness2, -5));

		return newMachine;
	}

	/**
	 * Tests the {@link OnEntryAndExitStateMachine} class with an <code>
	 * Integer</code> as possible states.
	 */
	@Test
	public void testOnEntryAndExitInfiniteStateMachine()
	{
		StateMachine<Integer> sm = constructEntryAndExitBehavioredInfiniteStateMachine(
				new OnEntryAndExitStateMachine<Integer>(new Integer(2)));

		sm.setState(3);
		Assert.assertTrue(witness2.get() == -2);
		Assert.assertTrue(witness.get() == 3);
		Assert.assertTrue(3 == sm.getState());

		sm.setState(5);
		Assert.assertTrue(witness2.get() == -3);
		Assert.assertTrue(witness.get() == 5);
		Assert.assertTrue(5 == sm.getState());

		sm.setState(2);
		Assert.assertTrue(witness2.get() == -5);
		Assert.assertTrue(witness.get() == 2);
		Assert.assertTrue(2 == sm.getState());

		// No changes in the witness of entry whereas the state did change.
		sm.setState(UNSET_INTEGER_STATE);
		Assert.assertTrue(witness2.get() == -2);
		Assert.assertTrue(witness.get() == 2);
		Assert.assertTrue(UNSET_INTEGER_STATE == sm.getState());
	}
}
