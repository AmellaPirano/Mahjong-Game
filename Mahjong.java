import java.util.*;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.*;

public class Mahjong
{
	private MahjongPlayer[] players;
	private TileSet set;
	private Tile currentDiscard;
	private int numPlayers;
	private int currPlayer;
	private static String rulesText = "The rules are as follows: " + 
	"There are 136 total tiles. There are three basic suits: Bamboo, Character" +
	" and Circle; and two honor suits: Wind and Dragon. The basic suits are numbered 1 - 9" +
	" and the honor suits have North, South, East, West and Red, Green and White respectively." +
	" There are four identical copies of each tile in the full set. The game is played with 2-4 players. Each player begins with 13 tiles." +
	" At the beginning of their turn, they draw a tile either from the set or by taking the previous player's discard." +
	" The goal is to have four melds and an eyes during your turn.  Melds may be formed with either three identical" +
	" tiles or three consecutive tiles of the same suit.  The honor suits may not form consecutive melds. (I.E. 3 red" +
	" dragons is a meld, but red dragon, green dragon, white dragon is not). Eyes are made up of two identical tiles.  At the end of a player's" +
	" turn, if they do not have the tiles to win in their hand, they must discard a tile.  The game continues until" +
	" a player has a winning hand.";
	
	public Mahjong(int numPlayers)
	{
		set = new TileSet();
		this.numPlayers = numPlayers;
		currPlayer = 0;
		
		players = new MahjongPlayer[numPlayers];
		String tmp = "";
		for (int i = 0; i < numPlayers; i++)
		{
			tmp = "Player " + (i+1);
			players[i] = new MahjongPlayer(set, tmp);
		}
		
		currentDiscard = new Tile(Suit.NONE, 0);
	}
	
	private void nextPlayer()
	{
		currPlayer++;
	}
	
	private int currPlayer()
	{
		return currPlayer;
	}
	
