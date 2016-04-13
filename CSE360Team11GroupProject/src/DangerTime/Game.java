package DangerTime;

import java.util.*;

public class Game 
{
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
				displayMenu();
				scanner.nextLine();
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
		do
		{
			room = new Room(player);
			//room.start()
		} while(player.isAlive());
		System.out.println("You have been slain!");
		System.out.println("Press a key to return to the main menu");
		scanner.next();
	}
	
	private void displayHighscores()
	{
		//
	}
	
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
			displayHighscores();
			quit = false;
			break;
		case 3:
			quit = true;
			break;
		default:
			quit = false;
			System.out.println("INVALID INPUT");
		}
		return quit;
	}
}
