package DangerTime;

public class Room {
	
	private Monster monster;
	private Player player;
	
	public Room(Player player)
	{
		this.player = player;
		this.monster = new Monster();
	}
}
