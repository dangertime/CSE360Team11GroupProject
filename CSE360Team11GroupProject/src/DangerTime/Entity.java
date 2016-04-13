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
	
	/**
	 * Method to determine the current health status of the entity based on the threshold (i.e. 0, meaning dead...)
	 * @return Returns true if entity has health above 0
	 */
	public boolean isAlive(){
		return currentHealth > 0;
	}
	
	/**
	 * Reduced the health of the entity by the amount passed in
	 * @param damageTaken the amount of damage dealt to the entity. 
	 */
	public void takeDamage(int damageTaken){
		currentHealth  -= damageTaken;
	}
	
	
}
