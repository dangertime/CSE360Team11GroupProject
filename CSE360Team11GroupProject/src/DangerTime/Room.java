package DangerTime;

public class Room {
	
	private final boolean DEBUG = false;
	
	private static final String EVENT_INFO_FILENAME = "src/events.txt";
	private static final int FIND_OR_FIGHT_CHANCE = 12; // a 1/12th chance of not fighting a monster
	
	private Monster monster;
	private Player player;
	private Dice findOrFight;
	private int isFightingFlag;
	
	public Room(Player player)
	{
		this.player = player;
		this.monster = new Monster();
		this.findOrFight = new Dice();
		this.isFightingFlag = 0;
	}
	
	public void fight()
	{
	    //while(player.isAlive() && monster.isAlive() && monster.stayToFight()) //could be do while loop, up to you
	    
    	    //monster goes first
    	    //mdamageplayer()
    	    
    	    //give menu option for player fighting or running
    	    
    	    //if fight, pdamageMonster()
    	    //else if run end the fight
    	// }
		
	}
	
	// haven't handled how health is given back
	//player will probably need a method to give health back, just call that
	public String event()
	{
		List<String> eventsList = new ArrayList<String>();
		String eventMsg;
		Random numGen = new Random();
		eventsList = parseEventFile();
		eventMsg = eventsList.get(numGen.nextInt(eventsList.size()));
		return eventMsg;
	}
	
	public List<String> parseEventFile()
	{
		List<String> eventFileRead = new ArrayList<String>();
		String fileLine;
		
		try
		{
			// FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(EVENT_INFO_FILENAME);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            while((fileLine = bufferedReader.readLine()) != null)
            {
                if(DEBUG)
                	System.out.println("reading file, current line: " + fileLine);
            	eventFileRead.add(fileLine);
            }   

            // Always close files.
            bufferedReader.close();         
		}
		catch(FileNotFoundException ex){
            System.out.println("Unable to open file '" + EVENT_INFO_FILENAME + "'");                
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
		return eventFileRead;
	} // end of parseEventFile
	
	public void mDamagePlayer()
	{
		player.takeDamage(monster.dealDamage());
	}
	
	public void pDamageMonster()
	{
		monster.takeDamage(player.dealDamage());
	}
	
	/**
	 * Returns whether or not the player should fight.
	 * Return value of 1 means player is fighting.
	 * Return valuse of 0 means player is not fighting
	 */
	public int isFighting() 
	{
		return isFighting;
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
		
		if(findOrFight.roll() == 12) {
			
			isFightingFlag = 0; 
			System.out.print(event());
			
		} else isFightingFlag = 1;
		
	}
}
