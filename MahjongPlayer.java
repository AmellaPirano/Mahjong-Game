import java.util.*;
import javax.swing.*;
import java.awt.*;

public class MahjongPlayer
{
	private Tile[] hand;
	private String name;
	private JButton draw;
	private JButton drawDiscard;
	private JRadioButton[] handTiles;
	private Tile blank;
	private ButtonGroup tileDiscard;
	
	public MahjongPlayer(TileSet set, String name)
	{
		hand = new Tile[14];
		initialDraw(set);
		this.name = name;
		
		handTiles = new JRadioButton[14];
		draw = new JButton(new ImageIcon(Tile.back()));
		blank = new Tile(Suit.NONE, 0);
		drawDiscard = new JButton(new ImageIcon(blank.front()));
		drawDiscard.setEnabled(false);
		tileDiscard = new ButtonGroup();
		for (int i = 0; i < 14; i++)
		{
			handTiles[i] = new JRadioButton(new ImageIcon(blank.front()));
			handTiles[i].setPreferredSize(new Dimension(100, 140));
			handTiles[i].setSelectedIcon(handTiles[i].getDisabledIcon());
			tileDiscard.add(handTiles[i]);
			handTiles[i].setEnabled(false);
		}
		handTiles[13].setEnabled(false);
	}
	
	public Tile[] hand()
	{
		return hand;
	}
	
	public String name()
	{
		return name;
	}
	
	public Tile[] draw(TileSet set)
	{
		hand[13] = set.draw();
		return hand;
	}
	
	protected Tile[] initialDraw(TileSet set)
	{
		for (int i = 0; i < 13; i++)
		{
			hand[i] = set.draw();
		}
		hand[13] = new Tile(Suit.NONE, 0);
		organize(13);
		return hand;
	}

	
	public void discard(int discard)
	{
		while (discard < 13)
		{
			hand[discard] = hand[discard + 1];
			discard++;
		}
		hand[13] = new Tile(Suit.NONE, 0);
	}
	
	protected void organize(int m)
	{
		int start = 0;
		int num = 0;
		for (int j = 0; j<m; j++)
		{
			int low = 20;
			boolean swap = false;
			Tile tmp;
			for (int i = j; i <m; i++)
			{
				if(hand[i].suit() == Suit.BAMBOO)
				{
					if (hand[i].number() < low)
					{
						low = hand[i].number();
						num = i;
						swap = true;
					}
				}
			}
			if (swap)
			{
				tmp = hand[j];
				hand[j] = hand[num];
				hand[num] = tmp;
				start++;
			}
			
		}
		
		for (int j = start; j<m; j++)
		{
			int low = 20;
			boolean swap = false;
			Tile tmp;
			for (int i = j; i <m; i++)
			{
				if(hand[i].suit() == Suit.CHARACTER)
				{
					if (hand[i].number() < low)
					{
						low = hand[i].number();
						num = i;
						swap = true;
					}
				}
			}
			if (swap)
			{
				tmp = hand[j];
				hand[j] = hand[num];
				hand[num] = tmp;
				start++;
			}
		
		}
		
		for (int j = start; j<m; j++)
		{
			int low = 20;
			boolean swap = false;
			Tile tmp;
			for (int i = j; i <13; i++)
			{
				if(hand[i].suit() == Suit.CIRCLE)
				{
					if (hand[i].number() < low)
					{
						low = hand[i].number();
						num = i;
						swap = true;
					}
				}
			}
			if (swap)
			{
				tmp = hand[j];
				hand[j] = hand[num];
				hand[num] = tmp;
				start++;
			}
			
		}
		
		for (int j = start; j<m; j++)
		{
			int low = 20;
			boolean swap = false;
			Tile tmp;
			for (int i = j; i <14; i++)
			{
				if(hand[i].suit() == Suit.WIND)
				{
					if (hand[i].number() < low)
					{
						low = hand[i].number();
						num = i;
						swap = true;
					}
				}
			}
			
			if (swap)
			{
				tmp = hand[j];
				hand[j] = hand[num];
				hand[num] = tmp;
				start++;
			}
		}
		
		for (int j = start; j<m; j++)
		{
			int low = 20;
			boolean swap = false;
			Tile tmp;
			for (int i = j; i <14; i++)
			{
				if(hand[i].suit() == Suit.DRAGON)
				{
					if (hand[i].number() < low)
					{
						low = hand[i].number();
						num = i;
						swap = true;
					}
				}
			}
			
			if (swap)
			{
				tmp = hand[j];
				hand[j] = hand[num];
				hand[num] = tmp;
				start++;
			}
		}
	}
	
