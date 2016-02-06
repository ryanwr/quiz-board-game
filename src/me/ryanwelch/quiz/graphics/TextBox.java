/*
	Author: Ryan Welch
*/

package me.ryanwelch.quiz.graphics;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class TextBox implements ActionListener
{

	private JTextField textBox;

	private int x;
	private int y;

	private boolean isSubmitted = false;
	private String result;

	public TextBox(int size)
	{
		textBox = new JTextField( size );

        Border border = textBox.getBorder();
        Border padding = new EmptyBorder(5, 5, 5, 5);
        textBox.setBorder(new CompoundBorder(border, padding));

		textBox.addActionListener( this );
	}


	/* ***************************************
	*
	*   Implements ActionListener actionPerformed
	* 	@param event The event object of the action
	*/

    public void actionPerformed(ActionEvent event)
    {
    	isSubmitted = true;
    	result = textBox.getText();
    }

    public boolean isSubmitted()
    {
    	return isSubmitted;
    }

    public String getText()
    {
    	return result;
    }

    public void reset()
    {
    	isSubmitted = false;
    	result = null;
    	textBox.setText("");
    }

    public void setPosition(int x, int y, int offsetX, int offsetY)
    {
    	this.x = x;
    	this.y = y;

    	textBox.setBounds( new Rectangle( new Point( x + offsetX, y + offsetY ) , textBox.getPreferredSize() ) );
    }

    public int getWidth()
    {
    	return textBox.getWidth();
    }

	public void add(Display display)
	{
	    display.add( textBox );
	    textBox.setVisible( true );
		display.invalidate();
	}

	public void remove(Display display)
	{
		display.remove( textBox );
		display.invalidate();
	}
}
