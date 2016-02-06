/*
	Author: Ryan Welch
*/

package me.ryanwelch.quiz.graphics;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class TextButton implements ActionListener
{

	private JButton button;

	private int x;
	private int y;

	private boolean isClicked = false;

	public TextButton(String value)
	{
		button = new JButton(value);

        Border border = button.getBorder();
        Border padding = new EmptyBorder(5, 5, 5, 5);
        button.setBorder(new CompoundBorder(border, padding));

		button.addActionListener( this );
		button.setFont(new Font("Serif", Font.PLAIN, 20));
		button.setFocusPainted(false);
	}


	/* ***************************************
	*
	*   Implements ActionListener actionPerformed
	* 	@param event The event object of the action
	*/

    public void actionPerformed(ActionEvent event)
    {
    	isClicked = true;
    }

    public boolean isClicked()
    {
    	return isClicked;
    }

    public void reset()
    {
    	isClicked = false;
    }

    public void setPosition(int x, int y, int offsetX, int offsetY)
    {
    	this.x = x;
    	this.y = y;

    	button.setBounds( new Rectangle( new Point( x + offsetX, y + offsetY ) , button.getPreferredSize() ) );
    }

    public int getWidth()
    {
    	return button.getWidth();
    }

	public void add(Display display)
	{
	    display.add( button );
	    button.setVisible( true );
		display.invalidate();
	}

	public void remove(Display display)
	{
		display.remove( button );
		display.invalidate();
	}
}
