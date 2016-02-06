/*
    Author: Ryan Welch
*/

package me.ryanwelch.quiz.state;

import me.ryanwelch.quiz.*;
import me.ryanwelch.quiz.graphics.*;
import me.ryanwelch.quiz.util.*;
import java.awt.*;

public class MenuState implements IState
{

	private Quiz quizInstance;

	private String status;
    private long lastStatusUpdate;

    private TextButton playNowButton;
    private TextButton loadButton;

	public MenuState(Quiz quizInstance)
	{
		this.quizInstance = quizInstance;
	}

    public void onEnter()
    {
    	playNowButton = new TextButton("Play Now");
        loadButton = new TextButton("Load Game");

        playNowButton.add(this.quizInstance.display);
        loadButton.add(this.quizInstance.display);
    }

    public void onExit()
    {
    	playNowButton.remove(this.quizInstance.display);
        loadButton.remove(this.quizInstance.display);
    }


    /* ***************************************
    *
    *   Implements IState, draws the current state
    *   @param display The display instance
    *   @param g The graphics instance
    */

    public void draw(Display display, Graphics g)
    {
        display.paintText(g, "Quiz", 260);

        if ( playNowButton != null )
        {
            playNowButton.setPosition(display.width / 2, 400, -1 * (playNowButton.getWidth() / 2), 0);
        }

        if ( loadButton != null )
        {
            loadButton.setPosition(display.width / 2, 460, -1 * (loadButton.getWidth() / 2), 0);
        }
    }


    /* ***************************************
    *
    *   Implements IState, updates the current state
    */

    public void update()
    {
        if ( playNowButton.isClicked() )
        {
            quizInstance.stateManager.setState(new GameState(quizInstance, "test.dat", false));
        }

        if ( loadButton.isClicked() )
        {
            quizInstance.stateManager.setState(new GameState(quizInstance, "test.dat", true));
        }
    }

}
