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
	
	public int rollDice()
	{
		return numGenerator.nextInt(diceSize);
	}
}