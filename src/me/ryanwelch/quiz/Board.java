/*
	Author: Ryan Welch
*/

package me.ryanwelch.quiz;

import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ArrayList;
import org.json.simple.*;
import org.json.simple.parser.*;
import me.ryanwelch.quiz.graphics.*;
import java.awt.*;
import java.awt.geom.*;
import me.ryanwelch.quiz.util.*;

public class Board
{

	// Array of players
	private List<Player> players = new ArrayList<Player>();

	// The size of the tile (all tiles are square)
	private final int TILE_SIZE = 70;

	/*
	*	Tile types:
	*	0 - An empty tile
	*	1 - A solid tile
	*/
	private final int[][] map = {
			{ 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 1 },
			{ 1, 0, 0, 0, 0, 0, 1 },
			{ 1, 1, 1, 1, 1, 1, 1 }
		};

	// TODO: Define movement
	private final int[][] mapOrder = {
			{ 1, 2, 3, 4, 5, 6, 7 },
			{20, 0, 0, 0, 0, 0, 8 },
			{19, 0, 0, 0, 0, 0, 9 },
			{18, 0, 0, 0, 0, 0,10 },
			{17,16,15,14,13,12,11 }
		};

	private Font font = new Font("Serif", Font.PLAIN, 20);

	private int currentIndex;
	private boolean hasFinished = false;


	/* ***************************************
	*
	*   Constructs Board class
	*/

	public Board()
	{
		currentIndex = 0;
	}


	/* ***************************************
	*
	*   Draw a specific tile to the screen
	*   @param d Display instance
	*	@param g2 Graphics2d instance
	*	@param tileX The x coordinate of the tile (not in pixels)
	*	@param tileY The y coordinate of the tile (not in pixels)
	*/

	public void drawTile(Display d, Graphics2D g2, int tileX, int tileY)
	{
		// Calculate tile position
		int x = tileX * TILE_SIZE;
		int y = tileY * TILE_SIZE;

		// Set colour for border
		g2.setPaint(new Color(30, 109, 180));
		// Draw outline
		g2.drawRect(x, y, TILE_SIZE, TILE_SIZE);

		// Set colour for tile fill
		g2.setPaint(new Color(177, 215, 249));
		// Draw fill inside the border (assuming border width 2)
		g2.fillRect(x + 1, y + 1, TILE_SIZE - 2, TILE_SIZE - 2);
	}


	public void drawPlayer(Display d, Graphics2D g2, Player player)
	{
		int x = (int) player.getX() * TILE_SIZE;
		int y = (int) player.getY() * TILE_SIZE;

		// Add player specific offsets, places each player in a different corner of the tile
		x += 2 + (TILE_SIZE / 2) * ((player.getId() - 1) % 2);
		y += 2 + (TILE_SIZE / 2) * (player.getId() > 2 ? 1 : 0);

		// Set colour for player, TODO: improve this, use hash codes to generate unique colors
		switch ( player.getId() )
		{
			case 1:
				g2.setPaint(new Color(14, 192, 251));
				break;
			case 2:
				g2.setPaint(new Color(105, 192, 58));
				break;
			case 3:
				g2.setPaint(new Color(255, 225, 26));
				break;
			case 4:
				g2.setPaint(new Color(253, 116, 0));
				break;
		}

		g2.fillOval(x, y, TILE_SIZE / 2 - 4, TILE_SIZE / 2 - 4);
	}


	public void drawScore(Display d, Graphics2D g2)
	{
		Player[] playerList = players.toArray(new Player[players.size()]); // Copy of array for sorting

		// Sort playerlist in order of scores
		sortByScore(playerList);

		// Setup fonts and rendering options
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setFont(font);

		g2.drawString("Scores:", 10, 20);

		Player player;
		for (int i = 0; i < playerList.length; i++)
		{
			player = playerList[i];
			g2.drawString("Player " + player.getId() + ": " + player.getScore(), 10, 40 + 20 * i);
		}
	}


	/* ***************************************
	*
	*   Draw the tile map to the screen
	*   @param d Display instance
	*	@param g2 Graphics2d instance
	*/

