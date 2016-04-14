package DangerTime;

public class Room {
	
	private final boolean DEBUG = false;
	
	private static final String EVENT_INFO_FILENAME = "src/events.txt";
	private static final int FIND_OR_FIGHT_CHANCE = 12; // a 1/12th chance of not fighting a monster
	
	public enum events {POTION, EMPTY_ROOM, GOLD};
	
	static String[] eventStrings = {"You found a potion!\n", 
						"This is an empty room, proceed with caution!\n", 
						"You found some gold!\n"};
	
	Scanner input = new Scanner(System.in);
	
	private Monster monster;
	private Player player;
	private Dice findOrFight;
	private boolean isFightingFlag;
	
	public Room(Player player)
	{
		this.player = player;
		this.monster = new Monster();
		this.findOrFight = new Dice();
		this.isFightingFlag = false;
	}
	
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
    	    mdamageplayer();
    	    
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
    	    	System.out.println("You have fled the battle!");
    	    	isFightingFlag = false;
    	    }
		
	}
	
	// haven't handled how health is given back
	//player will probably need a method to give health back, just call that
//	public String event()
//	{
//		List<String> eventsList = new ArrayList<String>();
//		String eventMsg;
//		Random numGen = new Random();
//		eventsList = parseEventFile();
//		eventMsg = eventsList.get(numGen.nextInt(eventsList.size()));
//		return eventMsg;
//	}
	
//	public List<String> parseEventFile()
//	{
//		List<String> eventFileRead = new ArrayList<String>();
//		String fileLine;
//		
//		try
//		{
//			// FileReader reads text files in the default encoding.
//            FileReader fileReader = 
//                new FileReader(EVENT_INFO_FILENAME);
//
//            // Always wrap FileReader in BufferedReader.
//            BufferedReader bufferedReader = 
//                new BufferedReader(fileReader);
//
//            while((fileLine = bufferedReader.readLine()) != null)
//            {
//                if(DEBUG)
//                	System.out.println("reading file, current line: " + fileLine);
//            	eventFileRead.add(fileLine);
//            }   
//
//            // Always close files.
//            bufferedReader.close();         
//		}
//		catch(FileNotFoundException ex){
//            System.out.println("Unable to open file '" + EVENT_INFO_FILENAME + "'");                
//        }
//        catch(IOException ex){
//            ex.printStackTrace();
//        }
//		return eventFileRead;
//	} // end of parseEventFile
//	
	public void mDamagePlayer()
	{
		int damage = monster.dealDamage();
		System.out.print(monster.toString() + " dealt " + damage + " damage to you!\n");
		player.takeDamage(damage);
	}
	
	public void pDamageMonster()
	{
		int damage = monster.dealDamage();
		System.out.print("You dealt " + damage + " damage to " + monster.toString() + "!\n");
		monster.takeDamage(damage);
	}
	
	/**
	 * Returns whether or not the player should fight.
	 * Return value of true means player is fighting.
	 * Return valuse of false means player is not fighting
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
		
		if(findOrFight.roll() == 12) {
			
			isFightingFlag = false; 
			event();
			
		} else isFightingFlag = true;
		
	}
	
	private void event() 
	{
		
		Random rand = new Random();
		int event = rand.nextInt(3);
		
		case(event) {
			POTION:
				
				System.out.println(eventStrings[POTION]);
				player.increaseHealth(rand.nextInt(4) * 10 + 10;);
				
				break;
				
			EMPTY_ROOM:
				
				System.out.println(eventStrings[EMPTY_ROOM]);
				break;
				
			GOLD:
				
				System.out.println(eventStrings[GOLD]);
				player.increaseScore(rand.nextInt(4) * 10 + 10);
				break;
		}
		
	}
}
