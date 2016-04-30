package DangerTime;

import java.util.Random;
import java.util.Scanner;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.Thread;

public class Room {
	
	private static final int FIND_OR_FIGHT_CHANCE = 12;  // a 1/12 chance of not fighting a monster
	
	// These variables are used for testing the Room class
	// They are needed to eliminate the randomness for testing purposes
	private final boolean TESTING = false; // set this to true to enable testing
	private boolean TESTING_EVENT_POTION = false;
	private boolean TESTING_EVENT_EMPTY_ROOM = false;
	private boolean TESTING_EVENT_GOLD = false;
	private boolean TESTING_PDAMAGEMONSTER = false;
	private boolean TESTING_MDAMAGEPLAYER = false;
	private boolean TESTING_FIND_OR_FIGHT_FALSE = false;
	private boolean TESTING_FIND_OR_FIGHT_TRUE = false;
	
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
	 * to continue battling until there is a victory or to flee for their lives.
	 * This method is exited when the player dies, the monster dies, the monster 
	 * flees, or the player flees.
	 */
	public void fight()
	{
    	
		try{
		    // open the sound file as a Java input stream
		    String gongFile = "sounds/monsterbattle.wav";
		    InputStream in = new FileInputStream(gongFile);
		 
		    // create an audiostream from the inputstream
		    AudioStream audioStream = new AudioStream(in);
		 
		    // play the audio clip with the audioplayer class
		    AudioPlayer.player.start(audioStream);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		
		String inputString;
		
		System.out.println("You have encountered a " + monster.toString());
	
		addDelay(1500);
		
	    System.out.println(monster.toString() + ": Prepare to die!");
		
		System.out.println("Get ready to fight! Press any key to continue...");
		input.next();
	    while(player.isAlive() && monster.isAlive() && monster.stayToFight() && isFightingFlag == true) 
	    {
    	
	    	addDelay(1500); // delay for 1.5 s
	    	//monster goes first
    	    System.out.println(mDamagePlayer());
    	    
    	    //give menu option for player running
    	    addDelay(1000);
    	    System.out.println("You have " + player.getHealth() + " health left.\n");
	    	addDelay(1500);
	    	System.out.println(monster.toString() + " has " + monster.getHealth() + " health left.\n");
	    	addDelay(1000);
	    	System.out.println("Would you like to continue or try and flee?");
	    	System.out.println("Press \'c\' to continue or \'f\' to try and flee:");
	    	
	    	while(!input.hasNext("c") && !input.hasNext("f"))
			{
	    		addDelay(750);
				System.out.println("INVALID INPUT");
				System.out.println("Press \'c\' to continue or \'f\' to try and flee:");
				input.nextLine();
			}
	    	
			inputString = input.next(); 
	    	
    	    if(inputString.equalsIgnoreCase("c"))
    	    {
    	    	addDelay(1500);
    	    	System.out.println(pDamageMonster());
    	    	addDelay(1000);
    	    	System.out.println(monster.toString() + " has " + monster.getHealth() + " health left.\n");
    	    	addDelay(1000);
    	    	System.out.println("You have " + player.getHealth() + " health left.\n");
    	    }
    	    else
    	    {
    	    	if(player.runAttempt())
    	    	{
    	    		addDelay(1000);
    	    		System.out.println("You have fled the battle!");
    	    		isFightingFlag = false;
    	    	}
    	    	
    	    	else 
    	    	{
    	    		addDelay(1000);
    	    		System.out.println("You can't run...ahahahaha...you fool!");
    	    	}
    	    }
    	    
    	    if(!monster.isAlive()){
    	    	addDelay(1000);
	    		System.out.println("You are victorious!");
	    		
	    		try{
	    		    // open the sound file as a Java input stream
	    		    String gongFile = "sounds/victory.wav";
	    		    InputStream in = new FileInputStream(gongFile);
	    		 
	    		    // create an audiostream from the inputstream
	    		    AudioStream audioStream = new AudioStream(in);
	    		 
	    		    // play the audio clip with the audioplayer class
	    		    AudioPlayer.player.start(audioStream);
	    		}
	    		catch(Exception ex){
	    			ex.printStackTrace();
	    		}	
	    		
	    		addDelay(1500);
	    		
	    		
    	    }
    	    
    	    
	    }
		
	}

    /**
     * Called within the fight() method when it is the monster's turn to attack
     * the player. It prints out the amount of health deducted from the player.
     */    
	public String mDamagePlayer()
	{
		String damageStr = "";
		int damage = monster.dealDamage();
		if(TESTING && TESTING_MDAMAGEPLAYER)
		{
			damage = 10; // removing the randomness of damage (for testing purposes)
		}
		damageStr = monster.toString() + " dealt " + damage + " damage to you!\n";
		player.takeDamage(damage);
		return damageStr;
	}
	
	/**
	 * Called within the fight() method when it is the player's turn to attack
	 * the monster. It prints out the amount of health deducted from the monster.
	 */
	public String pDamageMonster()
	{
		String damageStr = "";
		int damage = player.dealDamage();
		if (TESTING && TESTING_PDAMAGEMONSTER)
		{
			damage = 10; // removing the randomness of damage (for testing purposes)
		}
		damageStr = "You dealt " + damage + " damage to " + monster.toString() + "!\n";
		monster.takeDamage(damage);
		return damageStr;
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
		int findOrFightChance = findOrFight.roll();
		if (TESTING && TESTING_FIND_OR_FIGHT_TRUE)
		{
			findOrFightChance = 2; // forcing isFightingFlag to be true
		}
		else if(TESTING && TESTING_FIND_OR_FIGHT_FALSE)
		{
			findOrFightChance = 12; // forcing isFightingFlag to be false
		}
		if(findOrFightChance == 12)
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
	
	/**
	 * Let's us know what monster is in the room
	 * @return monster within the room
	 */
	public Monster getMonsterWithinRoom()
	{
		return this.monster;
	}
	
	
	/**
	 * Used to enhance text readability
	 * @param timeInMilli how long the program should be suspended
	 */
	private void addDelay(long timeInMilli)
	{
    	try
    	{
    		Thread.sleep(timeInMilli);
    	}
    	catch(InterruptedException ex)
    	{
    		Thread.currentThread().interrupt();
    	}
    	
	}
	
	// All the methods below are associated with testing the Room class
	public boolean getTesting()
	{
		return TESTING;
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
	public void setTestingMDamagePlayer()
	{
		TESTING_MDAMAGEPLAYER = true;
	}
	public void setTestingPDamageMonster()
	{
		TESTING_PDAMAGEMONSTER = true;
	}
	public void setTestingFindOrFightFalse()
	{
		TESTING_FIND_OR_FIGHT_FALSE = true;
		TESTING_FIND_OR_FIGHT_TRUE = false;
	}
	public void setTestingFindOrFightTrue()
	{
		TESTING_FIND_OR_FIGHT_TRUE = true;
		TESTING_FIND_OR_FIGHT_FALSE = false;
	}
}
