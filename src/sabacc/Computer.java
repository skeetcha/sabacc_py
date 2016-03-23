package sabacc;

import java.util.Scanner;
import sabacc.Player;
import sabacc.Variables;
import sabacc.Card;
import java.util.Random;

public class Computer
{
	public void start()
	{
		System.out.println("Now running in Computer Mode...");
		Scanner scanner = new Scanner(System.in);
		System.out.println("How many people are playing?");
		int ans = scanner.nextInt();
		scanner.close();
		
		if (ans < 2)
		{
			System.out.println("You must play with 2-8 players.");
			System.exit(0);
		}
		else if (ans >= 2 && ans <= 8)
		{
			for (int i = 0; i < ans; i++)
			{
				Variables.players.add(new Player());
				Variables.players.get(i).money = 100;
			}
			
			System.out.println("There are " + ans + " players.");
		}
		else
		{
			System.out.println("You must play with 2-8 players.");
			System.exit(0);
		}
		
		for (int i = 0; i < Variables.players.size(); i++)
		{
			scanner = new Scanner(System.in);
			System.out.println("Please begin to input names.");
			System.out.print("Player " + (i + 1) + ": ");
			Variables.players.get(i).name = scanner.nextLine();
		}
		
		scanner.close();
		
		Variables.callingPhase = false;
		bettingRound(); // Round 1 is Betting
		cardSetup();
		dealTwoCardsToEach();
		hitOrStand(); // Round 2 is Hit or Stand
		bettingRound(); // Round 3 is Betting
		hitOrStand(); // Round 4 is Hit or Stand
		decideCalling(); // Round 5 is Calling
		endRound(); // Decide who one the Round
	}
	
	public void bettingRound()
	{
		int[] bets = new int[Variables.players.size()];
		Scanner scanner = new Scanner(System.in);
		
		for (int i = 0; i < bets.length; i++)
		{
			System.out.println("Player " + (i + 1) + ", you have " + Variables.players.get(i).money + " credits.");
			
			if (i == 0)
			{
				if (!Variables.players.get(i).bombedout && !Variables.players.get(i).fold)
				{
					while (true)
					{
						System.out.println("How much would you like to bet?");
						int ans = scanner.nextInt();
						
						if (ans > Variables.players.get(i).money)
						{
							System.out.println("You do not have enough money.");
						}
						else
						{
							bets[i] = ans;
							Variables.players.get(i).money -= ans;
							break;
						}
					}
				}
				else if (Variables.players.get(i).bombedout)
				{
					System.out.println("You have bombed out. You may not bet.");
				}
				else if (Variables.players.get(i).fold)
				{
					System.out.println("You have folded. You may not bet.");
				}
			}
			else
			{
				if (!Variables.players.get(i).bombedout && !Variables.players.get(i).fold)
				{
					while (true)
					{
						System.out.println("Player " + i + " has bet " + bets[i - 1] + " credits.");
						System.out.println("Will you match or raise?");
						String ans = scanner.nextLine();
						
						if (ans.equalsIgnoreCase("match"))
						{
							if (bets[i - 1] > Variables.players.get(i).money)
							{
								System.out.println("You do not have enough money. You must fold.");
								Variables.players.get(i).fold = true;
								break;
							}
							else
							{
								System.out.println("You matched.");
								bets[i] = bets[i - 1];
								Variables.players.get(i).money -= bets[i];
								break;
							}
						}
						else if (ans.equalsIgnoreCase("raise"))
						{
							System.out.println("By how much?");
							int ans1 = scanner.nextInt();
							bets[i] = bets[i - 1] + ans1;
							Variables.players.get(i).money -= bets[i];
							bets[i - 1] = bets[i];
							
							for (int j = 0; j < i; j++)
							{
								Variables.players.get(j).money -= ans1;
							}
							
							break;
						}
						else
						{
							System.out.println("That is not an acceptable choice.");
						}
					}
				}
				else if (Variables.players.get(i).bombedout)
				{
					System.out.println("You have bombed out. You may not bet.");
				}
				else if (Variables.players.get(i).fold)
				{
					System.out.println("You have folded. You may not bet.");
				}
			}
		}
		
		scanner.close();
	}
	
