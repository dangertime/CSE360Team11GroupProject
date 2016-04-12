package DangerTime;

import static org.junit.Assert.*;

import org.junit.Test;

public class MonsterTest {

	@Test
	public void testConstructor(){
		Monster m = new Monster();
		System.out.println("\n\n\n" + m);
		assertNotNull(m);
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}
}
