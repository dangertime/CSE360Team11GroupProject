package DangerTime;

import static org.junit.Assert.*;
import org.junit.Test;

public class MonsterTest {

	/*
	 * using http://eclemma.org/ for code coverage
	 * green for fully covered lines,
	 * yellow for partly covered lines (some instructions or branches missed) and
	 * red for lines that have not been executed at all.
	 */
	
	/*
	 * NOTE: The TESTING variable MUST be set to true in the Monster class
	 * 		 in order for these test to work properly
	 */

	
	/*
	 * NOTE 2: Due to the random nature of how the monster class'
	 * 		   data is generated, various testing modes had to be created in
	 * 		   order to facilitate proper unit testing. These testing modes attempt
	 * 		   to remove as much of the randomness as possible while maintaining valid
	 * 		   tests. 
	 */
	
	
	@Test
	public void verifyTestModeEnabled(){
		Monster m = new Monster();
		
		assertTrue(m.getTesting());
	}
	
	@Test
	public void testConstructor(){
		Monster m = new Monster();
		

		assertNotNull(m);
	}
	@Test //doubles as test for toString
	public void testConstructor2(){
		Monster m = new Monster();
		
		assertEquals(m.toString(), "Boss Slime 20 20 true 1");
	}
	
	@Test
	public void testGetPoints() {
		Monster m = new Monster();
		
		assertEquals(m.getPoints(), 1);
	}	
	
	@Test
	public void testDamageHit() {
		Monster m = new Monster();
		m.setTestingHit();
		
		assertEquals(m.dealDamage(), 999);  //expecting results enabled from test mode
	}	
	
	@Test
	public void testDamageMiss() {
		Monster m = new Monster();
		m.setTestingMiss();
		
		assertEquals(m.dealDamage(), 0);
	}	
	
	@Test
	public void testStayToFight() {
		Monster m = new Monster();
		
		
		
		assertTrue( m.stayToFight() );
	}	
	
	@Test
	public void testDamgeRandom() {
		Monster m = new Monster();
		int damageDealt = m.dealDamage();
		
		assertTrue(damageDealt >= 0 && damageDealt < 11 );
	}
	
	@Test
	public void testGetMaxHealth() {
		Monster m = new Monster();
		
		
		assertEquals(m.getMaxHealth(),20);
	}
	
	@Test
	public void testGetCurrentHealth() {
		Monster m = new Monster();
		
		
		assertEquals(m.getHealth(),20);
	}
	
	@Test
	public void testTakeDamage() {
		Monster m = new Monster();
		int damageTaken = 2;
		int currentHealth = m.getHealth();
		
		m.takeDamage(damageTaken);
		
		assertEquals(m.getHealth(),currentHealth - damageTaken);
	}
	
	@Test
	public void testCurrentlyAlive() {
		Monster m = new Monster();
		
		
		assertTrue(m.isAlive());
		
	}
	
	@Test
	public void testNotAlive() {
		Monster m = new Monster();
		m.takeDamage(20);
		
		assertTrue(m.isAlive() == false);
		
	}
	
	@Test
	public void testBarelyAlive() {
		Monster m = new Monster();
		m.takeDamage(19); //1 health left
		
		assertTrue(m.isAlive());
		
	}
	
/*	
	@Test
	public void testDiceRoll(){
		Monster m = new Monster();
		int d;
		
		while(true){
			d = m.dealDamage();
			System.out.println(d);
			if(d > 9)
				break;
		}
			
	} */
}//end JUnit test
