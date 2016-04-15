package DangerTime;

import static org.junit.Assert.*;

import org.junit.Test;

public class RoomTest {

	@Test
	public void testConstructor()
	{
		Player player = new Player();
		Room room = new Room(player);
		assertNotNull(room);
	}
	
	@Test
	public void testFight()
	{
		Player player = new Player();
		Room room = new Room(player);
		room.fight();
	}
}
