package DangerTime;

import java.util.Random;
import java.util.Scanner;

public class Room {
	
	private static final int FIND_OR_FIGHT_CHANCE = 12;  // a 1/12 chance of not fighting a monster
	
	// These variables are used for testing the Room class
	// They are needed to eliminate the randomness for testing purposes
	private final boolean TESTING = true; // set this to true to enable testing
	private boolean TESTING_EVENT_POTION = false;
	private boolean TESTING_EVENT_EMPTY_ROOM = false;
	private boolean TESTING_EVENT_GOLD = false;
	
	
	public enum events {POTION, EMPTY_ROOM, GOLD};
	
	static String[] eventStrings = {"You found a potion!\n", 
									"This is an empty room, proceed with caution!\n", 
									"You found some gold!\n"};
	
	Scanner input = new Scanner(System.in);
	
	private Monster monster;
	private Player player;
	private Dice findOrFight;
	private boolean isFightingFlag;
	
	/** 
	 * Constructor just instantiates all of the member attributes.
	 */
	public Room(Player player)
	{
		this.player = player;
		this.monster = new Monster();
		this.findOrFight = new Dice(FIND_OR_FIGHT_CHANCE);
		this.isFightingFlag = false;
	}
	
	/**
	 * This method is responsible for controlling the battle between the player 
	 * and the monster. It provides some dialogue prompting the player to either
	 * to continue battling until there is a victor or to flee for their lives.
	 * This method is exited when the player dies, the monster dies, the player 
	 * flees, or the player flees.
	 */
	public void fight()
	{
		String inputString;
		
		System.out.println("You have encountered a " + monster.toString());
		System.out.println("Get ready to fight! Press any key to continue...");
		input.next();
	    while(player.isAlive() && monster.isAlive() && monster.stayToFight() && isFightingFlag == true) //could be do while loop, up to you
	    {
    	    System.out.println(monster.toString() + ": Prepare to die!");
	    	//monster goes first
    	    mDamagePlayer();
    	    
    	    //give menu option for player running
	    	System.out.println(monster.toString() + " has " + monster.getHealth() + " health left.");
	    	System.out.println("You have " + player.getHealth() + " health left.");
	    	System.out.println("Would you like to continue or try and flee?");
	    	System.out.println("Press \'f\' to try and flee or \'c\' to continue");
	    	
	    	while(!input.hasNext("c") && !input.hasNext("f"))
			{
				System.out.println("INVALID INPUT");
				System.out.println("Press \'f\' to try and flee or \'c\' to continue");
				input.nextLine();
			}
	    	
			inputString = input.next(); 
	    	
    	    if(inputString.equalsIgnoreCase("c"))
    	    {
    	    	pDamageMonster();
    	    	System.out.println(monster.toString() + " has " + monster.getHealth() + " health left.");
    	    	System.out.println("You have " + player.getHealth() + " health left.");
    	    }
    	    else
    	    {
    	    	if(player.runAttempt())
    	    	{
    	    		System.out.println("You have fled the battle!");
    	    		isFightingFlag = false;
    	    	} else 
    	    	{
    	    		System.out.println("You can't run...ahahahaha...you fool!");
    	    	}
    	    }
	    }
		
	}

    /**
     * Called within the fight() method when it is the monster's turn to attack
     * the player. It prints out the amount of health deducted from the player.
     */    
	public void mDamagePlayer()
	{
		int damage = monster.dealDamage();
		System.out.print(monster.toString() + " dealt " + damage + " damage to you!\n");
		player.takeDamage(damage);
	}
	
	/**
	 * Called within the fight() method when it is the player's turn to attack
	 * the monster. It prints out the amount of health deducted from the monster.
	 */
	public void pDamageMonster()
	{
		int damage = player.dealDamage();
		System.out.print("You dealt " + damage + " damage to " + monster.toString() + "!\n");
		monster.takeDamage(damage);
	}
	
	/**
	 * Returns whether or not the player should fight.
	 * Return value of true means player is fighting.
	 * Return values of false means player is not fighting
	 */
	public boolean isFighting() 
	{
		return isFightingFlag;
	}
	
	/**
	 * Triggers a dice roll that decides whether the player will fight
	 * or not fight and get a prize instead.
	 */
	public void enter() 
	{
		findOrFight();
	}
	
	/**
	 * Sets the findOrFight flag which ultimately decides whether the
	 * player will fight or get a prize.
	 */
	private void findOrFight() 
	{
		if(findOrFight.roll() == 12)
		{
			isFightingFlag = false;
		}
		else
		{
			isFightingFlag = true;
		}
	}
	
	/**
	 * This is called within the findOrFight() method. It randomly chooses one of
	 * the events from the eventString array. Depending on the type of event, 
	 * the player may receive health back or earn some extra points towards their
	 * score.
	 */
	public String event() 
	{
		String eventStr = "";
		Random rand = new Random();
		int randomIndex = rand.nextInt(3);
		//---------UNIT TESTING---------
		if(TESTING && TESTING_EVENT_POTION)
		{
			randomIndex = 0; // forcing a potion event to occur
		}
		else if(TESTING && TESTING_EVENT_EMPTY_ROOM)
		{
			randomIndex = 1; // forcing an empty room event to occur
		}
		else if(TESTING && TESTING_EVENT_GOLD)
		{
			randomIndex = 2; // forcing a gold event to occur
		}
		//------------------------------
		events event = events.values()[randomIndex];
		switch(event) {
		case POTION:
				
				eventStr = eventStrings[randomIndex];

				// making sure that the player receives a nonzero amount of health back
				player.increaseHealth(rand.nextInt(4) * 10 + 10);
				
				break;
				
		case EMPTY_ROOM:
				
				eventStr = eventStrings[randomIndex];
				break;
				
		case GOLD:
				
				eventStr = eventStrings[randomIndex];
				
				// making sure that the player receives a nonzero amount of health back
				player.increaseScore(rand.nextInt(4) * 10 + 10);
				break;
		}
		return eventStr;
	}
	
	// All the methods below are associated with testing the Room class
	public boolean getTesting()
	{
		return TESTING;
	}
	public boolean getTestingEventPotion()
	{
		return TESTING_EVENT_POTION;
	}
	public boolean getTestingEventEmptyRoom()
	{
		return TESTING_EVENT_EMPTY_ROOM;
	}
	public boolean getTestingEventGold()
	{
		return TESTING_EVENT_GOLD;
	}
	public void setTestingEventPotion()
	{
		TESTING_EVENT_POTION = true;
		TESTING_EVENT_EMPTY_ROOM = false;
		TESTING_EVENT_GOLD = false;
	}
	public void setTestingEventEmptyRoom()
	{
		TESTING_EVENT_POTION = false;
		TESTING_EVENT_EMPTY_ROOM = true;
		TESTING_EVENT_GOLD = false;
	}
	public void setTestingEventGold()
	{
		TESTING_EVENT_POTION = false;
		TESTING_EVENT_EMPTY_ROOM = false;
		TESTING_EVENT_GOLD = true;
	}
}
