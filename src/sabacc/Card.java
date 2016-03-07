package sabacc;

public class Card
{
	public String name; // null for non special cards
	public int value;
	public int suit; // -1 for special cards
	public int copy; // -1 for non special cards
	public boolean face;
	
	public Card(String n, int v, int s, int c, boolean f)
	{
		this.name = n;
		this.value = v;
		this.suit = s;
		this.copy = c;
		this.face = f;
	}
}