	private int ILoop(ArrayList<Tile> winHand, int winTotal)
	{
		if (winHand.size() < 3)
		{
			return winTotal;
		}
		for (int i = 0; i < winHand.size() - 1; i++)
		{
			for (int j = i + 1; j < winHand.size(); j++)
			{
				if (Tile.compare(winHand.get(i), winHand.get(j)) == 0)
				{
					for (int k = j + 1; k < winHand.size(); k++)
					{
						if (Tile.compare(winHand.get(k), winHand.get(j)) == 0)
						{
							winTotal += 1;
							if (k > i && k > j)
								{
									winHand.remove(k);
									
									if (j > i)
									{
										winHand.remove(j);
										winHand.remove(i);
										return ILoop(winHand, winTotal);
									}
									else
									{
										winHand.remove(i);
										winHand.remove(j);
										return ILoop(winHand, winTotal);
									}
								}
							else if (j > k && j > i)
							{
								winHand.remove(j);
								
								if (k > i)
								{
									winHand.remove(k);
									winHand.remove(i);
									return ILoop(winHand, winTotal);
								}
								else
								{
									winHand.remove(i);
									winHand.remove(k);
									return ILoop(winHand, winTotal);
								}
							}
							else
							{
								winHand.remove(i);
								
								if (j > k)
								{
									winHand.remove(j);
									winHand.remove(k);
									return ILoop(winHand, winTotal);
								}
								else
								{
									winHand.remove(k);
									winHand.remove(j);
									return ILoop(winHand, winTotal);
								}
							}
						}
					}
				}
			}
		}
		return winTotal;
	}
	
	private int SLoop(ArrayList<Tile> winHand, int winTotal)
	{
		if (winHand.size() < 3)
		{
			return winTotal;
		}
		for (int i = 0; i < winHand.size() - 1; i++)
		{
			for (int j = i + 1; j < winHand.size(); j++)
			{
				if (Tile.compare(winHand.get(i), winHand.get(j)) == 1)
					{
						for (int k = 0; k < winHand.size(); k++)
						{
							if (k == i || k == j || (Tile.compare(winHand.get(k), winHand.get(j)) == 0) || Tile.compare(winHand.get(k), winHand.get(i)) == 0)
							{
								continue;
							}

							if (Tile.compare(winHand.get(k), winHand.get(j)) == 1 || Tile.compare(winHand.get(i), winHand.get(k)) == 1)
							{
								winTotal += 1;
								if (k > i && k > j)
								{
									winHand.remove(k);
									
									if (j > i)
									{
										winHand.remove(j);
										winHand.remove(i);
										return SLoop(winHand, winTotal);
									}
									else
									{
										winHand.remove(i);
										winHand.remove(j);
										return SLoop(winHand, winTotal);
									}
								}
								else if (j > k && j > i)
								{
									winHand.remove(j);
									
									if (k > i)
									{
										winHand.remove(k);
										winHand.remove(i);
										return SLoop(winHand, winTotal);
									}
									else
									{
										winHand.remove(i);
										winHand.remove(k);
										return SLoop(winHand, winTotal);
									}
								}
								else
								{
									winHand.remove(i);
									
									if (j > k)
									{
										winHand.remove(j);
										winHand.remove(k);
										return SLoop(winHand, winTotal);
									}
									else
									{
										winHand.remove(k);
										winHand.remove(j);
										return SLoop(winHand, winTotal);
									}
								}
							}
						}
					}
			}
		}
		return winTotal;
		
	}
	public boolean determineWin()
	{
		int winTotal = 0;
		
		ArrayList<Tile> winHand = new ArrayList<Tile>();
		for (int i = 0; i < 14; i++)
		{
			winHand.add(hand[i]);
		}
		
		//First, check for identicals
		winTotal = ILoop(winHand, winTotal);

	//Next, check for sequences	
		winTotal = SLoop(winHand, winTotal);

		
		//Last, check the final two for equality
		if (winTotal == 4 && Tile.compare(winHand.get(0), winHand.get(1)) == 0) 
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void takeTurn(TileSet set, Tile prevDiscard)
	{
		for (int i = 0; i < 13; i++)
		{
			handTiles[i].setIcon(new ImageIcon(hand[i].front()));
		}
		handTiles[13] = new JRadioButton(new ImageIcon(blank.front()));
		tileDiscard.add(handTiles[13]);
		
		if (prevDiscard.suit() != Suit.NONE)
		{
			drawDiscard = new JButton(new ImageIcon(prevDiscard.front()));
			drawDiscard.setEnabled(true);
		}
	}
	
	public void clearSelection()
	{
		tileDiscard.clearSelection();
	}
	
	public JButton draw()
	{
		return draw;
	}
	
	public JButton drawDiscard()
	{
		return drawDiscard;
	}
	
	public JRadioButton[] handTiles()
	{
		return handTiles;
	}
}