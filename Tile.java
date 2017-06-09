import java.util.*;

public class Tile 
{
	private Suit suit;
	private int number;
	private Dragon dragonType;
	private Wind windType;
	private String tileFront;
	private static String tileBack = "TileBack.png";
	private static String[] tileFronts;
	
	/*
	* For honor tiles, 'number' parameter is used to indicate type:
	* Red dragon, north wind = 0
	* Green dragon, south wind = 1
	* White dragon, east wind = 2
	* West wind = 4
	*/
	public Tile(Suit suit, int number)
	{
		tileFronts = new String[34];
			tileFronts[0] = "OneBamboo.png";
			tileFronts[1] = "TwoBamboo.png";
			tileFronts[2] = "ThreeBamboo.png";
			tileFronts[3] = "FourBamboo.png";
			tileFronts[4] = "FiveBamboo.png";
			tileFronts[5] = "SixBamboo.png";
			tileFronts[6] = "SevenBamboo.png";
			tileFronts[7] = "EightBamboo.png";
			tileFronts[8] = "NineBamboo.png";
			tileFronts[9] = "OneCharacter.png";
			tileFronts[10] = "TwoCharacter.png";
			tileFronts[11] = "ThreeCharacter.png";
			tileFronts[12] = "FourCharacter.png";
			tileFronts[13] = "FiveCharacter.png";
			tileFronts[14] = "SixCharacter.png";
			tileFronts[15] = "SevenCharacter.png";
			tileFronts[16] = "EightCharacter.png";
			tileFronts[17] = "NineCharacter.png";
			tileFronts[18] = "OneCircle.png";
			tileFronts[19] = "TwoCircle.png";
			tileFronts[20] = "ThreeCircle.png";
			tileFronts[21] = "FourCircle.png";
			tileFronts[22] = "FiveCircle.png";
			tileFronts[23] = "SixCircle.png";
			tileFronts[24] = "SevenCircle.png";
			tileFronts[25] = "EightCircle.png";
			tileFronts[26] = "NineCircle.png";
			tileFronts[27] = "NorthWind.png";
			tileFronts[28] = "SouthWind.png";
			tileFronts[29] = "EastWind.png";
			tileFronts[30] = "WestWind.png";
			tileFronts[31] = "RedDragon.png";
			tileFronts[32] = "GreenDragon.png";
			tileFronts[33] = "WhiteDragon.png";
			
		this.suit = suit;
		if (suit == Suit.DRAGON)
		{
			if (number == 0)
			{
				dragonType = Dragon.RED;
				tileFront = tileFronts[31];
			}
			else if (number == 1)
			{
				dragonType = Dragon.GREEN;
				tileFront = tileFronts[32];
			}
			else
			{
				dragonType = Dragon.WHITE;
				tileFront = tileFronts[33];
			}
			this.number = 0;
		}
		else if (suit == Suit.WIND)
		{
			if (number == 0)
			{
				windType = Wind.NORTH;
				tileFront = tileFronts[27];
			}
			else if (number == 1)
			{
				windType = Wind.SOUTH;
				tileFront = tileFronts[28];
			}
			else if (number == 2)
			{
				windType = Wind.EAST;
				tileFront = tileFronts[29];
			}
			else
			{
				windType = Wind.WEST;
				tileFront = tileFronts[30];
			}
			this.number = 0;
		}
		else if (suit != Suit.NONE)
		{
			if (suit == Suit.BAMBOO)
			{
				tileFront = tileFronts[number - 1];
			}
			else if (suit == Suit.CHARACTER)
			{
				tileFront = tileFronts[number + 8];
			}
			else
			{
				tileFront = tileFronts[number + 17];
			}

		}
		else
		{
			tileFront = "Blank.png";
		}
		this.number = number;
	}
	
	public Suit suit()
	{
		return suit;
	}
	
	public String front()
	{
		return tileFront;
	}
	
	public static String back()
	{
		return tileBack;
	}
	
	public Dragon dragon()
	{
		if (suit != Suit.DRAGON)
		{
			return Dragon.ERR;
		}
		else
		{
			return dragonType;
		}
	}
	
	public Wind wind()
	{
		if (suit != Suit.WIND)
		{
			return Wind.ERR;
		}
		else
		{
			return windType;
		}
	}
	
	public int number()
	{
		return number;
	}
	
	//Returns 0 if tiles are same, 1 if tiles are consecutive, -1 otherwise
	public static int compare(Tile a, Tile b)
	{
		if (a.suit() != b.suit())
		{
			return -1;
		}
		else if (a.number() == b.number())
		{
			return 0;
		}
		else if (a.number() == b.number() + 1 || b.number() == a.number() + 1)
		{
			if (a.suit() == Suit.DRAGON || a.suit() == Suit.WIND)
			{
				return -1;
			}

			return 1;
		}
		else
		{
			return -1;
		}
	}
	
	public String toString()
	{
		String ret = "";
		if (suit == Suit.BAMBOO)
		{
			ret += " Bamboo";
		}
		else if (suit == Suit.CIRCLE)
		{
			ret += " Circle";
		}
		else if (suit == Suit.CHARACTER)
		{
			ret += " Character";
		}
		else if (suit == Suit.DRAGON)
		{
			if (dragonType == Dragon.RED)
			{
				return "Red dragon";
			}
			else if (dragonType == Dragon.GREEN)
			{
				return "Green dragon";
			}
			else
			{
				return "White dragon";
			}
		}
		else if (suit == Suit.WIND)
		{
			if (windType == Wind.NORTH)
			{
				return "North wind";
			}
			else if (windType == Wind.SOUTH)
			{
				return "South wind";
			}
			else if (windType == Wind.EAST)
			{
				return "East wind";
			}
			else
			{
				return "West wind";
			}
		}
		
		return number + ret;
	}
	
	public void set(Tile tile)
	{
		this.suit = tile.suit();
		this.number = tile.number();
	}
}
