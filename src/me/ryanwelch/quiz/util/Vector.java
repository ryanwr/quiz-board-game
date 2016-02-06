/*
    Author: Ryan Welch
*/

package me.ryanwelch.quiz.util;

public class Vector
{

	public double x;
	public double y;

	public Vector()
	{
		this.x = 0;
		this.y = 0;
	}

	public Vector(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	public Vector(Vector v)
	{
		this.x = v.x;
		this.y = v.y;
	}

	public double getX()
	{
		return x;
	}

	public double getY()
	{
		return y;
	}

	public void setX(double newX)
	{
		x = newX;
	}

	public void setY(double newY)
	{
		y = newY;
	}
}
