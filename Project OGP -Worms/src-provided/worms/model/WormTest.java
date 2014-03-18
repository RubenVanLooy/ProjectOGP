package worms.model;

import static org.junit.Assert.*;

import org.junit.*;

import worms.util.Util;
import worms.model.*;;

public class WormTest {

	private static final double EPS = Util.DEFAULT_EPSILON;
	
	/*
	 * A variable registering a default worm, used for testing.
	 */
	private static Worm defaultTestWorm;
	
	/**
	 * Set up an immutable test fixture.
	 * 
	 * @Post	The variable defaulTestWorm nor references a new worm which has
	 * 			0,0 as coordinates , faces right and up, has a radius of 2 and is name "Test".
	 * 
	 */
	@BeforeClass
	public static void setUpImmutableFixture() {
		defaultTestWorm = new Worm(0,0, Math.PI / 4,2,"Test");
	}
	
	/*
	 * Variables registering worms facing is the directions up, right, right + up and down.
	 */
	private Worm facingUpWorm;
	private Worm facingRightWorm;
	private Worm facingRightUpWorm;
	private Worm facingDownWorm;
	
	/**
	 * Set up a mutable test fixture.
	 * 
	 * @post	The variable facingUpWorm now references a new worm which has
	 * 			0,0 as coordinates, faces upwards, has a radius of 1 and is named "Test".
	 * @post	The variable facingRightWorm now references a new worm which has
	 * 			0,0 as coordinates, faces right, has a radius of 1 and is named "Test".
	 * @post	The variable facingRightUpWorm now references a new worm which has
	 * 			0,0 as coordinates, faces up and right, has a radius of 2 and is named "Test".
	 * @post	The variable facingDownWorm now references a new worm which has
	 * 			0,0 as coordinates, faces downwards, has a radius of 1 and is named "Test".
	 */
	@Before
	public void setUpMutableFixture(){
		facingUpWorm = new Worm(0,0, Math.PI / 2 ,1,"Test");
		facingRightWorm = new Worm(0,0,0,1,"Test");
		facingRightUpWorm =  new Worm(0,0, Math.PI / 4,2,"Test");
		facingDownWorm = new Worm(0,0, 3 * Math.PI / 2, 1 ,"Test");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_InvalidX() {
		Worm worm = new Worm(0./0.,0,0,1,"Test");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_InvalidY() {
		Worm worm = new Worm(0,0./0.,0,1,"Test");
	}
	
	@Test(expected = AssertionError.class)
	public void testConstructor_InvalidDirection() {
		Worm worm = new Worm(0,0,0./0.,1,"Test");
	}
	
	@Test
	public void testMoveHorizontal() {
		facingRightWorm.move(3);
		assertEquals(3,facingRightWorm.getX(), EPS);
		assertEquals(0, facingRightWorm.getY(), EPS);
	}
	
	@Test
	public void testMoveVertical() {
		facingUpWorm.move(3);
		assertEquals(0, facingUpWorm.getX(), EPS);
		assertEquals(3,facingUpWorm.getY(),EPS);
	}
	
	@Test
	public void testMoveDiagonal() {
		facingRightUpWorm.move(2);
		assertEquals(2*Math.pow(2, 1./2.),facingRightUpWorm.getX(), EPS);
		assertEquals(2*Math.pow(2, 1./2.),facingRightUpWorm.getY(), EPS);
	}
	
	@Test
	public void testHorizontalMovementCost() {
		facingRightWorm.move(3);
		assertEquals(3, facingRightWorm.getMovementCost(3),EPS);
	}
	
	@Test
	public void testVerticalMovementCost() {
		facingUpWorm.move(3);
		assertEquals(12, facingUpWorm.getMovementCost(3),EPS);
	}
	
	@Test
	public void testDiagonalMovementCost() {
		facingRightUpWorm.move(2);
		assertEquals(8,facingRightUpWorm.getMovementCost(2), EPS);
	}
	
	@Test(expected = ArithmeticException.class)
	public void testInvalidNumberOfSteps() {
		defaultTestWorm.move(7/0);
	}
	
	@Test
	public void testCanJump_TrueCase() {
		assertTrue(defaultTestWorm.canJump());
	}
	
	@Test
	public void testCanJump_FalseCase() {
		assertFalse(facingDownWorm.canJump());
		facingRightWorm.jump();
		assertFalse(facingRightWorm.canJump());
	}
	
	@Test(expected = IllegalStateException.class)
	public void testDownwardsJumpException() {
		facingDownWorm.jump();
	}
	
	@Test(expected = IllegalStateException.class)
	public void testNoActionPointsJumpException() {
		facingRightUpWorm.jump();
		facingRightUpWorm.jump();
	}
	
	@Test
	public void testHorizontalJumpEndPosition() {
		facingRightWorm.jump();
		assertEquals(0, facingRightWorm.getX(), EPS);
		assertEquals(0 ,facingRightWorm.getY(), EPS);
	}
	
	@Test
	public void testVerticalJumpEndPosition() {
		facingUpWorm.jump();
		assertEquals(0, facingUpWorm.getX(), EPS);
		assertEquals(0 ,facingUpWorm.getY(), EPS);
	}
	
	@Test
	public void testCalculateMass() {
		assertEquals( 35587.96158 , defaultTestWorm.calculateMass() , EPS);
	}
	
	@Test
	public void testJumpForce() {
		assertEquals( 501315.3511, defaultTestWorm.getJumpForce(), EPS );
	}
	
	@Test
	public void testInitialJumpVelocity() {
		assertEquals(7.043327699, defaultTestWorm.getInitialJumpVelocity() , EPS);
	}
	
	@Test
	public void testJumpDistance() {
		assertEquals(5.459488929, defaultTestWorm.getJumpDistance(), EPS);
	}
	
	@Test
	public void testJumpTime() {
		assertEquals(1.09619822, defaultTestWorm.jumpTime(), EPS);
	}
	
	@Test
	public void testInitialHorizontalVelocity() {
		assertEquals(4.980384778, defaultTestWorm.getInitialHorizontalVelocity(),EPS);
	}
	
	@Test
	public void testInitialVerticalVelocity() {
		assertEquals(4.980384778, defaultTestWorm.getInitialVerticalVelocity(),EPS);
	}
	
	@Test
	public void testNewXAfterTime() {
		assertEquals(2.490192389, defaultTestWorm.getNewXAfterTime(.5),EPS);
	}
	
	@Test
	public void testNewYAfterTime() {
		assertEquals(1.354361139, defaultTestWorm.getNewYAfterTime(.5),EPS);
	}
	
	@Test
	public void testJumpStep() {
		assertEquals(2.490192389, defaultTestWorm.jumpStep(.5)[0],EPS);
		assertEquals(1.354361139, defaultTestWorm.jumpStep(.5)[1],EPS);
	}
	
	@Test
	public void testDiagonalJumpEndPosition() {
		facingRightUpWorm.jump();
		assertEquals(5.45948829,facingRightUpWorm.getX(),EPS);
		assertEquals(0, facingRightUpWorm.getY(),EPS);
	}
	
	@Test
	public void testGetDirectionInBounds_LargeAngle() {
		assertEquals(Math.PI, defaultTestWorm.getDirectionInBounds(11 * Math.PI), EPS);
	}
	
	@Test
	public void testGetDirectionInBounds_NegativeAngle() {
		assertEquals(Math.PI, defaultTestWorm.getDirectionInBounds(-7 * Math.PI),EPS);
	}
	
	@Test
	public void testTurnOverLargeAngle() {
		facingRightWorm.turn(5 * Math.PI);
		assertEquals(Math.PI, facingRightWorm.getDirection(), EPS);
	}
	
	@Test
	public void testTurnOverNegativeAngle() {
		facingRightWorm.turn(-Math.PI);
		assertEquals(Math.PI, facingRightWorm.getDirection(), EPS);
	}
	
	@Test
	public void testTurnCost_PositiveAngle() {
		assertEquals(30, facingRightWorm.getTurnCost(Math.PI),EPS);
	}
	
	@Test
	public void testTurnCost_NegativeAngle() {
		assertEquals(30, facingRightWorm.getTurnCost(-Math.PI),EPS);
	}
	
	@Test
	public void testTurnCost() {
		facingRightWorm.turn(Math.PI);
		assertEquals(4418, facingRightWorm.getCurrentActionPoints(),EPS);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetRadiusException() {
		facingRightWorm.setRadius(facingRightWorm.getMinimalRadius() - 0.1);
	}
	
	@Test
	public void testIsPossibleCharacter_FalseCase() {
		assertFalse(defaultTestWorm.isPossibleCharacter('.'));
		assertFalse(defaultTestWorm.isPossibleCharacter('4'));
	}
	
	@Test
	public void testIsPossibleCharacter_TrueCase() {
		assertTrue(defaultTestWorm.isPossibleCharacter('c'));
		assertTrue(defaultTestWorm.isPossibleCharacter('\''));
		assertTrue(defaultTestWorm.isPossibleCharacter('"'));
		assertTrue(defaultTestWorm.isPossibleCharacter(' '));
	}
	
	@Test
	public void testIsPossibleFirstCharacter_FalseCase() {
		assertFalse(defaultTestWorm.isPossibleFirstCharacter('a'));
		assertFalse(defaultTestWorm.isPossibleFirstCharacter(' '));
		assertFalse(defaultTestWorm.isPossibleFirstCharacter('\''));
		assertFalse(defaultTestWorm.isPossibleFirstCharacter('"'));
	}
	
	@Test
	public void testIsPossibelFirstCharacter_TrueCase() {
		assertTrue(defaultTestWorm.isPossibleFirstCharacter('A'));
	}
	
	@Test
	public void testIsValidNameLength_FalseCase() {
		assertFalse(defaultTestWorm.isValidNameLength("A"));
	}
	
	@Test
	public void testIsValidNameLength_TrueCase() {
		assertTrue(defaultTestWorm.isValidNameLength("Ab"));
	}
	
	@Test
	public void testIsPossibleName_FalseCase() {
		assertFalse(defaultTestWorm.isPossibleName("A"));
		assertFalse(defaultTestWorm.isPossibleName("Ab3"));
		assertFalse(defaultTestWorm.isPossibleName("'A"));
		assertFalse(defaultTestWorm.isPossibleName("abcd"));
	}
	
	@Test
	public void testIsPossibleName_TrueCase() {
		assertTrue(defaultTestWorm.isPossibleName("Abc"));
		assertTrue(defaultTestWorm.isPossibleName("A''c"));
		assertTrue(defaultTestWorm.isPossibleName("Abc\""));
		assertTrue(defaultTestWorm.isPossibleName("A b c"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetNameException() {
		facingRightWorm.setName("a");
	}
	
	@Test
	public void testMaxActionPoints() {
		assertEquals(35588, defaultTestWorm.getMaxActionPoints());
	}
	
	@Test
	public void testIsValidNumber_FalseCase() {
		assertFalse(defaultTestWorm.isValidNumber(Math.pow(-1, 1./2.)));
		assertFalse(defaultTestWorm.isValidNumber(0./0.));
		assertFalse(defaultTestWorm.isValidNumber(Double.NEGATIVE_INFINITY/Double.POSITIVE_INFINITY));
	}
}
