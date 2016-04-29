package DangerTime;

import static org.junit.Assert.*;

import org.junit.Test;

public class DiceTest {

	@Test
	public void constructorTest() 
	{
		Dice die = new Dice(10);
		assertNotNull(die);
	}
	
	@Test
	public void rollTest()
	{
		Dice die = new Dice(10);
		int roll = die.roll();
		boolean assertion = roll <= 10 && roll > -1;
		assertTrue(assertion);
	}
}
