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
	
	// Checking that pDamageMonster() produces the desired message
	@Test
	public void testPDamageMonster()
	{
		Player player = new Player();
		Room room = new Room(player);
		room.setTestingPDamageMonster();
		assertEquals(room.pDamageMonster(), "You dealt 10 damage to " + (room.getMonsterWithinRoom()).toString() + "!\n");
	}
	
	// Checking that mDamagePlayer() produces the desired message
	@Test
	public void testMDamagePlayer()
	{
		Player player = new Player();
		Room room = new Room(player);
		room.setTestingMDamagePlayer();
		assertEquals(room.mDamagePlayer(), (room.getMonsterWithinRoom()).toString() + " dealt 10 damage to you!\n");

	}
	
	// Making sure that both branches of the FindOrFight() method can be reached
	@Test
	public void testFindOrFightFalse()
	{
		Player player = new Player();
		Room room = new Room(player);
		room.setTestingFindOrFightFalse();
		room.enter();
		assertFalse(room.isFighting());
	}
	
	@Test
	public void testFindOrFightTrue()
	{
		Player player = new Player();
		Room room = new Room(player);
		room.setTestingFindOrFightTrue();
		room.enter();
		assertTrue(room.isFighting());
	}
}
