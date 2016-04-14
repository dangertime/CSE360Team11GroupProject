package DangerTime;

public class Player extends Entity
{
	
	private static final int MAX_HEALTH = 100;
	private static final int HIT_CHANCE = 80;
	private static final int RUN_CHANCE = 40;
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
	
	public Player()
	{
		this.maxHealth = MAX_HEALTH;
		this.currentHealth = this.maxHealth;
		this.damageDie1 = new Dice(MAX_DAMAGE);
		this.damageDie2 = new Dice(MAX_DAMAGE);
		this.hitChance = new Dice(HIT_CHANCE);
		this.runChance = new Dice(RUN_CHANCE);
		this.score = 0;
		this.numRooms = 0;
		this.monstersDefeated = 0;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public int dealDamage(){
		int damage;
		int firstRoll = damageDie1.roll();
		int secondRoll = damageDie2.roll();
		if(firstRoll == secondRoll){
			damage = (firstRoll + secondRoll)*2;
		} else { 
			damage = firstRoll + secondRoll;
		}
		return damage;
	}
	
	public int getScore(){
		return score;
	}
	
	public int getNumRooms(){
		return numRooms;
	}
	
	public int getMonstersDefeated(){
		return monstersDefeated;
	}
	
	public void increaseScore(int points){
		score += points;
	}
	
	public void defeatedMonster(){
		monstersDefeated += 1;
	}
	
	public void clearedRoom(){
		numRooms =+ 1;
	}
}
