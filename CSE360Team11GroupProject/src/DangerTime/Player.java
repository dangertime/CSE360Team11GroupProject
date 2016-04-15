package DangerTime;

public class Player extends Entity
{
	
	private static final int MAX_HEALTH = 100;
	private static final int HIT_CHANCE = 80;
	private static final int RUN_CHANCE = 40;
	private static final int HIT_CHANCE_DIE_SIZE = 100;
	private static final int RUN_CHANCE_DIE_SIZE = 100;
	private static final int MAX_DAMAGE = 10;
	
	/*
	 * Data from Super Class:
	 *  int currentHealth;
	 *  int maxHealth;
	 *  String name;
	 *  Dice hitChance;
	 *  Dice runChance;
	 */
	
	private int score;
	private int numRooms;
	private int monstersDefeated;
	private Dice damageDie1; 
	private Dice damageDie2; 
	
	/**
	 * Constructor to set all the default values for a new player
	 * Sets upthe amount of dice needed and the correct size of each dice
	 */
	public Player()
	{
		this.name = "Danger, Tim";
		this.maxHealth = MAX_HEALTH;
		this.currentHealth = this.maxHealth;
		this.damageDie1 = new Dice(MAX_DAMAGE);
		this.damageDie2 = new Dice(MAX_DAMAGE);
		this.hitChance = new Dice(HIT_CHANCE_DIE_SIZE);
		this.runChance = new Dice(RUN_CHANCE_DIE_SIZE);
		this.score = 0;
		this.numRooms = 0;
		this.monstersDefeated = 0;
	}
	
	/**
	 * Method that is called to set the name of the player
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * Method that is called to deal damage to the monster
	 * @return Returns the amount of damage the player deals to the monster
	 */
	public int dealDamage(){
		int damage;
		int firstRoll = damageDie1.roll();
		int secondRoll = damageDie2.roll();
		damage = firstRoll + secondRoll;
		if(damage == 0)
		{
			System.out.println("You regenerated " + (int)(MAX_HEALTH * 0.1) + " health!");
		}
		else
		{
			if(!hit())
			{
				System.out.println("You missed because you are bad.");
				damage = 0;
			}
		}
		return damage;
	}
	
	/**
	 * Method that is called to check if the player makes a valid run
	 * @return Returns a bool that if true means the player successfully ran
	 */
	public boolean runAttempt(){
		boolean run;
		int roll = runChance.roll();
		if(roll > RUN_CHANCE){
			run = true;
		} else {
			run = false;
		}
		return run;
	}
	
	/**
	 * Method that is called to return the score of the player
	 * @return Returns the the score of the player
	 */
	public int getScore(){
		return score;
	}
	
	/**
	 * Method that is called to return the number of rooms cleared
	 * @return Returns the amount of rooms cleared
	 */
	public int getNumRooms(){
		return numRooms;
	}
	
	/**
	 * Method that is called to return the number of monsters defeated
	 * @return Returns the amount of monsters defeated
	 */
	public int getMonstersDefeated(){
		return monstersDefeated;
	}
	
	/**
	 * Method that is called to increase the score of a player based on the points of the monster defeated
	 */
	public void increaseScore(int points){
		score += points;
	}
	
	/**
	 * Method that is called to record that the player defeated a monster
	 */
	public void defeatedMonster(){
		monstersDefeated += 1;
	}
	
	/**
	 * Method that is called to record that the player has completed a room
	 */
	public void clearedRoom(){
		numRooms += 1;
	}
	
	/**
	 * Increases currentHealth by parameter health.
	 */
	public void increaseHealth(int health) {
		currentHealth += health;
	}
	
	/**
	 * Private method that is called to check if a player will hit or miss on a damage roll
	 * @return Returns a boolean value where if true the player hits if false the player will miss
	 */
	private boolean hit(){
		boolean hit;
		int roll = hitChance.roll();
		if(roll < HIT_CHANCE){
			hit = true;
		} else {
			hit = false;
		}
		return hit;
	}
}
