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
					Variables.cards.get(i).value += Variables.shiftAmount;
				}
				else if ((!Variables.cards.get(i).face) && (Variables.cards.get(i).copy == -1) && (Variables.cards.get(i).name == null) && (!Variables.cards.get(i).interField) && ((Variables.cards.get(i).value + Variables.shiftAmount) > 11))
				{
					if ((Variables.cards.get(i).value + Variables.shiftAmount) == 12)
					{
						Card c = Variables.cards.get(i);
						c.name = "Commander";
						c.value = 12;
						c.face = true;
						Variables.cards.set(i, c);
					}
					else if ((Variables.cards.get(i).value + Variables.shiftAmount) == 13)
					{
						Card c = Variables.cards.get(i);
						c.name = "Mistress";
						c.value = 13;
						c.face = true;
						Variables.cards.set(i, c);
					}
					else if ((Variables.cards.get(i).value + Variables.shiftAmount) == 14)
					{
						Card c = Variables.cards.get(i);
						c.name = "Master";
						c.value = 14;
						c.face = true;
						Variables.cards.set(i, c);
					}
					else if ((Variables.cards.get(i).value + Variables.shiftAmount == 15))
					{
						Card c = Variables.cards.get(i);
						c.name = "Ace";
						c.value = 15;
						c.face = true;
						Variables.cards.set(i, c);
					}
					else if ((Variables.cards.get(i).value + Variables.shiftAmount) > 15)
					{
						Card c = Variables.cards.get(i);
						c.name = null;
						c.value = (c.value + Variables.shiftAmount) - 15;
						c.face = false;
						Variables.cards.set(i, c);
					}
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