	public void cardSetup()
	{
		// Suits
		// 0: Saber
		// 1: Flask
		// 2: Coin
		// 3: Stave
		
		// Parameters:
		// String name, null for non-special cards
		// int value
		// int suit, -1 for special cards
		// int copy, -1 for non-special cards
		// boolean face
		
		Variables.cards.add(new Card(null, 1, 0, -1, false)); // 1 of Sabers
		Variables.cards.add(new Card(null, 2, 0, -1, false)); // 2 of Sabers
		Variables.cards.add(new Card(null, 3, 0, -1, false)); // 3 of Sabers
		Variables.cards.add(new Card(null, 4, 0, -1, false)); // 4 of Sabers
		Variables.cards.add(new Card(null, 5, 0, -1, false)); // 5 of Sabers
		Variables.cards.add(new Card(null, 6, 0, -1, false)); // 6 of Sabers
		Variables.cards.add(new Card(null, 7, 0, -1, false)); // 7 of Sabers
		Variables.cards.add(new Card(null, 8, 0, -1, false)); // 8 of Sabers
		Variables.cards.add(new Card(null, 9, 0, -1, false)); // 9 of Sabers
		Variables.cards.add(new Card(null, 10, 0, -1, false)); // 10 of Sabers
		Variables.cards.add(new Card(null, 11, 0, -1, false)); // 11 of Sabers
		Variables.cards.add(new Card("Commander", 12, 0, -1, true)); // Commander of Sabers
		Variables.cards.add(new Card("Mistress", 13, 0, -1, true)); // Mistress of Sabers
		Variables.cards.add(new Card("Master", 14, 0, -1, true)); // Master of Sabers
		Variables.cards.add(new Card("Ace", 15, 0, -1, true)); // Ace of Sabers
		
		Variables.cards.add(new Card(null, 1, 1, -1, false)); // 1 of Flasks
		Variables.cards.add(new Card(null, 2, 1, -1, false)); // 2 of Flasks
		Variables.cards.add(new Card(null, 3, 1, -1, false)); // 3 of Flasks
		Variables.cards.add(new Card(null, 4, 1, -1, false)); // 4 of Flasks
		Variables.cards.add(new Card(null, 5, 1, -1, false)); // 5 of Flasks
		Variables.cards.add(new Card(null, 6, 1, -1, false)); // 6 of Flasks
		Variables.cards.add(new Card(null, 7, 1, -1, false)); // 7 of Flasks
		Variables.cards.add(new Card(null, 8, 1, -1, false)); // 8 of Flasks
		Variables.cards.add(new Card(null, 9, 1, -1, false)); // 9 of Flasks
		Variables.cards.add(new Card(null, 10, 1, -1, false)); // 10 of Flasks
		Variables.cards.add(new Card(null, 11, 1, -1, false)); // 11 of Flasks
		Variables.cards.add(new Card("Commander", 12, 1, -1, true)); // Commander of Flasks
		Variables.cards.add(new Card("Mistress", 13, 1, -1, true)); // Mistress of Flasks
		Variables.cards.add(new Card("Master", 14, 1, -1, true)); // Master of Flasks
		Variables.cards.add(new Card("Ace", 15, 1, -1, true)); // Ace of Flasks
		
		Variables.cards.add(new Card(null, 1, 2, -1, false)); // 1 of Coins
		Variables.cards.add(new Card(null, 2, 2, -1, false)); // 2 of Coins
		Variables.cards.add(new Card(null, 3, 2, -1, false)); // 3 of Coins
		Variables.cards.add(new Card(null, 4, 2, -1, false)); // 4 of Coins
		Variables.cards.add(new Card(null, 5, 2, -1, false)); // 5 of Coins
		Variables.cards.add(new Card(null, 6, 2, -1, false)); // 6 of Coins
		Variables.cards.add(new Card(null, 7, 2, -1, false)); // 7 of Coins
		Variables.cards.add(new Card(null, 8, 2, -1, false)); // 8 of Coins
		Variables.cards.add(new Card(null, 9, 2, -1, false)); // 9 of Coins
		Variables.cards.add(new Card(null, 10, 2, -1, false)); // 10 of Coins
		Variables.cards.add(new Card(null, 11, 2, -1, false)); // 11 of Coins
		Variables.cards.add(new Card("Commander", 12, 2, -1, true)); // Commander of Coins
		Variables.cards.add(new Card("Mistress", 13, 2, -1, true)); // Mistress of Coins
		Variables.cards.add(new Card("Master", 14, 2, -1, true)); // Master of Coins
		Variables.cards.add(new Card("Ace", 15, 2, -1, true)); // Ace of Coins
		
		Variables.cards.add(new Card(null, 1, 3, -1, false)); // 1 of Staves
		Variables.cards.add(new Card(null, 2, 3, -1, false)); // 2 of Staves
		Variables.cards.add(new Card(null, 3, 3, -1, false)); // 3 of Staves
		Variables.cards.add(new Card(null, 4, 3, -1, false)); // 4 of Staves
		Variables.cards.add(new Card(null, 5, 3, -1, false)); // 5 of Staves
		Variables.cards.add(new Card(null, 6, 3, -1, false)); // 6 of Staves
		Variables.cards.add(new Card(null, 7, 3, -1, false)); // 7 of Staves
		Variables.cards.add(new Card(null, 8, 3, -1, false)); // 8 of Staves
		Variables.cards.add(new Card(null, 9, 3, -1, false)); // 9 of Staves
		Variables.cards.add(new Card(null, 10, 3, -1, false)); // 10 of Staves
		Variables.cards.add(new Card(null, 11, 3, -1, false)); // 11 of Staves
		Variables.cards.add(new Card("Commander", 12, 3, -1, true)); // Commander of Staves
		Variables.cards.add(new Card("Mistress", 13, 3, -1, true)); // Mistress of Staves
		Variables.cards.add(new Card("Master", 14, 3, -1, true)); // Master of Staves
		Variables.cards.add(new Card("Ace", 15, 3, -1, true)); // Ace of Staves
		
		Variables.cards.add(new Card("The Star", -17, -1, 0, false)); // The Star (copy 1)
		Variables.cards.add(new Card("The Star", -17, -1, 1, false)); // The Star (copy 2)
		
		Variables.cards.add(new Card("The Evil One", -15, -1, 0, false)); // The Evil One (copy 1)
		Variables.cards.add(new Card("The Evil One", -15, -1, 1, false)); // The Evil One (copy 2)
		
		Variables.cards.add(new Card("Moderation", -14, -1, 0, false)); // Moderation (copy 1)
		Variables.cards.add(new Card("Moderation", -14, -1, 1, false)); // Moderation (copy 2)
		
		Variables.cards.add(new Card("Demise", -13, -1, 0, false)); // Demise (copy 1)
		Variables.cards.add(new Card("Demise", -13, -1, 1, false)); // Demise (copy 2)
		
		Variables.cards.add(new Card("Balance", -11, -1, 0, false)); // Balance (copy 1)
		Variables.cards.add(new Card("Balance", -11, -1, 1, false)); // Balance (copy 2)
		
		Variables.cards.add(new Card("Endurance", -8, -1, 0, false)); // Endurance (copy 1)
		Variables.cards.add(new Card("Endurance", -8, -1, 1, false)); // Endurance (copy 2)
		
		Variables.cards.add(new Card("Queen of Air and Darkness", -2, -1, 0, false)); // Queen of Air and Darkness (copy 1)
		Variables.cards.add(new Card("Queen of Air and Darkness", -2, -1, 1, false)); // Queen of Air and Darkness (copy 2)
		
		Variables.cards.add(new Card("Idiot", 0, -1, 0, false)); // Idiot (copy 1)
		Variables.cards.add(new Card("Idiot", 0, -1, 1, false)); // Idiot (copy 2)
	}
	
