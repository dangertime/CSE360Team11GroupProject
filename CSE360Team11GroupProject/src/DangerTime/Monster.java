package DangerTime;
import java.util.Random;


public class Monster extends Entity
{
	//constants made for easy modification and game balancing
	private static final String ADJ_TEXT_FILENAME = "monster_adj.txt";
	private static final String MONSTER_INFO_FILENAME = "monsters.txt";
	private static final int MAX_DAMAGE = 10;	//number of sides on damage die
	private static final int HIT_CHANCE = 75;	// if roll on hit die > HIT_CHANCE, monster misses
	private static final int RUN_FIRST_CHANCE = 19;
	private static final int FIGHT_OR_RUN_DIE_SIDES = 20;  //PROBABILITY TO RUN ON FIRST MOVE: 
														   // (RUN_FIRST_CHANCE)/ (FIGHT_OR_RUN_DIE_SIDS)
	
	/*
	 * Data from Super Class:
	 *  int currentHealth;
	 *  int maxHealth;
	 *  String name;
	 *  Dice hitChance;
	 *  Dice runChance;
	 */
	
	private int points;
	private Dice damageDie;
	private Dice fightOrRun;	//dice to determine if monster will stay to fight or run on first move
	private boolean stayAndFight = true; //stores the value for the monster staying to fight on first move
	
	
	/**
	 * Constructor reads text files to generate monster 
	 * attributes. monster type(2nd half of name), 
	 * points, and health are read from monsters.txt
	 */
	public Monster()
	{
		
	}
	
	
	
	
	
	
	
}//end monster class
