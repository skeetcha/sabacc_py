package sabacc.computer;

import java.util.Scanner;
import sabacc.Player;
import sabacc.Variables;

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
				Variables.players.get(i).money = 0;
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
			else if (i == 1)
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
							Variables.players.get(i - 1).money -= ans1;
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
}