/*
	Author: Ryan Welch
*/

package me.ryanwelch.quiz.graphics;

import me.ryanwelch.quiz.*;
import me.ryanwelch.quiz.state.*;
import javax.swing.*;
import java.awt.*;

public class Display extends JPanel
{

	private Quiz quizInstance;
	private JFrame application;

	public int width;
	public int height;

	public Font font = new Font("Serif", Font.PLAIN, 36);


	/* ***************************************
	*
	*   Constructor
	*/

	public Display(Quiz quizInstance, int width, int height)
	{
		super();

		this.quizInstance = quizInstance;

		this.application = new JFrame();

		application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		application.add(this);

		application.setResizable(false);

		// TODO: Add components

		this.width = width;
		this.height = height;

		application.setSize(width, height);
		application.setVisible(true);
	}


	/* ***************************************
	*
	*   Overides JPanel paintComponent
	*	@param g The graphics object
	*/

	public void paintComponent(Graphics g)
	{
		// Call overidden method
		super.paintComponent(g);

		IState state = quizInstance.stateManager.getState();
		if ( state != null )
		{
			state.draw(this, g);
		}
	}


	/* ***************************************
	*
	*   Helper method to draw text
	*	@param g The graphics object
	* 	@param text Text to draw to graphics
	*	@param x x-coordinate of the text
	* 	@param y y-coordinate of the text
	*/

	public void paintText(Graphics g, String text, int x, int y)
	{
		Graphics2D g2 = (Graphics2D) g;

		// Setup fonts and rendering options
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setFont(font);

		// Draw string to graphics
		g2.drawString(text, x, y);
	}


	/* ***************************************
	*
	*   Helper method to draw centered text
	*	@param g The graphics object
	* 	@param text Text to draw to graphics
	*/
	public void paintText(Graphics g, String text, int y)
	{
		Graphics2D g2 = (Graphics2D) g;

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setFont(font);

		FontMetrics fontMetrics = g.getFontMetrics();
		int x = (width / 2) - (fontMetrics.stringWidth(text) / 2);

		g2.drawString(text, x, y);
	}

	public void quit()
	{
		application.dispose();
	}


} // END class Quiz
