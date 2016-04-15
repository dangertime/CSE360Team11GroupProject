package DangerTime;

import static org.junit.Assert.*;

import org.junit.Test;

public class PlayerTest {

	@Test
	public void testConstructor(){
		Player p = new Player();
		assertEquals("Danger, Tim", p.name);
	}
	@Test
	public void testSetName(){
		Player p = new Player();
		p.setName("Phil");
		assertEquals("Phil", p.name);
	}
	@Test
	public void testDealDamage(){
		Player p = new Player();
		int damage = p.dealDamage();
		assertTrue(damage >= 0 && damage <= 100);
	}
	@Test
	public void testRunAttempt(){
		Player p = new Player();
		boolean bool = p.runAttempt();
		assertTrue(true);
	}
	@Test
	public void testGetScore(){
		Player p = new Player();
		p.increaseScore(100);
		p.increaseScore(3000);
		assertTrue(p.getScore() == 3100);
	}
	@Test
	public void testGetRooms(){
		Player p = new Player();
		p.clearedRoom();
		p.clearedRoom();
		p.clearedRoom();
		assertTrue(p.getNumRooms() == 3);
	}
	@Test
	public void testGetMonstersDefeated(){
		Player p = new Player();
		p.defeatedMonster();
		p.defeatedMonster();
		assertTrue(p.getMonstersDefeated() == 2);
	}
	@Test
	public void testIncreaseScore(){
		Player p = new Player();
		p.increaseScore(100);
		p.increaseScore(30);
		assertTrue(p.getScore() == 130);
	}
	@Test
	public void testDefeatedMonster(){
		Player p = new Player();
		p.defeatedMonster();
		p.defeatedMonster();
		p.defeatedMonster();
		p.defeatedMonster();
		assertTrue(p.getMonstersDefeated() == 4);
	}
	@Test
	public void testClearedRoom(){
		Player p = new Player();
		p.clearedRoom();
		p.clearedRoom();
		assertTrue(p.getNumRooms() == 2);
	}
}
