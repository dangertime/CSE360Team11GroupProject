package DangerTime;

public abstract class Entity 
{
	private int currentHealth;
	private int maxHealth;
	private int score;
	private Dice damageDie1;
	private Dice damageDie2;
	private String name;
	
	public void setHealth(int newHealth)
	{
		currentHealth = newHealth;
	}
	
	public int getHealth()
	{
		return currentHealth;
	}
	
	public int getMaxHealth()
	{
		return maxHealth;
	}
	
	public int getScore()
	{
		return score;
	}
}
