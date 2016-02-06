/*
    Author: Ryan Welch
*/

package me.ryanwelch.quiz;

import me.ryanwelch.quiz.util.*;
import me.ryanwelch.quiz.graphics.*;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.simple.*;
import java.awt.*;

public class Player
{

	// The current position on the board of the player, in tile coords
	private Vector position;
	private int score;
	private int id;

	public Player(int id)
	{
		this.id = id;
		this.score = 0;
		this.position = new Vector();
	}

	public int getId()
	{
		return id;
	}

	public void addToScore(int points)
	{
		score += points;
	}

	public void setScore(int newScore)
	{
		score = newScore;
	}

	public int getScore()
	{
		return score;
	}

	public double getX()
	{
		return position.getX();
	}

	public double getY()
	{
		return position.getY();
	}

	public Vector getPosition()
	{
		return position;
	}

	public void setPosition(Vector newPosition)
	{
		position = newPosition;
	}

	public Map serialize()
	{
		Map obj = new LinkedHashMap();
		obj.put("tile_x", getX());
		obj.put("tile_y", getY());
		obj.put("id", id);
		obj.put("score", score);

		return obj;
	}

	public void unserialize(JSONObject obj)
	{
		double x = (double) obj.get("tile_x");
		double y = (double) obj.get("tile_y");
		position = new Vector(x, y);

		long _id = (long) obj.get("id");
		id = (int) _id;
		long _score = (long) obj.get("score");
		score = (int) _score;
	}

}
