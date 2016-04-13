package DangerTime;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;


public class Monster extends Entity
{
	private final boolean DEBUG = false;

	
	//constants made for easy modification and game balancing
	private static final String ADJ_TEXT_FILENAME = "src/monster_adj.txt";
	private static final String MONSTER_INFO_FILENAME = "src/monsters.txt";
	private static final int MAX_DAMAGE = 10;	//number of sides on damage die
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
		this.hitChance = new Dice(HIT_CHANCE);
		this.runChance = new Dice(FIGHT_OR_RUN_DIE_SIDES);
		
		//rolling to see if monster runs away
		if(runChance.roll() > RUN_FIRST_CHANCE)
			stayAndFight = false;
		
		//If monster is a boss, scale its health by constant
		if(this.name.contains("Boss"))
			this.maxHealth *= BOSS_HEALTH_SCALING_FACTOR;
		
		//set current health after determining if monster is a boss	
		this.currentHealth = this.maxHealth;

	}
	
	/**
	 * @return Returns the name of the monster if debug is set to false. If debug is true, returns all attributes of monster. 
	 */
	public String toString(){
		String toPrint = "";
		
		if(DEBUG){
			toPrint += "Monster: " + name + "\n";
			toPrint += "Max Health: " + maxHealth + "\n";
			toPrint += "Current Health: " + currentHealth + "\n";
			toPrint += "Points: " + points + "\n";
		}
		else
			toPrint = this.name;
		
		
		return toPrint;
	}
	
	public int getPoints(){
		
		return this.points;
		
	}
	
	/**
	 * Method that is called to deal damage to player
	 * @return Returns the amount of damage the monster deals to the player
	 */
	public int dealDamage(){
		return damageDie.roll();
	}
	

	/**
	 * True if monsters roll to determine if running before fighting was > threshold 
	 * @return True if monster is fighting, false if monster runs away on first move. 
	 */
	public boolean stayTofight(){
		return stayAndFight;
	}
	
	
	/**
	 * Reads text files to generate name, health, and points.
	 * Should probably be broken into several functions
	 * @return Returns and ArrayList containing information read from file about monster.
	 * 		-Index 0 = Monster Adjective
	 * 		-Index 1 = Monster Name/Type
	 * 		-Index 2 = Monsters health
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
		sizeOfAdjList = monsterAdjFileRead.size();
		
		//add adj of name
		returnParams.add(monsterAdjFileRead.get(numGen.nextInt(sizeOfAdjList)) + " "); 
		
		//sets monsterInfo to string at random index in list
		sizeOfMonstList = monsterFileRead.size();	
		monsterInfo = monsterFileRead.get(numGen.nextInt(sizeOfMonstList)); 
		


//~~~~~~~~assign monster attributes~~~~~~~~~~~~		
		StringTokenizer splitMonst = new StringTokenizer(monsterInfo,",");
		
		returnParams.add(splitMonst.nextToken());	//add 2nd half of name
		returnParams.add(splitMonst.nextToken());	//add health
		returnParams.add(splitMonst.nextToken());	//add points value
		
		
		return returnParams;
/*		
		//this.currentHealth = this.maxHealth;
		//this.points = Integer.parseInt(splitMonst.nextToken());
		
		if(DEBUG){ //to check the values of various variable 
			System.out.println("list.size " + monsterFileRead.size());	
			System.out.println("size of list " + sizeOfMonstList);	
			System.out.println("name " + name);	
			System.out.println("current health " + currentHealth);	
			System.out.println("max health " + maxHealth);	
			System.out.println("points " + points);	
		}
*/		
		
	}//end parse method
	

}//end monster class
