package DangerTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;
import java.io.*;
import sun.audio.*;


public class Monster extends Entity
{
	private final boolean DEBUG = false;
	private final boolean TESTING = false;	//set this to enable testing
	
	private boolean TESTING_HIT = false; //set to true to eliminate randomness for testing purposes
	private boolean TESTING_MISS = false; //set to true to eliminate randomness for testing purposes
	private boolean TESTING_ROOM = false;
	
	//constants made for easy modification and game balancing
	private static final String ADJ_TEXT_FILENAME = "src/monster_adj.txt";
	private static final String MONSTER_INFO_FILENAME = "src/monsters.txt";
	private static final int MAX_DAMAGE = 10;	//number of sides on damage die
	private static final int HIT_CHANCE_DIE_SIZE = 100;	
	private static final int HIT_CHANCE = 75;	// if roll on hit die > HIT_CHANCE, monster misses
	private static final int BOSS_HEALTH_SCALING_FACTOR = 2;
	private static final int RUN_FIRST_CHANCE = 19;
	private static final int FIGHT_OR_RUN_DIE_SIDES = 20;  //PROBABILITY TO RUN ON FIRST MOVE: 
														   // (RUN_FIRST_CHANCE)/ (FIGHT_OR_RUN_DIE_SIDS)
	
	/*Data from Super Class:
	 *  int currentHealth;
	 *  int maxHealth;
	 *  String name;
	 *  Dice hitChance;
	 *  Dice runChance;
	 */