	public static void main(String[] args) throws IOException
	{
		JPanel panel = new JPanel();
		JFrame frame = new JFrame();
		frame.setSize(2000, 1200);
		frame.setTitle("Mahjong");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel startPanel = new JPanel();
		startPanel.setLayout(new GridLayout(1,2));
		JButton start = new JButton("Start game");
		start.setFont(new Font("Serif", Font.PLAIN, 32));
		JButton rules = new JButton("Rules");
		rules.setFont(new Font("Serif", Font.PLAIN, 32));
		JButton tiles = new JButton("Tiles");
		tiles.setFont(new Font("Serif", Font.PLAIN, 32));
		JTextField players = new JTextField("Enter number of players between two and four");
		players.setFont(new Font("Serif", Font.PLAIN, 32));
		JLabel welcome = new JLabel("Welcome to Mahjong!", JLabel.CENTER);
		welcome.setFont(new Font("Serif", Font.PLAIN, 90));
		JTextArea ruleText = new JTextArea(Mahjong.rulesText, 9, 50);
		ruleText.setLineWrap(true);
		ruleText.setWrapStyleWord(true);
		ruleText.setFont(new Font("Serif", Font.PLAIN, 24));
		BufferedImage tilesImage = ImageIO.read(new File("AllTiles.png"));
		
		panel.setLayout(new GridLayout(4, 1));
			
		start.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent event)
				{
					if (players.getText().length() > 1)
					{
						players.setText("Please enter a valid number of human players: 2, 3 or 4");
						frame.repaint();
					}
					else if (Integer.parseInt(players.getText()) < 2 || Integer.parseInt(players.getText()) > 4)
					{
						players.setText("Please enter a valid number of human players: 2, 3 or 4");
						frame.repaint();
					}
					else
					{
						Mahjong game = new Mahjong(Integer.parseInt(players.getText()));
						frame.dispose();
		
						int i = 0;
						game.turn(game);	
					}
				}
			});
		
		rules.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent event)
				{
					JFrame ruleFrame = new JFrame();
					JPanel rulePanel = new JPanel();
					ruleFrame.setSize(1000, 400);
					rulePanel.add(ruleText);
					rulePanel.setVisible(true);
					ruleFrame.add(rulePanel);
					ruleFrame.setVisible(true);
					frame.repaint();
				}
			});
			
		tiles.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent event)
				{
					JFrame tileFrame = new JFrame();
					JPanel tilePanel = new JPanel();
					tileFrame.setSize(1200, 1200);
					tilePanel.add(new JLabel(new ImageIcon(tilesImage)));
					tileFrame.add(tilePanel);
					tilePanel.setVisible(true);
					tileFrame.setVisible(true);
					frame.repaint();
				}
			});
		
		startPanel.add(start);
		startPanel.add(players);
		panel.add(welcome);
		panel.add(startPanel);
		panel.add(rules);
		panel.add(tiles);
		panel.setVisible(true);
		frame.setVisible(true);
		frame.add(panel);
		frame.repaint();
	}

	
	private void turn(Mahjong game)
	{
		JPanel panel = new JPanel();
		JFrame frame = new JFrame();
		frame.setSize(2000, 1200);
		frame.setTitle("Mahjong");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel.setLayout(new BorderLayout());
		JButton endTurn = new JButton("End turn");
		endTurn.setEnabled(false);
		endTurn.setFont(new Font("Serif", Font.PLAIN, 32));
		JPanel center = new JPanel();
		center.setLayout(new GridLayout(1, 2));
		center.setVisible(true);
		JPanel hand = new JPanel();
		hand.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));	
		hand.setVisible(true);
		panel.repaint();
		frame.repaint();
			
		MahjongPlayer p = game.players[currPlayer()];
		p.takeTurn(game.set, game.currentDiscard);
			
				
			p.draw().addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						try {
						p.draw(set);
						}
						catch (IndexOutOfBoundsException e)
						{
							frame.removeAll();
							JOptionPane.showMessageDialog(frame, "Out of tiles to draw.  Game over!");
						}
						p.handTiles()[13].setIcon(new ImageIcon(p.hand()[13].front()));
						p.handTiles()[13].setSelectedIcon(p.handTiles()[13].getDisabledIcon());
						p.handTiles()[13].setEnabled(true);
						for (int n = 0; n < 14; n++)
						{
							p.handTiles()[n].setEnabled(true);
						}
						p.draw().setEnabled(false);
						p.drawDiscard().setEnabled(false);
						if (p.determineWin())
						{
							win(p);
						}
						((AbstractButton) event.getSource()).removeActionListener(this);	
					}
				});
			
			p.drawDiscard().addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						p.hand()[13] = game.currentDiscard;
						p.handTiles()[13].setIcon(new ImageIcon(p.hand()[13].front()));
						p.handTiles()[13].setSelectedIcon(p.handTiles()[13].getDisabledIcon());
						for (int n = 0; n < 14; n++)
						{
							p.handTiles()[n].setEnabled(true);
						}
						p.draw().setEnabled(false);
						p.drawDiscard().setEnabled(false);	
						if (p.determineWin())
						{
							win(p);
						}
						((AbstractButton) event.getSource()).removeActionListener(this);	
					}
				});
				
				for (int k = 0; k < 14; k++)
				{
					final int j = k;
					p.handTiles()[j].addActionListener(
					new ActionListener()
					{
						public void actionPerformed(ActionEvent event)
						{
							endTurn.setEnabled(true);
							((AbstractButton) event.getSource()).removeActionListener(this);	
						}
					});
				}
				
				endTurn.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						int j = 0;
						int k = 0;
						while (k < 14)
						{
							if (p.handTiles()[k].isSelected())
							{
								j = k;
								break;
							}
							k++;
						}
						
						game.setCurrentDiscard(p.hand()[j]);
						p.discard(j);
						for (int n = 0; n < 14; n++)
						{
							p.handTiles()[n].setEnabled(false);
						}
						p.draw().setEnabled(true);
						p.drawDiscard().setEnabled(true);
						
						p.clearSelection();
						frame.dispose();
						nextPlayer();
						nextTurn(game);
						((AbstractButton) event.getSource()).removeActionListener(this);	
					}
				});
				
				center.add(p.drawDiscard());
				center.add(p.draw());
				panel.add(center, BorderLayout.CENTER);
				for (int j = 0; j < 14; j++)
				{
					hand.add(p.handTiles()[j]);
					hand.add(endTurn);
				}
				panel.add(hand, BorderLayout.SOUTH);
				panel.setVisible(true);
				frame.add(panel);
				frame.repaint();
				
		frame.add(panel);
		panel.setVisible(true);
		frame.setVisible(true);
	}
	
	private void nextTurn(Mahjong game)
	{
		if (currPlayer() >= game.numPlayers)
		{
			backToStart();
		}
		JLabel pass = new JLabel(game.players[currPlayer()].name() + ", it's your turn!");
		pass.setFont(new Font("Serif", Font.PLAIN, 90));
		JPanel p = new JPanel();
		p.add(pass);
		JFrame f = new JFrame();
		f.setSize(2000, 1200);
		f.add(p);
		turn(game);
		f.setVisible(true);
		f.repaint();
	}
	
	private void setCurrentDiscard(Tile newDiscard)
	{
		currentDiscard = new Tile(newDiscard.suit(), newDiscard.number());
	}
	
	private void backToStart()
	{
		currPlayer = currPlayer - numPlayers;
	}
	
	private void win(MahjongPlayer p)
	{
		JFrame winFrame = new JFrame();
		winFrame.setSize(2000, 1200);
		winFrame.setTitle("Mahjong");
		winFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel winScreen = new JPanel();
		JLabel winText = new JLabel(p.name() + "wins!!");
		winText.setFont(new Font("Serif", Font.PLAIN, 90));
		winScreen.setLayout(new BorderLayout());
		winScreen.add(BorderLayout.CENTER, winText);
		JPanel winHand = new JPanel();
		winHand.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
		for (int h = 0; h < 14; h++)
		{
			winHand.add(p.handTiles()[h]);
		}
			
			winScreen.add(BorderLayout.SOUTH, winHand);
		
		winScreen.setVisible(true);
		winFrame.add(winScreen);
		winFrame.setVisible(true);
		winFrame.repaint();
		winScreen.repaint();
	}
}