	public void dealTwoCardsToEach()
	{
		for (int i = 0; i < Variables.players.size(); i++)
		{
			Random rand = new Random();
			
			int randNum = rand.nextInt(Variables.cards.size());
			Variables.players.get(i).hand.add(Variables.cards.get(randNum));
			Variables.cards.remove(randNum);
			
			randNum = rand.nextInt(Variables.cards.size());
			Variables.players.get(i).hand.add(Variables.cards.get(randNum));
			Variables.cards.remove(randNum);
		}
	}
	
	public void hitOrStand()
	{
		for (int i = 0; i < Variables.players.size(); i++)
		{
			int score = 0;
			
			for (int j = 0; j < Variables.players.get(i).hand.size(); j++)
			{
				score += Variables.players.get(i).hand.get(j).value;
			}
			
			System.out.println("Player " + (i + 1) + ", your cards have a score of " + score + ".");
			
			Scanner scanner = new Scanner(System.in);
			System.out.println("Would you like to hit or stand?");
			
			while (true)
			{
				String ans = scanner.nextLine();
				
				if (ans.equalsIgnoreCase("hit"))
				{
					Random rand = new Random();
					int randNum = rand.nextInt(Variables.cards.size());
					Variables.players.get(i).hand.add(Variables.cards.get(randNum));
				}
				else if (ans.equalsIgnoreCase("stand"))
				{
					Variables.players.get(i).fold = true;
					break;
				}
				else
				{
					System.out.println("That is not an acceptable answer.");
				}
			}
			
			scanner.close();
		}
	}
	
