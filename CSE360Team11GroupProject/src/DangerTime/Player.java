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
	
	public Player()
	{
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
	
	public void setName(String name){
		this.name = name;
	}
	
	public int dealDamage(){
		int damage;
		int firstRoll = damageDie1.roll();
		int secondRoll = damageDie2.roll();
		if(hitOrMiss()){
			if(firstRoll == secondRoll){
				damage = (firstRoll + secondRoll)*2;
			} else { 
				damage = firstRoll + secondRoll;
			}
			return damage;
		} else {
			damage = 0;
			System.out.println("You missed because you are bad.");
			return damage;
		}

	}
	
	public Boolean runAttempt(){
		Boolean run;
		int roll = runChance.roll();
		if(roll > RUN_CHANCE){
			run = true;
		} else {
			run = false;
		}
		return run;
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
	
	private Boolean hitOrMiss(){
		Boolean hit;
		int roll = hitChance.roll();
		if(roll > HIT_CHANCE){
			hit = true;
		} else {
			hit = false;
		}
		return hit;
	}
}
