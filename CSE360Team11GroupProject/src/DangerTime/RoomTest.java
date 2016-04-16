package DangerTime;

import static org.junit.Assert.*;

import org.junit.Test;

public class RoomTest {
	// making sure that Room objects are properly instantiated 
	@Test
	public void testConstructor()
	{
		Player player = new Player();
		Room room = new Room(player);
		assertNotNull(room);
	}
	
	// Looking to see if testing mode is enabled
	@Test
	public void verifyTestModeEnabled()
	{
		Player player = new Player();
		Room room = new Room(player);
		assertTrue(room.getTesting());
	}
	
	// Checking that a potion event produces the correct message
	@Test
	public void testEventPotion()
	{
		Player player = new Player();
		Room room = new Room(player);
		room.setTestingEventPotion();
		assertEquals(room.event(), "You found a potion!\n");
	}
	
	// Checking that an empty room event produces the correct message
	@Test
	public void testEventEmptyRoom()
	{
		Player player = new Player();
		Room room = new Room(player);
		room.setTestingEventEmptyRoom();
		assertEquals(room.event(), "This is an empty room, proceed with caution!\n");
	}
	
	// Checking that a gold event produces the correct message
	@Test
	public void testEventGold()
	{
		Player player = new Player();
		Room room = new Room(player);
		room.setTestingEventGold();
		assertEquals(room.event(), "You found some gold!\n");
	}
}
