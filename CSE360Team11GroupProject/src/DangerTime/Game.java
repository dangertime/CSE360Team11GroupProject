package DangerTime;

import java.util.*;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

public class Game 
{
	private static final String HIGHSCORE_FILE = "src/highscore.txt";
	private static final int HIGHSCORE_MAX_DISPLAY = 20;
	private static final int MONSTER_SCORE_REWARD = 100;
	private static final int EVENT_SCORE_REWARD = 50;
	
	private Player player;
	private Room room;
	private Scanner scanner;
	
	public Game()
	{
		this.player = new Player();
		scanner = new Scanner(System.in);
	}
	
	/**
	 * Begin running the game, starting with the main menu.
	 */
	public void start()
	{
		int input;
		boolean quit;
		do
		{
			displayMenu();
			while(!scanner.hasNextInt())
			{
				System.out.println("INVALID INPUT");
				scanner.nextLine();
				displayMenu();
			}
			input = scanner.nextInt();
			quit = handleInput(input);
		} while(!quit);
	}
	
	/**
	 * Displays the main menu with the options of where to go next.
	 */
	private void displayMenu()
	{
		try{
		    // open the sound file as a Java input stream
		    String gongFile = "sounds/mainmenu.wav";
		    InputStream in = new FileInputStream(gongFile);
		 
		    // create an audiostream from the inputstream
		    AudioStream audioStream = new AudioStream(in);
		 
		    // play the audio clip with the audioplayer class
		    AudioPlayer.player.start(audioStream);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}	
		
		
		
		System.out.println("WELCOME TO DANGER TIME!");
		delay(1000);
		System.out.println("=========================");
		delay(500); System.out.println("\t1: Start game");
		delay(500); System.out.println("\t2: Highscore");
		delay(500); System.out.println("\t3: Quit\n");
	}
	
	/**
	 * Start the game by instantiating the first room.
	 */
	private void play()
	{
		//Allow the player to name themselves for highscore purposes, excluding commas
		System.out.println("What is your name brave hero?");
		System.out.println("Your name must be one word.");
		String name;
		do
		{
			System.out.println("Commas (,); may not be used in your name.");
			scanner.nextLine();
			while(!scanner.hasNext())
			{
				System.out.println("Please enter a name");;
			}
			name = scanner.next();
		} while(name.contains(","));
		player.setName(name);
		
		System.out.println("Welcome, brave " + player.getName() + ".");
		delay(500); System.out.println("Your journey begins now...\n");
		
		//Begin the game
		do
		{
			delay(1000); System.out.println("You enter a new room...\n");
			room = new Room(player);
			room.enter();
			if(room.isFighting())
			{
				room.fight();
				if(player.isAlive())
				{
					player.defeatedMonster();
					player.increaseScore(MONSTER_SCORE_REWARD);
				}
			}
			else
			{
				System.out.println(room.event());
				player.increaseScore(EVENT_SCORE_REWARD);
			}
			if(player.isAlive())
			{
				player.clearedRoom();
				System.out.println("You have cleared the room!\n");
				delay(1000); System.out.println("You proceed to the next room...\n");
			}
			
		} while(player.isAlive());
		
		try{
		    // open the sound file as a Java input stream
		    String gongFile = "sounds/ondeath.wav";
		    InputStream in = new FileInputStream(gongFile);
		 
		    // create an audiostream from the inputstream
		    AudioStream audioStream = new AudioStream(in);
		 
		    // play the audio clip with the audioplayer class
		    AudioPlayer.player.start(audioStream);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}	
		
		//Resolve when the player loses
		System.out.println("You have been slain!");
		System.out.println("Recording your score now...");
		handleStatistics(player);
		delay(1000);
		System.out.println("Returning to the Main menu...\n\n");
		scanner.nextLine();
		delay(3000);
	}
	
