package sabacc;

import java.util.Scanner;

public class Main
{
	public static void main(String[] args)
	{
		System.out.println("Would you like to run in Tablet mode?");
		String ans = "";
		Scanner scanner = new Scanner(System.in);
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
		else
		{
			System.out.println("That is not an acceptable answer.");
			scanner.close();
			System.exit(0);
		}
	}
}