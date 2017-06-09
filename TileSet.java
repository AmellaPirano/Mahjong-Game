import java.util.*;
import java.io.*;

public class TileSet
{
	private ArrayList<Tile> tiles;
	private int top;
	
	public TileSet()
	{
		tiles = new ArrayList<Tile>();
		top = 136;
		
		for (int i = 0; i < 9; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				tiles.add(new Tile(Suit.BAMBOO, i+1));
			}
		}
		for (int i = 0; i < 9; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				tiles.add(new Tile(Suit.CIRCLE, i+1));
			}
		}
		for (int i = 0; i < 9; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				tiles.add(new Tile(Suit.CHARACTER, i+1));
			}
		}
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				tiles.add(new Tile(Suit.DRAGON, i));
			}
		}
		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				tiles.add(new Tile(Suit.WIND, i));
			}
		}
		shuffle();
	}
	
	public Tile draw()
	{
		top = top - 1;
		return tiles.get(top);
	}
	
	public int getTop()
	{
		return top;
	}
	
	private void shuffle()
	{
		ArrayList<Tile> shuffled = new ArrayList<Tile>();
		Random rn = new Random();
		int random;
		int j = 0;
		
		for (int k = 136; k >= 1; k--)
		{
			random = rn.nextInt(k);
			shuffled.add(tiles.get(random));
			tiles.remove(random);
			j++;
		}
		
		tiles = shuffled;
	}
}