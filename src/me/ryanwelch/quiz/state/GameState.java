/*
    Author: Ryan Welch
*/

package me.ryanwelch.quiz.state;

import me.ryanwelch.quiz.*;
import me.ryanwelch.quiz.graphics.*;
import me.ryanwelch.quiz.util.*;
import java.awt.*;
import java.io.*;

public class GameState implements IState
{

	private Quiz quizInstance;
	private Board board;

	private final String[] questions = {"What is the best city?", "When did WWII end?", "What uni are we at?", "Which metal is heavier, silver or gold?", "How many legs do butterflies have?", "Which is the country with the most people?", "Which state is the biggest in the US?"};
	private final String[] answers = {"London", "1945", "QMUL", "Gold", "Six", "China", "Alaska"};

	private int questionIndex;
	private String question;

	private String status;
    private long lastStatusUpdate;

    private TextButton saveQuitButton;
	private TextBox inputBox;

    private String saveFile;
    private boolean shouldLoad = false;

	public GameState(Quiz quizInstance, String saveFile, boolean shouldLoad)
	{
		this.quizInstance = quizInstance;
		this.board = new Board();

        this.saveFile = saveFile;
        this.shouldLoad = shouldLoad;
	}

    public void onEnter()
    {
        // TODO: get num players from previous state
        for ( int i = 1; i <= 4; i++)
        {
        	board.addPlayer(new Player(i));
        }

        if ( saveFile != null && shouldLoad )
        {
            load(saveFile);
        }

        saveQuitButton = new TextButton("Save and Quit");
        saveQuitButton.add(this.quizInstance.display);

    	inputBox = new TextBox(20);
    	inputBox.add(this.quizInstance.display);
    }

    public void onExit()
    {
        if ( saveFile != null )
        {
            save(saveFile);
        }

    	board.clearPlayers();

        saveQuitButton.remove(this.quizInstance.display);

    	inputBox.remove(this.quizInstance.display);
    }


    /* ***************************************
    *
    *   Roll a virtual dice and return the a number between 1 and 6
    * 	@return num The number rolled on the dice
    */

    public int rollDice()
    {
    	return (int) (Math.random() * 5) + 1;
    }


    /* ***************************************
    *
    *   Show question to the user
    *	@return currentIndex The index of the current question
    */

    public int generateQuestion()
    {
    	int randomIndex;

        do
        {
            randomIndex = (int) Math.round( Math.random() * (questions.length - 1) );
        }
        while (randomIndex == questionIndex); // Guarantee the same question wont be repeated immediately after

    	return randomIndex;
    }


    /* ***************************************
    *
    *   Check question
    *	@param questionIndex The index of the question
    *	@param userAnswer The user input to check
    *	@return correct Returns true if the answer is correct
    */

    public boolean checkQuestion(int questionIndex, String userAnswer)
    {
    	String correctAnswer = answers[questionIndex];

    	if (userAnswer.equalsIgnoreCase(correctAnswer))
    	{
    		return true;
    	}

    	return false;
    }


    public boolean save(String saveFile)
    {
        String json = board.serialize();

        PrintWriter out;

        // Attempt to get file handle
        try
        {
            out = new PrintWriter(new FileWriter(saveFile));
        }
        catch(IOException e)
        {
            return false;
        }

        // Wirte out
        out.println(json);
        out.close();

        return true;
    }

    public boolean load(String saveFile)
    {
        String json;

        StringBuilder res = new StringBuilder();
        String temp = "";

        BufferedReader in;

        // Attempt to load file
        try
        {
            in = new BufferedReader(new FileReader(saveFile));

        }
        catch(FileNotFoundException e)
        {
            return false;
        }

        // Attempt to read file
        try
        {
            while ((temp = in.readLine()) != null)
            {
                res.append(temp);
            }

        }
        catch(IOException e)
        {
            return false;
        }

        json = res.toString();

        // If we managed to read something unserialize, else exit
        if ( json != null && json != "" )
        {
            board.unserialize(json);
            return true;
        }
        else
        {
            return false;
        }
    }


    /* ***************************************
    *
    *   Implements IState, draws the current state
    *   @param display The display instance
    *   @param g The graphics instance
    */

    public void draw(Display display, Graphics g)
    {

        board.draw(display, g);

    	if ( !board.hasFinished() && question != null && status == null )
    	{
    		display.paintText(g, question, 60);
    	}

    	if ( status != null )
    	{
    		display.paintText(g, status, 60); // 420
    	}

        if ( !board.hasFinished() && inputBox != null )
        {
            inputBox.setPosition(display.width / 2, 75, -1 * (inputBox.getWidth() / 2), 0);
        }

        if ( saveQuitButton != null )
        {
            saveQuitButton.setPosition(display.width / 2, 580, -1 * (saveQuitButton.getWidth() / 2), 0);
        }

    }


    /* ***************************************
    *
    *   Implements IState, updates the current state
    */

    public void update()
    {
    	if ( question == null )
    	{
            // Generate a new question on first run
    		questionIndex = generateQuestion();
    		question = questions[questionIndex];
    	}

        // Dissapear out old status after 1000 ms
        if ( lastStatusUpdate < System.currentTimeMillis() - 1000 )
        {
            status = null;
        }

    	if ( !board.hasFinished() && inputBox.isSubmitted() )
    	{
            // If the user has entered an answer

    		if( checkQuestion(questionIndex, inputBox.getText()) )
	    	{
	    		// If the user answered correctly
	    		int dice = rollDice();

                Player player = board.getCurrentPlayer();

                Vector newPosition = player.getPosition();
                Vector tempPosition;
                int moves = dice;
                boolean hasWon = false;
                while (moves-- != 0)
                {
                    tempPosition = board.nextPosition(newPosition);
                    if(tempPosition == null)
                    {
                        // Player has won
                        hasWon = true;
                        board.setFinished(true);
                        break;
                    } else {
                        newPosition = tempPosition;
                    }
                }

                player.setPosition(newPosition);

                player.addToScore(dice); // TODO: Make the score the distance to the end

                if(!hasWon) {
                    // Set the status to notify the user
                    status = "Well done, move " + dice + " " + (dice == 1 ? "space" : "spaces") + "!";
                    lastStatusUpdate = System.currentTimeMillis();
	    	    } else {
                    status = "Player " + player.getId() + " has won!";
                    lastStatusUpdate = System.currentTimeMillis();
                }
            }
			else
	    	{
	    		// Else the user answered incorrectly

                // Set the status to notify the user
	    		status = "Sorry, the answer was " + answers[questionIndex];
                lastStatusUpdate = System.currentTimeMillis();
	    	}

            // Reset the input box
	    	inputBox.reset();

            // Generate a new question to display
	    	questionIndex = generateQuestion();
    		question = questions[questionIndex];

            // Update turn to next player
	    	board.nextTurn();
    	}

        if ( saveQuitButton.isClicked() )
        {
            quizInstance.stateManager.setState(null);
        }
    }

}
