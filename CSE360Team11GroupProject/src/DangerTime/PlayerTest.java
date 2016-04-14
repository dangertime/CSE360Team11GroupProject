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
		if(damage >= 0 && damage <= 100){
			assertTrue(true);
		} else {
			assertTrue(false);
		}
	}
	@Test
	public void testRunAttempt(){
		Player p = new Player();
		Boolean bool = p.runAttempt();
		if(bool){
			assertTrue(true);
		} else {
			assertTrue(false);
		}
	}
	@Test
	public void testGetScore(){
		Player p = new Player();
		p.increaseScore(100);
		p.increaseScore(3000);
		if(p.getScore() == 3100){
			assertTrue(true);
		} else {
			assertTrue(false);
		}
	}
	@Test
	public void testGetRooms(){
		Player p = new Player();
		p.clearedRoom();
		p.clearedRoom();
		p.clearedRoom();
		if(p.getNumRooms() == 3){
			assertTrue(true);
		} else {
			assertTrue(false);
		}
	}
	@Test
	public void testGetMonstersDefeated(){
		Player p = new Player();
		p.defeatedMonster();
		p.defeatedMonster();
		if(p.getMonstersDefeated() == 2){
			assertTrue(true);
		} else {
			assertTrue(false);
		}
	}
	@Test
	public void testIncreaseScore(){
		Player p = new Player();
		p.increaseScore(100);
		p.increaseScore(30);
		if(p.getScore() == 130){
			assertTrue(true);
		} else {
			assertTrue(false);
		}
	}
	@Test
	public void testDefeatedMonster(){
		Player p = new Player();
		p.defeatedMonster();
		p.defeatedMonster();
		p.defeatedMonster();
		p.defeatedMonster();
		if(p.getMonstersDefeated() == 4){
			assertTrue(true);
		} else {
			assertTrue(false);
		}
	}
	@Test
	public void testClearedRoom(){
		Player p = new Player();
		p.clearedRoom();
		p.clearedRoom();
		if(p.getNumRooms() == 2){
			assertTrue(true);
		} else {
			assertTrue(false);
		}
	}
}
