/*
    Author: Ryan Welch
*/

package me.ryanwelch.quiz.state;

import me.ryanwelch.quiz.*;

public class StateManager
{

	private IState currentState;
	private boolean willExit = false;


	/* ***************************************
    *
    *   Constructs StateManager class
    */

	public StateManager()
	{
	}


	/* ***************************************
    *
    *   Transitions to a new state and calls onExit and onEnter respectively
    *	@param newState The next state to transition to
    */

	public void setState(IState newState)
	{
		if ( currentState != null )
		{
			currentState.onExit();
		}

		if ( newState != null )
		{
			currentState = newState;

			currentState.onEnter();
		}
		else
		{
			willExit = true;
		}
	}


	/* ***************************************
    *
    *   Returns the current state
    */

	public IState getState()
	{
		return currentState;
	}


	public boolean canExit()
	{
		return willExit;
	}

}
