package DangerTime;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;


public class Monster extends Entity
{
	private final boolean DEBUG = true;

	
	//constants made for easy modification and game balancing
	private static final String ADJ_TEXT_FILENAME = "src/monster_adj.txt";
	private static final String MONSTER_INFO_FILENAME = "src/monsters.txt";
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
		damageDie = new Dice(MAX_DAMAGE);
		this.hitChance = new Dice(HIT_CHANCE);
		
		parseInfoFile();
	}
	public String toString(){
		String toPrint = "";
		
		if(DEBUG){
			toPrint += "Monster: " + name + "\n";
			toPrint += "Max Health: " + maxHealth + "\n";
			toPrint += "Points: " + points + "\n";
		}
		else
			toPrint = this.name;
		
		
		return toPrint;
	}
	/**
	 * Reads text files to generate name, health, and points.
	 * Should probably be broken into several functions
	 */
	private void parseInfoFile(){
		List<String> monsterFileRead = new ArrayList<String>();
		List<String> monsterAdjFileRead = new ArrayList<String>();
		Random numGen = new Random();
		String monsterInfo;
		String fileLine;
		int sizeOfMonstList;
		int sizeOfAdjList;
		
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
		this.name = monsterAdjFileRead.get(numGen.nextInt(sizeOfAdjList)) + " ";
		
		sizeOfMonstList = monsterFileRead.size();	
		monsterInfo = monsterFileRead.get(numGen.nextInt(sizeOfMonstList)); //sets monsterInfo to string at random index in list
		
		
		StringTokenizer splitMonst = new StringTokenizer(monsterInfo,",");

//~~~~~~~~assign monster attributes~~~~~~~~~~~~		
		
		this.name += splitMonst.nextToken();
		this.maxHealth = Integer.parseInt(splitMonst.nextToken());
		this.currentHealth = this.maxHealth;
		this.points = Integer.parseInt(splitMonst.nextToken());
		
		if(DEBUG){ //to check the values of various variable 
			System.out.println("list.size " + monsterFileRead.size());	
			System.out.println("size of list " + sizeOfMonstList);	
			System.out.println("name " + name);	
			System.out.println("current health " + currentHealth);	
			System.out.println("max health " + maxHealth);	
			System.out.println("points " + points);	
		}
		
	}
	

}//end monster class
