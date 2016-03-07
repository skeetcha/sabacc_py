package sabacc;

import java.util.ArrayList;

public class Player
{
	public int money = 0;
	public String name = "";
	public boolean fold = false;
	public boolean bombedout = false;
	public ArrayList<Card> hand = new ArrayList<Card>();
}