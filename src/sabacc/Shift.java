package sabacc;

import java.util.Random;

public class Shift implements Runnable
{
	private Thread t;
	
	public void run()
	{
		try
		{
			Thread.sleep((long)Variables.timeSeed);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
		if (!Variables.callingPhase)
		{
			for (int i = 0; i < Variables.cards.size(); i++)
			{
				if ((!Variables.cards.get(i).face) && (Variables.cards.get(i).copy == -1) && (Variables.cards.get(i).name == null) && (!Variables.cards.get(i).interField))
				{
					
				}
				else if (Variables.cards.get(i).face)
				{
					System.out.println("Face card");
				}
				else if ((!Variables.cards.get(i).face) && (Variables.cards.get(i).copy != -1))
				{
					System.out.println("Special card");
				}
				else if (Variables.cards.get(i).interField)
				{
					System.out.println("Card is in Interferrence Field, cannot be shifted");
				}
			}
		}
	}
	
	public void start()
	{
		System.out.println("Starting Shift Timer Thread");
		setupShifts();
		
		if (t == null)
		{
			t = new Thread(this, "Shift Timer");
			t.start();
		}
	}
	
	public boolean coinFlip()
	{
		Random rand = new Random();
		int num = rand.nextInt(100);
		
		if ((num % 2) == 0)
		{
			return true; // Heads
		}
		else
		{
			return false; // Tails
		}
	}
	
	public void setupShifts()
	{
		Random rand = new Random();
		int num = rand.nextInt(100);
		
		if (num > 0)
		{
			Variables.shiftNum += 1;
		}
		
		if ((num % 2) == 0)
		{
			Variables.shiftNum += 1;
		}
		
		if ((num % 8) == 0)
		{
			Variables.shiftNum += 1;
		}
		
		int newNum = (int)Math.ceil(num * 0.1);
		
		if (!coinFlip())
		{
			newNum *= -1;
		}
		
		Variables.shiftAmount = newNum;
	}
}