	public void decideCalling()
	{
		for (int i = 0; i < Variables.players.size(); i++)
		{
			System.out.println("Player " + (i + 1) + ", would you like to call?");
			Scanner scanner = new Scanner(System.in);
			
			while (true)
			{
				String ans = scanner.nextLine();
				
				if (ans.equalsIgnoreCase("yes"))
				{
					System.out.println("The calling round has begun!");
					callingRound(i);
					break;
				}
				else if (ans.equalsIgnoreCase("no"))
				{
					System.out.println("Player " + (i + 1) + " has decided not to call.");
					System.out.println("Next player.");
					break;
				}
				else
				{
					System.out.println("That is not an acceptable answer.");
				}
			}
			
			scanner.close();
		}
	}
	
	public void callingRound(int beginningPlayer)
	{
		Variables.callingPhase = true;
		
		if (beginningPlayer == Variables.players.size())
		{
			callingPlayer(beginningPlayer);
			
			for (int i = 0; i < Variables.players.size() - 1; i++)
			{
				callingPlayer(i);
			}
		}
		else if (beginningPlayer == 0)
		{
			for (int i = 0; i < Variables.players.size(); i++)
			{
				callingPlayer(i);
			}
		}
		else
		{
			for (int i = beginningPlayer; i < Variables.players.size(); i++)
			{
				callingPlayer(i);
			}
			
			for (int i = 0; i < beginningPlayer; i++)
			{
				callingPlayer(i);
			}
		}
	}
	
	public void callingPlayer(int playerRef)
	{
		System.out.print("Player ");
		System.out.print(playerRef + 1);
		System.out.print(" has ");
		
		for (int i = 0; i < Variables.players.get(playerRef).hand.size(); i++)
		{
			if (Variables.players.get(playerRef).hand.get(i).name == null)
			{
				// Not a special or face card
				System.out.print("The ");
				System.out.print(Variables.players.get(playerRef).hand.get(i).value);
				System.out.print(" of ");
				
				if (Variables.players.get(playerRef).hand.get(i).suit == 0)
				{
					System.out.print("Sabers, ");
				}
				else if (Variables.players.get(playerRef).hand.get(i).suit == 1)
				{
					System.out.print("Flasks, ");
				}
				else if (Variables.players.get(playerRef).hand.get(i).suit == 2)
				{
					System.out.print("Coins, ");
				}
				else if (Variables.players.get(playerRef).hand.get(i).suit == 3)
				{
					System.out.print("Staves, ");
				}
			}
			else
			{
				if (Variables.players.get(playerRef).hand.get(i).face)
				{
					// Face card
					System.out.print("The ");
					System.out.print(Variables.players.get(playerRef).hand.get(i).name);
					System.out.print(" of ");
					
					if (Variables.players.get(playerRef).hand.get(i).suit == 0)
					{
						System.out.print("Sabers, ");
					}
					else if (Variables.players.get(playerRef).hand.get(i).suit == 1)
					{
						System.out.print("Flasks, ");
					}
					else if (Variables.players.get(playerRef).hand.get(i).suit == 2)
					{
						System.out.print("Coins, ");
					}
					else if (Variables.players.get(playerRef).hand.get(i).suit == 3)
					{
						System.out.print("Staves, ");
					}
				}
				else
				{
					// Special card
					System.out.print("The ");
					System.out.print(Variables.players.get(playerRef).hand.get(i).copy + 1);
					
					if (Variables.players.get(playerRef).hand.get(i).copy == 0)
					{
						System.out.print("st ");
					}
					else if (Variables.players.get(playerRef).hand.get(i).copy == 1)
					{
						System.out.print("nd ");
					}
					
					System.out.print(" copy of ");
					System.out.print(Variables.players.get(playerRef).hand.get(i).name);
					System.out.print(", ");
				}
			}
		}
		
		int score = 0;
		
		for (int i = 0; i < Variables.players.get(playerRef).hand.size(); i++)
		{
			score += Variables.players.get(playerRef).hand.get(i).value;
		}
		
		System.out.print(" with a score of ");
		System.out.print(score);
		System.out.println(".");
		
		if (score > 23 || score < -23)
		{
			System.out.print("Player ");
			System.out.print(playerRef + 1);
			System.out.println(" has bombed out!");
			Variables.players.get(playerRef).bombedout = true;
			Variables.players.get(playerRef).money -= Variables.mainPot;
			Variables.sabaccPot += Variables.mainPot;
		}
	}
	