	private int points;
	private Dice damageDie;
	private boolean stayAndFight = true; //stores the value for the monster staying to fight on first move
	
	
	/**
	 * Constructor reads text files to generate monster 
	 * attributes. monster type(2nd half of name), 
	 * points, and health are read from monsters.txt
	 */
	public Monster()
	{
		List<String> monsterInfo = new ArrayList<String>();
		
		monsterInfo = parseInfoFile();
		
		//setting attributes of monster
		this.name = monsterInfo.get(0) + monsterInfo.get(1);
		this.maxHealth = Integer.parseInt(monsterInfo.get(2));
		this.points = Integer.parseInt(monsterInfo.get(3));
		this.damageDie = new Dice(MAX_DAMAGE);
		this.hitChance = new Dice(HIT_CHANCE_DIE_SIZE);
		this.runChance = new Dice(FIGHT_OR_RUN_DIE_SIDES);
		
		//rolling to see if monster runs away
		if(runChance.roll() > RUN_FIRST_CHANCE)
			stayAndFight = false;
		
		//If monster is a boss, scale its health by constant
		if(this.name.contains("Boss"))
			this.maxHealth *= BOSS_HEALTH_SCALING_FACTOR;
		
		//set current health after determining if monster is a boss	
		this.currentHealth = this.maxHealth;


		//code for testing and debug
		if(TESTING){
			/*
			 * Scared Slime,10,1
			 */
			this.stayAndFight = true;
			System.out.println("\n\n!!!!!!!!! WARNING !!!!!!!!!!!!\n"
							  + "!!!   TESTING MODE ENABLED!!!\n"
							  + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n");
			
			if(TESTING_HIT && TESTING_MISS) //if both testing mode are enabled, your results will be invalid
				System.out.println("WARNING\n\n BOTH TESTING MODE ENABLED\nPLEASE DISABLE ONE TESTING MODE!");
		}
		if(DEBUG){			
			System.out.println("\n\n!!!!!!!!! WARNING !!!!!!!!!!!!\n"
							  + "!!!!   DEBUG MODE ENABLED !!!\n"
							  + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n");
		}
		


	}
	
	/**
	 * @return Returns the name of the monster if debug is set to false. If debug is true, returns all attributes of monster. 
	 */
	public String toString(){
		String toPrint = "";
		
		if( (DEBUG || TESTING) && !TESTING_ROOM){
			toPrint += name + " " + maxHealth + " " + 
					   currentHealth + " " + stayAndFight + " " + 
					   points;
		}
		else
			toPrint = this.name;
		
		
		return toPrint;
	}
	
	/**
	 * Method that returns the number of points the monster is worth. Used for when
	 * a player defeats a monster. 
	 * @return Returns number of points monster was worth
	 */
	public int getPoints(){
		
		return this.points;
		
	}
	
	/**
	 * Method that is called to deal damage to player
	 * @return Returns the amount of damage the monster deals to the player
	 */
	public int dealDamage(){
		int damage;
		
		if(hitOrMiss()){
			damage = damageDie.roll();
			
			//~~~~~~ code for unit testing ~~~~~~~~~~~~~~				
			if(TESTING && TESTING_HIT){ //hard code damage for testing purposes. Roll should be tested in Dice JUnit
				damage = 999;
			}
			//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	
			
		}
		else{	//works for TESTING_MISS 
			System.out.println(name + " missed! They should try harder next time...");
			damage = 0;
		}
	
		
		return damage;
	}
	

	/**
	 * True if monsters roll to determine if running before fighting was > threshold 
	 * @return True if monster is fighting, false if monster runs away on first move. 
	 */
	public boolean stayToFight(){
		return stayAndFight;
	}
	
	/*
	 * Internal method used to determine if a mosters damage hits or misses
	 */
	private Boolean hitOrMiss(){
		Boolean hit;
		int roll = hitChance.roll();
		if(roll < HIT_CHANCE){
			hit = true;
		} else {
			hit = false;
		}
		
		//~~~~~~ code for unit testing ~~~~~~~~~~~~~~		
		if(TESTING && TESTING_HIT){
			hit = true;
		}
		else if(TESTING && TESTING_MISS){
			hit = false;
		}
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~		
		
		
		return hit;
	}

	
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// functions for checking test modes
// used for unit testing
	public boolean getTesting(){
		return TESTING;
	}
	
	public boolean getTestingHit(){
		return TESTING_HIT;
	}
	
	public boolean getTestingMiss(){
		return TESTING_MISS;
	}
	
	public void setTestingHit(){
		TESTING_HIT = true;
		TESTING_MISS = false;	//to avoid bugs
	}
	
	public void setTestingMiss(){
		TESTING_MISS = true;
		TESTING_HIT = false;	//to avoid bugs
	}
	
	public void setTestingRoom(){
		TESTING_ROOM = true;
		TESTING_HIT = false;	//to avoid bugs
		TESTING_MISS = false;
	}
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~	

	
	/**
	 * Reads text files to generate name, health, and points.
	 * Should probably be broken into several functions
	 * @return Returns and ArrayList containing information read from file about monster. 
	 * 		-Index 0 = Monster Adjective, 
	 * 		-Index 1 = Monster Name/Type, 
	 * 		-Index 2 = Monsters health, 
	 * 		-Index 3 = Monsters point value
	 */
	private List<String> parseInfoFile(){
		List<String> monsterFileRead = new ArrayList<String>();	//holds lines from monster.txt
		List<String> monsterAdjFileRead = new ArrayList<String>();	//holds lines from adj text file
		List<String> returnParams = new ArrayList<String>();		//list that gets returned
		Random numGen = new Random();				//RNG for selecting monster and adj
		String monsterInfo;							//assigned value from random index of monsterFileRead
		String fileLine;							//string used to store line read from file, added to lists
		int sizeOfMonstList;						//holds size of monsterFileRead
		int sizeOfAdjList;							//holds size of monsterAdjFileRead
		
		if(DEBUG){ //list files in current directory. used to determine where to put text files
			File file = new File(".");
			for(String fileNames : file.list()) System.out.println(fileNames);
		}
		
		//~~~~~~~~~READING MONSTER_INFO_FILE~~~~~~~~~~~~~		
		try{
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(MONSTER_INFO_FILENAME);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            while((fileLine = bufferedReader.readLine()) != null) {
                if(DEBUG)
                	System.out.println("reading file, current line: " + fileLine);
                if(!fileLine.startsWith("#"))
					monsterFileRead.add(fileLine);
            }   

            // Always close files.
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex){
            System.out.println("Unable to open file '" + MONSTER_INFO_FILENAME + "'");                
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
		
		
		//~~~~~~~~~READING MONSTER_ADJ~~~~~~~~~~~~~
		try{
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(ADJ_TEXT_FILENAME);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            while((fileLine = bufferedReader.readLine()) != null) {
                if(DEBUG)
                	System.out.println("reading file, current line: " + fileLine);
                if(!fileLine.startsWith("#"))
					monsterAdjFileRead.add(fileLine);
            }   

            // Always close files.
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex){
            System.out.println("Unable to open file '" + ADJ_TEXT_FILENAME + "'");                
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
		
		
		//~~~~~~get random monster and create the name~~~~~~~~
		
		
		
		if(TESTING){
			
			//add adj of name
			returnParams.add(monsterAdjFileRead.get(0) + " "); 
			
			monsterInfo = monsterFileRead.get(0);
			
		}
		else{

			sizeOfAdjList = monsterAdjFileRead.size();
			
			//add adj of name
			returnParams.add(monsterAdjFileRead.get(numGen.nextInt(sizeOfAdjList)) + " "); 
			
			//sets monsterInfo to string at random index in list
			sizeOfMonstList = monsterFileRead.size();	
			monsterInfo = monsterFileRead.get(numGen.nextInt(sizeOfMonstList)); 
		}


		//~~~~~~~~assign monster attributes~~~~~~~~~~~~		
		StringTokenizer splitMonst = new StringTokenizer(monsterInfo,",");
		
		returnParams.add(splitMonst.nextToken());	//add 2nd half of name
		returnParams.add(splitMonst.nextToken());	//add health
		returnParams.add(splitMonst.nextToken());	//add points value
		
		
		return returnParams;
		
	}//end parse method
	

}//end monster class