	public void draw(Display d, Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;

		AffineTransform prevTransform = g2.getTransform();

		int xOffset = (d.width / 2) - (map[0].length * TILE_SIZE ) / 2;
		int yOffset = (d.height / 2) - (map.length * TILE_SIZE ) / 2;
		// Translate to draw board at center of screen
		g2.translate(xOffset, yOffset);

		// Remember previous graphics settings
		Paint prevPaint = g2.getPaint();
		Stroke prevStroke = g2.getStroke();
		g2.setStroke(new BasicStroke(2));

		// Draw tile map
		for (int i = 0; i < map.length; i++)
		{
			for (int j = 0; j < map[i].length; j++)
			{
				// The type of tile
				int tile = map[i][j];

				// If the tile is of type 1 (a solid tile)
				if ( tile ==  1 )
				{
					// Note: i is y value, j is x value
					drawTile(d, g2, j, i);
				}
			}
		}

		// Draw players
		for (Player player : players)
		{
			drawPlayer(d, g2, player);
		}

		// Revert graphics settings
		g2.setStroke(prevStroke);
		g2.setPaint(prevPaint);

		g2.setTransform(prevTransform);

		drawScore(d, g2);
	}


	// Bubble sort
	public Player[] sortByScore(Player[] array)
	{
		int length = array.length;
		boolean sorted = false;

        while (!sorted)
        {
            sorted = true;

			for (int i = 0; i < length - 1; i++)
			{
				if (array[i].getScore() < array[i + 1].getScore())
				{
					// swap them
					Player temp = array[i + 1];
					array[i + 1] = array[i];
					array[i] = temp;
					// array wasn't sorted
					sorted = false;
				}
			}
	    }

	    return array;
	}


	/* ***************************************
	*
	*   Find the neighbouring tile which has the next position (goes up in step by 1)
	*	@param tileX The x coordinate of the tile (not in pixels)
	*	@param tileY The y coordinate of the tile (not in pixels)
	*/

	public Vector nextPosition(Vector position)
	{
		int tileX = (int) position.getX();
		int tileY = (int) position.getY();

		int currentValue = mapOrder[tileY][tileX];

		if(tileX - 1 >= 0 && mapOrder[tileY][tileX - 1] == currentValue + 1)
		{
			return new Vector(tileX - 1, tileY);
		}
		else if(tileX + 1 < mapOrder[tileY].length && mapOrder[tileY][tileX + 1] == currentValue + 1)
		{
			return new Vector(tileX + 1, tileY);
		}
		else if(tileY - 1 >= 0 && mapOrder[tileY - 1][tileX] == currentValue + 1)
		{
			return new Vector(tileX, tileY - 1);
		}
		else if(tileY + 1 < mapOrder.length && mapOrder[tileY + 1][tileX] == currentValue + 1)
		{
			return new Vector(tileX, tileY + 1);
		}

		// Only happens when we are at the end, no neighbours with a higher value
		return null;
	}


	/* ***************************************
	*
	*   Set the next player to the current player
	*/

	public void nextTurn()
	{
		currentIndex++;
		if ( currentIndex >= players.size() )
		{
			currentIndex = 0;
		}
	}


	/* ***************************************
	*
	*   Add a player to the board game
	*	@param player The player
	*/

	public boolean addPlayer(Player player)
	{
		if ( !players.contains(player) )
		{
			players.add(player);

			return true;
		}

		return false;
	}


	/* ***************************************
	*
	*   Gets the player at position
	*	@param index The index of the player
	*/

	public Player getPlayer(int index)
	{
		if(index < 0 || index >= players.size())
		{
			return null;
		}

		return players.get(index);
	}


	/* ***************************************
	*
	*   Get the player of the current turn
	*/

	public Player getCurrentPlayer()
	{
		return players.get(currentIndex);
	}


	/* ***************************************
	*
	*   Get all the players of the board
	*/

	public List<Player> getPlayers()
	{
		return new ArrayList<Player>(players);
	}


	/* ***************************************
	*
	*   Clear all players from the board
	*/

	public void clearPlayers()
	{
		players.clear();
	}


	public boolean hasFinished()
	{
		return hasFinished;
	}

	public void setFinished(boolean finished)
	{
		hasFinished = finished;
	}

	public String serialize()
	{
		// Serialize to json
		JSONArray list = new JSONArray();
		for (Player player : players)
		{
			list.add(player.serialize());
		}

		Map obj = new LinkedHashMap();
		obj.put("current_turn", currentIndex);
		obj.put("finished", hasFinished);
		obj.put("players", list);

		return JSONValue.toJSONString(obj);
	}

	public void unserialize(String json)
	{
		// Parse json and load board state
		JSONObject obj = (JSONObject) JSONValue.parse(json);

		long currentTurn = (long) obj.get("current_turn");
		currentIndex = (int) currentTurn;

		hasFinished = (boolean) obj.get("finished");

		clearPlayers();

		JSONArray list = (JSONArray) obj.get("players");
		Player player;
		for (int i = 0; i < list.size(); i++)
		{
			player = new Player(0);
			player.unserialize((JSONObject) list.get(i));
			addPlayer(player);
		}
	}
}