	public void endRound()
	{
		for (int i = 0; i < Variables.players.size(); i++)
		{
			if (Variables.players.get(i).bombedout)
			{
				System.out.print("Player ");
				System.out.print(i + 1);
				System.out.print(" has bombed out. They do not win.");
			}
			else
			{
				for (int j = 0; j < Variables.players.get(i).hand.size(); j++)
				{
					Variables.players.get(i).score += Variables.players.get(i).hand.get(j).value;
				}
			}
		}
		
		Player[] winningHands = new Player[3];
		
		for (int i = 0; i < Variables.players.size(); i++)
		{
			if (Variables.players.get(i).score == 23)
			{
				winningHands[1] = Variables.players.get(i);
			}
			else if (Variables.players.get(i).score == -23)
			{
				winningHands[2] = Variables.players.get(i);
			}
			else
			{
				boolean idiot = false;
				boolean two = false;
				boolean three = false;
				boolean sameSuit = false;
				int suit = -1;
				
				for (int j = 0; j < Variables.players.get(i).hand.size(); j++)
				{
					if (Variables.players.get(i).hand.get(j).name == "Idiot")
					{
						idiot = true;
					}
					else if (Variables.players.get(i).hand.get(j).value == 2 && Variables.players.get(i).hand.get(j).name == null)
					{
						two = true;
						
						if (three)
						{
							if (Variables.players.get(i).hand.get(j).suit == suit)
							{
								sameSuit = true;
							}
							else
							{
								sameSuit = false;
							}
						}
						else
						{
							suit = Variables.players.get(i).hand.get(j).suit;
						}
					}
					else if (Variables.players.get(i).hand.get(j).value == 3 && Variables.players.get(i).hand.get(j).name == null)
					{
						three = true;
						
						if (two)
						{
							if (Variables.players.get(i).hand.get(j).suit == suit)
							{
								sameSuit = true;
							}
							else
							{
								sameSuit = false;
							}
						}
						else
						{
							suit = Variables.players.get(i).hand.get(j).suit;
						}
					}
				}
				
				if (idiot && two && three && sameSuit)
				{
					winningHands[0] = Variables.players.get(i);
				}
			}
		}
		
		boolean noIdiotsArray = true;
		boolean noPositivePureSabacc = true;
		boolean noNegativePureSabacc = true;
		
		if (winningHands[0] != null)
		{
			noIdiotsArray = false;
		}
		else if (winningHands[1] != null)
		{
			noPositivePureSabacc = false;
		}
		else if (winningHands[2] != null)
		{
			noNegativePureSabacc = false;
		}
		
		if (!noIdiotsArray)
		{
			int num = 0;
			
			for (int i = 0; i < Variables.players.size(); i++)
			{
				if (winningHands[0] == Variables.players.get(i))
				{
					num = i;
				}
			}
			
			System.out.print("Player ");
			System.out.print(num + 1);
			System.out.println(" has won with an Idiot's Array!");
			Variables.players.get(num).money += Variables.mainPot;
			Variables.mainPot = 0;
			Variables.players.get(num).money += Variables.sabaccPot;
			Variables.sabaccPot = 0;
			System.out.print("Player ");
			System.out.print(num + 1);
			System.out.print(" collects the Sabacc Pot and the Main Pot, bringing their wealth to ");
			System.out.print(Variables.players.get(num).money);
			System.out.println(" credits.");
		}
		else
		{
			if (!noPositivePureSabacc)
			{
				int num = 0;
				
				for (int i = 0; i < Variables.players.size(); i++)
				{
					if (winningHands[1] == Variables.players.get(i))
					{
						num = i;
					}
				}
				
				System.out.print("Player ");
				System.out.print(num + 1);
				System.out.println(" has won with a Pure Sabacc (23)!");
				Variables.players.get(num).money += Variables.mainPot;
				Variables.mainPot = 0;
				Variables.players.get(num).money += Variables.sabaccPot;
				Variables.sabaccPot = 0;
				System.out.print("Player ");
				System.out.print(num + 1);
				System.out.print(" collects the Sabacc Pot and the Main Pot, bringing their wealth to ");
				System.out.print(Variables.players.get(num).money);
				System.out.println(" credits.");
			}
			else
			{
				if (!noNegativePureSabacc)
				{
					int num = 0;
					
					for (int i = 0; i < Variables.players.size(); i++)
					{
						if (winningHands[2] == Variables.players.get(i))
						{
							num = i;
						}
					}
					
					System.out.print("Player ");
					System.out.print(num + 1);
					System.out.println(" has won with a Pure Sabacc (-23)!");
					Variables.players.get(num).money += Variables.mainPot;
					Variables.mainPot = 0;
					Variables.players.get(num).money += Variables.sabaccPot;
					Variables.sabaccPot = 0;
					System.out.print("Player ");
					System.out.print(num + 1);
					System.out.print(" collects the Sabacc Pot and the Main Pot, bringing their wealth to ");
					System.out.print(Variables.players.get(num).money);
					System.out.println(" credits.");
				}
				else
				{
					int[] scores = new int[Variables.players.size()];
					
					for (int i = 0; i < Variables.players.size(); i++)
					{
						scores[i] = Variables.players.get(i).score;
					}
					
					int smallest = scores[0];
					int largest = scores[0];
					
					for (int i = 1; i < scores.length; i++)
					{
						if (scores[i] > largest)
						{
							largest = scores[i];
						}
						else if (scores[i] < smallest)
						{
							smallest = scores[i];
						}
					}
					
					if (largest == Math.abs(smallest))
					{
						int num = 0;
						
						for (int i = 0; i < Variables.players.size(); i++)
						{
							if (largest == Variables.players.get(i).score)
							{
								num = i;
							}
						}
						
						System.out.print("Player ");
						System.out.print(num + 1);
						System.out.print(" won the game by having the largest hand with a score of ");
						System.out.print(Variables.players.get(num).score);
						System.out.println(". They collect the Main Pot only.");
						Variables.players.get(num).money += Variables.mainPot;
						Variables.mainPot = 0;
					}
					else if (largest > Math.abs(smallest))
					{
						int num = 0;
						
						for (int i = 0; i < Variables.players.size(); i++)
						{
							if (largest == Variables.players.get(i).score)
							{
								num = i;
							}
						}
						
						System.out.print("Player ");
						System.out.print(num + 1);
						System.out.print(" won the game by having the largest hand with a score of ");
						System.out.print(Variables.players.get(num).score);
						System.out.println(". They collect the Main Pot only.");
						Variables.players.get(num).money += Variables.mainPot;
						Variables.mainPot = 0;
					}
					else if (largest < Math.abs(smallest))
					{
						int num = 0;
						
						for (int i = 0; i < Variables.players.size(); i++)
						{
							if (largest == Variables.players.get(i).score)
							{
								num = i;
							}
						}
						
						System.out.print("Player ");
						System.out.print(num + 1);
						System.out.print(" won the game by having the largest hand with a score of ");
						System.out.print(Variables.players.get(num).score);
						System.out.println(". They collect the Main Pot only.");
						Variables.players.get(num).money += Variables.mainPot;
						Variables.mainPot = 0;
					}
				}
			}
		}
	}
}