package DangerTime;

import java.util.Random;

public class Dice 
{
	private int diceSize;
	private Random numGenerator;
	
	public Dice(int diceSize)
	{
		this.diceSize = diceSize;
		this.numGenerator = new Random();
	}
	
	/*
	 * possible bug in roll method?
	 * Dice of size 10 rolls 0-9.
	 * Should this be 1-10, since 0 damage
	 * is handled by the chance to miss?
	 */
	public int roll()
	{
		return numGenerator.nextInt(diceSize);
	}
}