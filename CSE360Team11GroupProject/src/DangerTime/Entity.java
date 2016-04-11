package DangerTime;

public abstract class Entity 
{
	protected int currentHealth;
	protected int maxHealth;
	protected String name;
	protected Dice hitChance;
	protected Dice runChance;
	
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
	
}