	/**
	 * Get input from the user and make sure it is correct
	 * @param t_input input of the user
	 * @return whether the user wishes to quit or not
	 */
	private boolean handleInput(int t_input)
	{
		boolean quit;
		switch (t_input)
		{
		case 1:
			play();
			quit = false;
			break;
		case 2:
			displayHighscores(getHighScores());
			quit = false;
			break;
		case 3:
			quit = true;
			break;
		default:
			quit = false;
			System.out.println("NOT AN OPTION IN MENU");
		}
		return quit;
	}
	
	/**
	 * Print out the hishscores of the highscores list
	 * @param t_highScores List of highscores w/ tokens
	 */
	private void displayHighscores(List<String> t_highScores)
	{
		StringTokenizer stringTokenizer;
		
		//Sort the array for an accurate highscore display
		int n = t_highScores.size();
		while(n > 0)
		{
			int m = 0;
			for(int index = 1; index < n; index++)
			{
				stringTokenizer = new StringTokenizer(t_highScores.get(index - 1), ",");
				int first = Integer.parseInt(stringTokenizer.nextToken());
				
				stringTokenizer = new StringTokenizer(t_highScores.get(index), ",");
				int second = Integer.parseInt(stringTokenizer.nextToken());
				
				if(first < second)
				{
					String temp = t_highScores.get(index - 1);
					t_highScores.set(index - 1, t_highScores.get(index));
					t_highScores.set(index, temp);
					m = index;
				}
			}
			n = m;
		}
		
		//Dont display every highscore, only best
		int loopLimit = t_highScores.size() > HIGHSCORE_MAX_DISPLAY ? HIGHSCORE_MAX_DISPLAY : t_highScores.size();
		
		delay(500);
		System.out.println("Name--------Score----Rooms--Monsters");
		System.out.println("====================================");
		for(int index = 0; index < loopLimit; index++)
		{
			stringTokenizer = new StringTokenizer(t_highScores.get(index), ",");
			
			int score = Integer.parseInt(stringTokenizer.nextToken());
			String name = stringTokenizer.nextToken();
			if(name.length() > 8)
				name = name.substring(0,8);
			int rooms = Integer.parseInt(stringTokenizer.nextToken());
			int monsters = Integer.parseInt(stringTokenizer.nextToken());
			
			delay(250); System.out.format("%-8s %8d %8d %8d\n",name,score,rooms,monsters);
		}
		System.out.print("\n\n");
	}
	
	/**
	 * Read the high scores from the highscores.txt file
	 * @return list of the highscores w/ tokens
	 */
	private List<String> getHighScores()
	{
		List<String> highScores = new ArrayList<String>();
		
		try
		{
			String readLine;
			FileReader fileReader = new FileReader(HIGHSCORE_FILE);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			while((readLine = bufferedReader.readLine()) != null)
			{
				//Ignore comments made in the text file
				if(!readLine.startsWith("#"))
				{
					highScores.add(readLine);
				}
			}
			bufferedReader.close();
		}
		catch (FileNotFoundException ex)
		{
			System.out.println("Unable to open file '" + HIGHSCORE_FILE + "'");
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
		
		return highScores;
	}
	
	/**
	 * Save the statistics of the player to file
	 * @param t_player player to save stats from
	 */
	private void handleStatistics(Player t_player)
	{
		try
		{
			FileWriter fileWriter = new FileWriter(HIGHSCORE_FILE, true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(t_player.getScore() + "," 
					+  t_player.getName() + ","
					+ t_player.getNumRooms() + "," 
					+ t_player.getMonstersDefeated() + "\n");
			bufferedWriter.close();
		}
		catch(FileNotFoundException ex)
		{
			System.out.println("Unable to open file '" + HIGHSCORE_FILE + "'");
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}
	
	private void delay(int milliseconds)
	{
		try
		{
			Thread.sleep(milliseconds);
		}
		catch (InterruptedException ex)
		{
			System.out.println("ERROR IN WAITING!!!!");
		}
	}
}
