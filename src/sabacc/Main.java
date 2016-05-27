package sabacc;

import java.util.Scanner;

public class Main
{
	public static void main(String[] args)
	{
		System.out.println("Please input a number to use as the seed, or type 0 and press enter for a random seed.");
		Scanner scanner = new Scanner(System.in);
		Variables.timeSeed = scanner.nextInt();
		
		if (Variables.timeSeed == 0)
		{
			Variables.timeSeed = (int) System.currentTimeMillis();
		}
		else
		{
			scanner.close();
		}
		
		System.out.println("Would you like to run in Tablet mode?");
		String ans = "";
		scanner = new Scanner(System.in);
		ans = scanner.nextLine();
		
		if (ans.equalsIgnoreCase("yes"))
		{
			Tablet t = new Tablet();
			t.start();
			scanner.close();
		}
		else if (ans.equalsIgnoreCase("no"))
		{
			Computer c = new Computer();
			c.start();
			scanner.close();
		}
		else if (ans.equalsIgnoreCase("y"))
		{
			Tablet t = new Tablet();
			t.start();
			scanner.close();
		}
		else if (ans.equalsIgnoreCase("n"))
		{
			Computer c = new Computer();
			c.start();
			scanner.close();
		}
		else
		{
			System.out.println("That is not an acceptable answer.");
			scanner.close();
			System.exit(0);
		}
	}
}