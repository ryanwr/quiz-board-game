/*
    Author: Ryan Welch
    Description: Quiz Board Game
*/

package me.ryanwelch.quiz;

import me.ryanwelch.quiz.graphics.*;
import me.ryanwelch.quiz.state.*;
import me.ryanwelch.quiz.*;
import javax.swing.*;
import java.awt.*;

public class Quiz
{
    public StateManager stateManager;
    public Display display;

    // Width and height of the screen
    public final int WIDTH = 1080;
    public final int HEIGHT = 720;


    /* ***************************************
    *
    *   Entry point main
    *   @param args Command line arguments
    */

    public static void main(String[] args)
    {
    	Quiz quiz = new Quiz();

        quiz.start();
    }


    /* ***************************************
    *
    *   Constructs Quiz class
    */

    public Quiz()
    {
        display = new Display(this, WIDTH, HEIGHT);

        stateManager = new StateManager();
    }


    /* ***************************************
    *
    *   Main game loop
    */

	public void start()
    {
        // Set the state to a new GameState
        stateManager.setState(new MenuState(this));

		// Loop indefinitely
    	while(true)
    	{
            if (stateManager.canExit())
            {
                display.quit();
                break;
            }

            // Get the state
            IState state = stateManager.getState();

            if ( state != null )
            {
                // Update the state
                state.update();
            } else {
                break;
            }

            // Causes screen redraw
            display.invalidate();
            display.repaint();

            try
            {
                // Update at aprox. 30fps
                Thread.sleep(1000/30);
            }
            catch(InterruptedException e)
            {
                break;
            }
    	}
    }


}
