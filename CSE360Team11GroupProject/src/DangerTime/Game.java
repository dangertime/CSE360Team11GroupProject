package DangerTime;

import java.util.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Game 
{
	private static final String HIGHSCORE_FILE = "highscore.txt";
	private static final int HIGHSCORE_MAX_DISPLAY = 20;
	
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
		System.out.println("It's danger time!");
		System.out.println("\t1: Start game");
		System.out.println("\t2: Highscore");
		System.out.println("\t3: Quit");
	}
	
	/**
	 * Start the game by instantiating the first room.
	 */
	private void play()
	{
		//Allow the player to name themselves for highscore purposes
		System.out.println("What is your name brave hero?");
		while(!scanner.hasNext())
		{
			System.out.println("Please enter a name");;
		}
		player.setName(scanner.nextLine());
		
		//Begin the game
		do
		{
			room = new Room(player);
			//room.start()
		} while(player.isAlive());
		
		//Resolve when the player loses
		System.out.println("You have been slain!");
		System.out.println("Recording your score now...");
		handleStatistics(player);
		System.out.println("Press return/enter to return to the main menu");
		scanner.nextLine();
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
			
			System.out.format("%-8s %8d %8d %8d\n",name,score,rooms,monsters);
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
			FileWriter fileWriter = new FileWriter(HIGHSCORE_FILE);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(t_player.name + "," 
					+ t_player.getScore() + "," 
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
}
