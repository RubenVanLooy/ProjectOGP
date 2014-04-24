package worms.model.worm;


import java.util.ArrayList;

import worms.model.Position;
import worms.model.World.World;
import worms.model.World.WorldConstants;
import worms.model.weapon.*;
import be.kuleuven.cs.som.annotate.*;
import be.kuleuven.cs.som.taglet.*;

/**
 * A class of worms which have an x-coordinate, a y-coordinate, a radius, 
 * a direction, a mass, an amount of action points and a name.
 * 
 * @invar	The x-coordinate and y-coordinate of every worm must be valid numbers. <br>
 * 			| isValidNumber(getX()) && isValidNumber(getY())
 * @invar	The radius of every worm must be a valid number and larger than the minimal radius of the worm. <br>
 * 			| isValidNumber(getRadius()) && radius >= minimalRadius
 * @invar	The direction of every worm must be a valid number and must be larger then 0 and smaller then 2 * PI. <br>
 * 			| isValidNumber(getDirection()) && getDirection() > 0 && getDirection() < 2 * Math.PI 
 * @invar	The name of every worm must be a possible name for a worm. <br>
 * 			| Name.isPossibleName(getName())
 * @invar	The mass of every worm must be a valid number. <br>
 * 			| isValidNumber(getMass())
 * @invar	The current action points of every worm must be a valid number and smaller then its max action points. <br>
 * 			| isValidNumber(getCurrentActionPoints()) && getCurrentActionPoints() < getMaxActionPoints()
 * @invar	The current hit points of every worm must be a valid number and smaller then its max hit points. <br>
 * 			| isValidNumber(getCurrentHitPoints()) && getCurrentHitPoints() < getMaxHitPoints()
 * @version 1.0
 * 
 * @author Ruben Van Looy
 */

public class Worm {

	
	/**
	 * Initialize this new worm with the given x-coordinate, y-coordinate, direction, radius and name.
	 * 
	 * @param	world
	 * 			The world this worm is in.
	 * @param 	x
	 * 			The x-coordinate for this new worm.
	 * @param	y
	 * 			he y-coordinate for this new worm.
	 * @param 	direction
	 * 			The direction for this new worm.
	 * @param 	radius
	 * 			The radius for this new worm.
	 * @param 	name
	 * 			The name for this new worm.
	 * @post	The new x-coordinate of this new worm is equal to the given x-coordinate. <br>
	 * 			| new.getX() == x
	 * @post	The new y-coordinate of this new worm is equal to the given y-coordinate. <br>
	 * 			| new.getY() == y
	 * @post	The new direction of this new worm is equal to the given direction. <br>
	 * 			| new.getdirection() == direction
	 * @post	The new radius of this new worm is equal to the given radius. <br>
	 * 			| new.getRadius() == radius
	 * @post	The new name of this new worm is equal to the given name. <br>
	 * 			| new.getName() == name
	 */
	@Raw
	public Worm(World world, double x, double y, double direction, double radius, String name) {
		setX(x);
		setY(y);
		setDirection(direction);
		setRadius(radius);
		setName(name);
		setMass();
		setMaxActionPoints();
		setCurrentActionPoints(getMaxActionPoints());
		setMaxHitPoints();
		setCurrentHitPoints(getCurrentHitPoints());
		initializeWeapons();
	}
	
	/**
	 * Return the x-coordinate of this worm.
	 */
	@Basic @Raw
	public double getX() {
		return position.getX();
	}
	
	/**
	 * set the x-coordinate of this worm to the given x-coordinate.
	 * 				
	 * @param 	xCoordinate
	 * 			The new x-coordinate for this worm.
	 * @post	The new x-coordinate of this worm is equal to the given x-coordinate. <br>
	 * 			| new.getX() == xCoordinate
	 * @throws	IllegalArgumentException
	 * 			The given x-coordinate is not a valid number. <br>
	 * 			| !IsValidNumber(xCoordinate)
	 */
	public void setX(double xCoordinate) throws IllegalArgumentException {
		
		if (!isValidNumber(xCoordinate))
			throw new IllegalArgumentException();
		
		position.setX(xCoordinate);
	}
	
	/**
	 * Return the y-coordinate of this worm.
	 */
	@Basic @Raw
	public double getY() {
		return position.getY();
	}
	
	/**
	 * set the y-coordinate of this worm to the given y-coordinate.
	 * 
	 * @param 	yCoordinate
	 * 			The new y-coordinate for this worm
	 * @Post	The new y-coordinate of this worm is equal to the given y-coordinate. <br>
	 * 			| new.getY() == yCoordinate
	 * @throws	IllegalArgumentException
	 * 			The given y-coordinate is not a valid number. <br>
	 * 			| !IsValidNumber(yCoordinate)
	 */
	public void setY(double yCoordinate) throws IllegalArgumentException {
		
		if (!isValidNumber(yCoordinate))
			throw new IllegalArgumentException();
		
		position.setY(yCoordinate);
	}
	
	/**
	 * Return the position of this worm.
	 */
	public Position getPosition() {
		return position.clone();
	}
	
	/**
	 * Move this worm this over the given amount of steps and subtract the movement's cost.
	 * 
	 * @param 	nbSteps
	 * 			The number of steps to move this worm over.
	 * @effect	Do nbSteps steps <br>
	 * 			| doSteps(nbSteps)
	 * @throws	ArithmeticException
	 * 			The given number of steps is not a valid integer. <br>
	 * 			| nbSteps == int x /0
	 */
	public void move() {
			doSteps(1);
			subtractMovementCost(1);
	}
	
	/**
	 * Do the given amount of steps for this worm.
	 * 
	 * @param 	nbSteps
	 * 			The number of steps to do.
	 * @effect	Do a single step for all nbSteps. <br>
	 * 			| for each step < nbSteps <br>
	 * 			|		doSingleStep
	 */
	private void doSteps(int nbSteps) {
		for(int step = 0; step < nbSteps; step++)
			doSingleStep();
	}
	
	/**
	 * Do a single step in the direction of this worm.
	 * 
	 * @post	The given worm has been moved one step in its direction. <br>
	 * 			| new.getX() == getX() + cos(direction) * radius && <br>
	 * 			| new.getY() == getY() + sin(direction) * radius 
	 */
	private void doSingleStep() {
		
		double horizontalDistance, verticalDistance;

		horizontalDistance = Math.cos(direction) * radius;
		verticalDistance = Math.sin(direction) * radius;

		setX(getX() + horizontalDistance);
		setY(getY() + verticalDistance);

	}
	
	/**
	 * Get the cost to move this worm over the given number of steps.
	 * 
	 * @param 	nbSteps
	 * 			The number of steps for which to get the cost.
	 * @return	The cost of movement for the given worm over nbSteps steps. <br>
	 *  		| result = <br>
	 *  		| nbSteps * [ Math.abs( Math.cos(direction) ) + 4 * Math.abs( Math.sin(direction) ) ]
	 * @note	A step is equal to the worm's radius. <br>
	 * 			Horizontal movement costs 1 action point each step, <br>
	 * 			vertical movement costs 4 action points each step.
	 */
	public int getMovementCost(int nbSteps) {
		
		int cost;
		
		cost = (int) Math.ceil( nbSteps * ( (Math.abs(Math.cos(direction)) + 4 * Math.abs(Math.sin(direction)) ) ) );
		
		return cost;
	}
	
	/**
	 * Subtract the cost of a movement over nbSteps of this worm's current action points.
	 * 
	 * @param 	nbSteps
	 * 			The number of steps to subtract the cost for.
	 * @post	The current amount of action points of this worm is reduced by the cost of the movement over nbSteps steps. <br>
	 * 			| new.getActionPoints == currentActionPoints - getMovementcost(nbSteps)
	 */
	private void subtractMovementCost(int nbSteps) {
		int cost = getMovementCost(nbSteps);
		setCurrentActionPoints(currentActionPoints - cost);
	}
	
	/**
	 * Make this worm jump in it's direction.
	 * 
	 * @post	This worm has 0 action points. <br>
	 * 			| new.getCurrentActionPoints == 0
	 * @effect	Set the given worm's coordinates to the final position of a jump in its direction. <br>
	 * 			| worm.setX(getJumpStep(worm, getJumpTime(worm))[0]) && <br> 
	 * 			| worm.setY(getJumpStep(worm, getJumpTime(worm))[1])
	 * @throws	IllegalStateException
	 * 			This worm can not jump. <br>
	 * 			| !canJump()
	 */
	public void jump(double timeStep) throws IllegalStateException{
		if (!canJump())
			throw new IllegalStateException();
		
		double jumpTime = jumpTime(timeStep);
		Position jumpStep = jumpStep(jumpTime);
		setX(jumpStep.getX());
		setY(jumpStep.getY());
		
		setCurrentActionPoints(0);
	}
	
	/**
	 * Get the jump time of this worm.
	 * 
	 * @return	The time it takes for this worm to complete a jump in its direction. <br>
	 * 			| jumpDistance() / ( getInitialJumpVelocity() * cos(direction))
	 */
	public double jumpTime(double timeStep) throws IllegalStateException{
		if (!canJump())
			throw new IllegalStateException();
		
		double t = 0.;
		Position currentPosition = jumpStep(t);
			
		while (!canLandAt(currentPosition) && world.isInMap(currentPosition, getRadius())) {
			t+= timeStep;
			currentPosition = jumpStep(t);
		}
			
		return t;
		
	}
	
	/**
	 * Check whether this worm can land at the given position.
	 * 
	 * @param 	position
	 * 			The position to be checked.
	 * @return	False if the given position is not adjacent to impassable terrain in this worm's world. <br>
	 * 			| getWorld().isAdjacentToImpassableTerrain(position, getRadius()) <br>
	 * 			False if the distance between the given position and this worm is smaller then this worm's radius. <br>
	 * 			| position.getDistanceFrom(getPosition()) < getRadius() <br>
	 * 			True otherwise.
	 */
	public boolean canLandAt(Position position) {
		if (!getWorld().isAdjacentToImpassableTerrain(position, getRadius()))
			return false;
		if (position.getDistanceFrom(getPosition()) < getRadius())
			return false;
		
		return true;
	}
	
	/**
	 * Get the jump step of this worm after the given time.
	 * 
	 * @param 	t
	 * 			The time the worm has jumped.
	 * 
	 * @return	The jump step of this worm after the given time, by using an array of 2 elements. <br>
	 * 			With the first element being the x-coordinate and the second the y-coordinate. <br>
	 * 			| {getNewXAfterTime(t) , getNewYAfterTime(t)}
	 */
	public Position jumpStep (double t) throws IllegalStateException{
		if (!canJump())
			throw new IllegalStateException();
		
		double newX = getNewXAfterTime(t);
		double newY = getNewYAfterTime(t);

		Position vector = new Position(newX, newY);
		return vector;
	}
	
	/**
	 * Check whether this worm can jump.
	 * 
	 * @return	false
	 * 			If the given worm's direction is smaller then PI. <br>
	 * 			| direction < PI <br>
	 * 			false
	 * 			If this worm's action points are equal to 0. <br>
	 * 			| currentActionPoints == 0
	 *          true
	 * 			In all other cases.
	 */
	public boolean canJump() {
			if (direction > Math.PI)
				return false;
			if (currentActionPoints == 0)
				return false;
			
		return true;
	}
	
	/**
	 * Get the force exerted by this worm when it jumps.
	 * 
	 * @param 	worm
	 * 			The worm to get the force from.
	 * @return	The force that this worm exerts when jumping. <br>
	 * 			| (5 * currentActionPoints) + (mass * WorldConstants.GRAVITATIONAL_CONSTANT)
	 */
	public double getJumpForce() {
		return (5 * currentActionPoints) + (mass * WorldConstants.GRAVITATIONAL_CONSTANT);
	}
	
	/**
	 * Get the initial velocity from a jumping worm.
	 * 
	 * @return	The initial velocity of this worm when jumping. <br>
	 * 			| (getJumpForce / mass) * WormConstants.Time_OF_EXERTING_FORCE
	 */
	public double getInitialJumpVelocity() {
		return (getJumpForce() / mass) * WormConstants.TIME_OF_EXERTING_JUMP_FORCE;
	}
	
	/**
	 * Get the distance this worm jumps.
	 * 
	 * @return	The distance this worm jumps. <br>
	 * 			| (getInitialJumpVelocity ^ 2 * sin(2 * direction)) / WorldConstants.GRAVITATIONAL_CONSTANT
	 */
	public  double getJumpDistance() {
		double initV = getInitialJumpVelocity();
		return (initV * initV * Math.sin(2 * direction)) / WorldConstants.GRAVITATIONAL_CONSTANT;
	}
	
	/**
	 * Get the initial horizontal velocity of this worm.
	 * 
	 * @return	The initial horizontal velocity of this worm. <br>
	 * 			| getInitalJumpVelocity * cos(direction)
	 */
	public  double getInitialHorizontalVelocity() {
		return getInitialJumpVelocity() * Math.cos(direction);
	}
	
	/**
	 * Get the new x-coordinate of this worm after jumping for a given time.
	 * @param 	t
	 * 			The time the worm has jumped.
	 * 
	 * @return	The x-coordinate of this worm after jumping for this time. <br>
	 * 			| getX + getHorizontalJumpVelocity * t
	 */
	public  double getNewXAfterTime(double t) {
		return getX() + (getInitialHorizontalVelocity() * t);
	}
	
	/**
	 * Get the initial vertical velocity of this worm.
	 * 
	 * @return	The initial vertical velocity of this worm. <br>
	 * 			| getInitialJumpVelocity * sin(direction)
	 */
	public  double getInitialVerticalVelocity() {
		return getInitialJumpVelocity() * Math.sin(direction);
	}
	
	/**
	 * Get the new y-coordinate of this worm after jumping for a given time.
	 * @param 	t
	 * 			The time this worm has jumped.
	 * 
	 * @return	The y-coordinate of this worm after jumping for the given time. <br>
	 * 			| getY + getInitalVerticalVelocity * t - (1/2) * WorldConstants.GRAVITATIONAL_CONSTANT * t ^ 2
	 */
	public double getNewYAfterTime(double t) {
		return getY() + (getInitialVerticalVelocity() * t - (1./2.) * WorldConstants.GRAVITATIONAL_CONSTANT * t * t);
	}
	
	/*
	 * A variable registering the position of this worm.
	 */
	private Position position =  new Position(0,0);
	
	/**
	 * Return the direction of this worm.
	 */
	@Basic @Raw
	public double getDirection() {
		return direction;
	}
	
	/**
	 * set the direction of this worm to the given direction
	 * 
	 * @param 	direction
	 * 			The new direction for this worm.
	 * @pre		The given direction must be a valid number. <br>
	 * 			| isValidNumber(direction)
	 * @post	The new direction of this worm is equal to the given direction in the bounds of 0 and 2 * PI. <br>
	 * 			| new.getdirection() == Turn.getDirectionInBounds(direction)
	 */
	private void setDirection(double direction) {
		assert isValidNumber(direction);
		this.direction = getDirectionInBounds(direction);
	}
	
	/**
	 * Turn this worm over the given angle.
	 * 
	 * @param	angle
	 * 			The angle to turn this worm over.
	 * @pre		The given angle must be a valid number. <br>
	 * 			| isValidNumber(angle)
	 * @effect	The angle is added to this worm's direction and the cost of the turn is subtracted form this worm's action points. <br>
	 * 			| new.getDirection() == direction + angle && <br>
	 * 			| subtractTurnCost(angle)
	 */
	public void turn(double angle) {
		assert isValidNumber(angle);
		setDirection(direction + angle);
		subtractTurnCost(angle);
	}
	
	
	/**
	 * Subtract the cost of a turn over the given angle of this worm's action points.
	 * 
	 * @param 	angle
	 * 			The angle to subtract action points for.
	 * @post	The given worm's action points are reduced by the cost of a turn of the given angle. <br>
	 * 			| new.getCurrentActionPoints() == currentActionPoints - calculateTurnCost(angle)
	 */
	private void subtractTurnCost(double angle) {
		int cost = getTurnCost(angle);
		currentActionPoints -= cost;
	}
	
	/**
	 * Calculate the cost of turning this worm over the given angle.
	 * 
	 * @param 	angle
	 * 			The angle to calculate the cost for.
	 * @return	The cost of turning the given worm over the given angle. <br>
	 * 			| result = <br>
	 * 			| abs(((60 * angle) / (PI * 2))) + 1
	 * @note	Turning a worm over an angle of 2 * PI costs 60 action points. <br>
	 * 			Therefore turning the worm over an angle, equal to (2 * PI / f) costs (60 / f) action points.
	 */
	public int getTurnCost(double angle) {
		
		return (int) Math.ceil(Math.abs( (60 * angle) / (Math.PI * 2) ));
	}
	
	/**
	 * Get the direction of this worm in the bounds 0 and 2 * PI
	 * 
	 * @param 	worm
	 * 			The worm to get the direction from.
	 * @post	The given direction is larger then 0 and smaller then 2 * PI. <br>
	 * 			| direction > 0 && direction < 2 * PI
	 * @return	The given direction in the bounds of 0 and 2 * PI
	 */
	@Raw
	public double getDirectionInBounds(double direction) {
		while (direction < 0) {
			direction += 2 * Math.PI;
		}
		while (direction > 2 * Math.PI) {
			direction -= 2 * Math.PI;
		}
		return direction;
	}
	
	/*
	 * Variable registering in which direction this worm is facing.
	 */
	private double direction;
	
	
	/**
	 * Return the radius of this worm.
	 */
	@Basic @Raw
	public double getRadius(){
		return radius;
	}
	
	/**
	 * Return the minimal radius of all worms.
	 */
	@Basic @Raw @Immutable
	public double getMinimalRadius() {
		return minimalRadius;
	}
	
	/**
	 * set the radius of this worm to the given radius.
	 * 
	 * @param 	radius
	 * 			The new radius for this worm.
	 * @post	The new radius of this worm is equal to the given radius. <br>
	 * 			| new.getRadius() == radius
	 * @post	The new mass of this worm is set by using the new radius. <br>
	 * 			| new.getMass() == calculateMass()
	 * @throws	IllegalArgumentException
	 * 			The given radius is not a valid number. <br>
	 * 			| !IsValidNumber(radius)
	 * @throws	IllegalArgumentException
	 * 			The given radius is not a valid radius. <br>
	 * 			| !IsValidRadius(radius)
	 */
	@Raw
	public void setRadius(double radius) throws IllegalArgumentException{
		if (!isValidNumber(radius))
			throw new IllegalArgumentException();
		if (!isPossibleRadius(radius))
			throw new IllegalArgumentException();
		
		this.radius = radius;
		setMass();
	}
	
	/**
	 * Check whether the given radius is valid or not.
	 * 
	 * @param 	radius
	 * 			The radius to be checked.
	 * @return	True if the given radius is larger then or equal to the minimal radius. <br>
	 * 			False if the given radius is smaller then or equal to the minimal radius. <br>
	 * 			| IsValidNumber(radius) && radius > MINIMAL_RADIUS;
	 */
	public boolean isPossibleRadius(double radius) {
		return radius >= minimalRadius;
	}
	
	/*
	 * Variable registering the radius of this worm.
	 */
	private double radius;
	
	/*
	 * Variable registering the minimal radius for all worms.
	 */
	private double minimalRadius = 0.25;
	
	/**
	 * @return 	The mass of this worm. <br>
	 * 			| WormConstants.DENSITY * (4 / 3) * PI * radius ^ 3
	 */
	@Basic @Raw
	public double getMass() {
		return WormConstants.DENSITY * ( (4. / 3.) * Math.PI * Math.pow(radius, 3) );
	}
	
	/**
	 * set the mass of this worm to the given mass.
	 * 
	 * @param 	mass
	 * 			The new mass for this worm.
	 * @post	The new mass of this worm is equal to the given mass. <br>
	 * 			| new.getMass() == mass
	 * @post	The new maximum amount of action points is set by using the new mass. <br>
	 * 			| new.getMaxActionPoints() == new.mass
	 */
	@Raw
	private void setMass() {
		mass = WormConstants.DENSITY * ( (4. / 3.) * Math.PI * Math.pow(radius, 3) );
		setMaxActionPoints();
		setMaxHitPoints();
	}
	
	/*
	 * Variable registering the mass of this worm.
	 */
	private double mass;
	
	/**
	 * @return The name of this worm.
	 */
	@Basic @Raw
	public String getName() {
		return name;
	}
	
	/**
	 * @return	An array of symbols a valid name for a worm can have.
	 */
	@Basic @Immutable
	public char[] getSymbols() {
		return symbols.clone();
	}
	
	/**
	 * set the name of this worm to the given name.
	 * 
	 * @param 	name
	 * 			The new name of this worm.
	 * @post	The new name of this worm is equal to the given name. <br>
	 * 			| new.getName() == name;
	 * @throws	IllegalArgumentException
	 * 			The given name is not a valid name for a worm. <br>
	 * 			| !canHaveAsName(name)
	 */
	@Raw
	public void setName(String name) throws IllegalArgumentException {
		if (!canHaveAsName(name))
			throw new IllegalArgumentException(name);
		
		this.name = name;
	}
	
	/**
	 * Check if the given name is a valid name for a worm.
	 * 
	 * @param 	name
	 * 			The name to be checked.
	 * @return	True if the name is a possible name for a worm. <br>
	 * 			False if the name is not a possible name for a worm. <br>
	 * 			| isPossibleName(name)
	 */
	public boolean canHaveAsName(String name) {
		return isPossibleName(name);
	} 
	
	/**
	 * Checks if the given character is a possible first character for a worm's name. 
	 * 
	 * @param 	character
	 * 			The character to be checked.
	 * @return 	True if this character is an upper case character. <br>
	 * 			False if this character is a lower case character. <br>
	 * 			| Character.isUpperCase(character)
	 */
	public  boolean isPossibleFirstCharacter(char character) {
		return Character.isUpperCase(character);
	}
	
	/**
	 * Check if the given character is a possible character for in a worm's name.
	 * 	
	 * @param 	character
	 * 			The character to check.
	 * @return	True if this character is a valid character. <br>
	 * 			False if this character is not a valid character. <br>
	 * 			| Character.isLetterOrDigit(character) || <br>
	 * 			| for each symbol : getSymbols<br>
	 * 			|	if (character == symbol) <br>
	 * 			|		return true
	 */
	public  boolean isPossibleCharacter(char character) {
		
		for (int index = 0; index < symbols.length; index++) {
			if (character == symbols[index])
				return true;
		}
		
		return Character.isLetterOrDigit(character);
	}
	
	/**
	 * Check if the given name is a possible name for a worm.
	 * 
	 * @param 	name
	 * 			The name to be checked.
	 * @return	True if the first character is a valid first character and if every other character is a valid character and if the name has a valid length. <br>
	 * 			False if the first character is not valid or if the name contains a character which is not valid or the length of the name is not valid. <br>
	 * 			| ! isPossibleFirstCharacter(name.charAt(0)) || for all x isPossibleCharacter(name.charAt(x)) || isValidNameLength(name)
	 */
	public  boolean isPossibleName(String name) {
	
		if (!isPossibleFirstCharacter(name.charAt(0)))
			return false;
		
		if (!isValidNameLength(name))
			return false;
		
		for (int index = 1; index < name.length(); index++)
			if (!isPossibleCharacter(name.charAt(index)))
				return false;
		
		return true;
		
	}
	
	/**
	 * Check whether the given name has a valid length.
	 * 
	 * @param 	name
	 * 			The name to be checked.
	 * @return	true 
	 * 			If the length of the given name is larger then 2 or equal too 2. <br>
	 * 			false
	 * 			If the length of the given name is smaller then 2. <br>
	 * 			| name.length >= 2
	 */
	public  boolean isValidNameLength(String name) {
		return name.length() >= 2;
	}
	
	/*
	 * Array registering all possible symbols (other then letters) in a worm's name.
	 */
	private char[] symbols = {'\'','"',' '};
	
	/*
	 * Variable registering the name of this worm.
	 */
	private String name;
	
	/**
	 * Return the current amount of action points of this worm
	 */
	@Basic @Raw
	public int getCurrentActionPoints() {
		return currentActionPoints;
	}
		
	/**
	 * set the amount of action points of this worm to the given amount of action points.
	 * 
	 * @param 	actionPoints
	 * 			The new amount of action points of this worm.
	 * @post	The new amount of action points is equal to the given amount of action points. <br>
	 * 			| new.getActionPoints() == actionPoints
	 */
	private void setCurrentActionPoints(int actionPoints) {
		if ((actionPoints >= 0) && (actionPoints <= maxActionPoints))
			currentActionPoints = actionPoints;
	}
	
	/**
	 * Return the maximum amount of action points of this worm.
	 */
	public int getMaxActionPoints(){
		return (int) Math.round(getMass());
	}
	
	/**
	 * set the maximum amount of action points, using this worm's mass.
	 * 
	 * @post	The maximum amount of action points is equal to this worm's mass. <br>
	 * 			| new.getMaxActionPoints == getMass()
	 * @post	If the current amount of action points of this worm is larger then the maximum amount of action points of this worm, <br>
	 * 			the current amount of action points of this worm will be set to the maximum. <br>
	 * 			| if (getCurrentActionPoints > getMaxActionPoints) <br>
	 *			| setCurrentActionPoints(getMaxActionPoints)
	 */
	private void setMaxActionPoints() {
		maxActionPoints = (int) Math.round(getMass());
		
		if (currentActionPoints > maxActionPoints)
			currentActionPoints = maxActionPoints;
	}
	
	/*
	 * Variable registering the current amount of action points of this worm.
	 */
	private int currentActionPoints;
	
	/*
	 * Variable registering the maximum amount of action points of this worm.
	 */
	private int maxActionPoints;
	
	/**
	 * Return the current amount of hit points of this worm.
	 */
	public int getCurrentHitPoints() {
		return currentHitPoints;
	}
	
	/**
	 * Set the current amount of hit points of this worm.
	 * 
	 * @param 	hitPoints
	 * 			The hit points to be set.
	 * @post	The current amount of hit points of this worm is equal to the given hit points. <br>
	 * 			| new.getCurrentHitPoints == hitPoints
	 */
	private void setCurrentHitPoints(int hitPoints) {
		if (hitPoints >= 0 && hitPoints <= getMaxHitPoints())
			currentHitPoints = hitPoints;
	}
	
	/**
	 * Return the maximum amount of hit point of this worm.
	 */
	public int getMaxHitPoints() {
		return (int) Math.round(getMass());
	}
	
	/**
	 * Set the maximum amount of hit points of this worm.
	 * 
	 * @post	The maximum amount of hit points of this worm is equal to this worm's mass. <br>
	 * 			| new.getMaxHitPoints == getMass
	 * @post	If the current amount of hit points of this worm is larger then the maximum amount of hit points of this worm, <br>
	 * 			the current amount of hit pointsof this worm is set to the maximum amount. <br>
	 * 			| if (getCurrentHitPoints > getMaxHitPoints) <br>
				| setCurrentHitPoints(getMaxHitPoints)
	 */
	private void setMaxHitPoints() {
		maxHitPoints = (int) Math.round(getMass());
		
		if (currentHitPoints > maxHitPoints)
			currentHitPoints = maxHitPoints;
	}
	
	/*
	 * Variable registering the current amount of hit points of this worm.
	 */
	private int currentHitPoints;
	
	/*
	 * Variable registering the maximum amount of hit points of this worm.
	 */
	private int maxHitPoints;
	
	/**
	 * Check whether the given number is a valid number or not. 
	 * 
	 * @param 	number
	 * 			The given number.
	 * @return	True if the given number is a number. <br>
	 * 			False if the given number is not a number. <br>
	 * 			| !Double.isNaN(number)
	 * 			
	 */
	public boolean isValidNumber(double number) {
		return !Double.isNaN(number);
	}
	
	/**
	 * Return the world this worm is in.
	 */
	public World getWorld() {
		return world;
	}
	
	/**
	 * Add this worm to the given world
	 * 
	 * @param 	world
	 * 			The world to which this worm is to be added.
	 * 
	 */
	public void addTooWorld(World world) {
		if (!canAddTooWorld(world))
			throw new IllegalArgumentException();
		world.addWorm(this);
	}
	
	public boolean canAddTooWorld(World world) {
		if (world == null)
			return false;
		if (world.isTermniated())
			return false;
		if (world.hasAsWorm(this))
			return false;
		if (isTerminated())
			return false;
			
		return true;
	}
	
	/**
	 * Set the world of this worm
	 * 
	 * @param 	world
	 * 			The world to be set.
	 * @post	The world of this worm is equal to the given world. <br>
	 * 			| new.getWorld == world
	 * @throws 	IllegalArgumentException
	 * 			The given world does not contain the given worm. <br>
	 * 			| !world.hasAsWorm(this)
	 */
	public void setWorld(World world) throws IllegalArgumentException {
		if (!world.hasAsWorm(this))
			throw new IllegalArgumentException();
		this.world = world;
	}
	
	/*
	 * A variable registering the world this worm is in.
	 */
	private World world;
	
	/**
	 * Check whether the given weapon is a valid weapon.
	 * 
	 * @param 	weapon
	 * 			The weapon to be checked.
	 * @return	False if the given weapon is not effective. <br>
	 * 			| weapon == null <br>
	 * 			False is the given weapon is terminated. <br>
	 * 			| weapon.isTerminated() <br>
	 * 			True otherwise
	 */
	public boolean isValidWeapon(Weapon weapon) {
		if (weapon == null)
			return false;
		if (weapon.isTerminated())
			return false;
		return true;
	}
	
	/**
	 * Return an arraylist containing the weapons of this worm.
	 */
	public ArrayList<Weapon> getAllWeapons() {
		ArrayList<Weapon> toReturn = new ArrayList<Weapon>(weapons);
		return toReturn;
	}
	
	/**
	 * Add a weapon to this worm's weapons.
	 * 
	 * @param 	weapon
	 * 			The weapon to be added.
	 * @post	This worm's weapon contains the given weapon.
	 * 			| new.getAllWeapons.contains(weapon)
	 * @throws	IllegalArgumentException
	 * 			The given weapon is not a valid weapon. <br>
	 * 			| !isValidWeapon(weapon)
	 * @throws	IllegalArgumentException
	 * 			This worm already has the given weapon. <br>
	 * 			| getAllWeapons.contains(weapon)
	 */
	public void addWeapon(Weapon weapon) {
		if (!isValidWeapon(weapon))
			throw new IllegalArgumentException();
		if (weapons.contains(weapon))
			throw new IllegalArgumentException();
		weapons.add(weapon);
	}
	
	/**
	 * Remove the given weapon of this worms weapons.
	 * 
	 * @param 	weapon
	 * 			The weapon to be removed.
	 * @post	This worm does not have the given weapon. <br>
	 * 			| !new.getAllWeapons.contains(weapon)
	 */
	public void removeWeapon(Weapon weapon) {
		weapons.remove(weapon);
	}
	
	/**
	 * Set the the current weapon to the given weapon of this worm.
	 * 
	 * @param 	weapon
	 * 			The weapon to be set as the current weapon for this worm.
	 * @post	This worm has the given weapon as its current weapon.
	 */
	private void setCurrentWeapon(Weapon weapon) {
		if (!isValidWeapon(weapon))
			throw new IllegalArgumentException();
		currentWeapon = weapon;
	}
	
	/**
	 * Return the weapon this worm currently has selected.
	 */
	public Weapon getCurrentWeapon() {
		return currentWeapon.clone();
	}
	
	/**
	 * Return the index of the given weapon.
	 * 
	 * @param 	weapon
	 * 			The weapon to get the index from.
	 */
	public int getWeaponIndex(Weapon weapon) {
		return weapons.indexOf(weapon);
	}
	
	/**
	 * Select the next weapon of this worm.
	 * 
	 * @post	This worm's current weapon is its next weapon. <br>
	 * 			| new.getCurrentWeapon == getWeaponAt(getWeaponIndex(currentWeapon) + 1)
	 * @post	If the worm had selected the first weapon it now has its first weapon as its current weapon. <br>
	 * 			| if (old.getWeaponIndex = getNbWeapons) <br>
	 * 			| 		new.getCurrentWeapon == getWeaponAt(0)
	 */
	public void selectNextWeapon() {
		int index = getWeaponIndex(currentWeapon);
		index++;
		if (index > getNbWeapons())
			index = 0;
		currentWeapon = getWeaponAt(index);
	}
	
	/**
	 * Return the number of weapons this worm has.
	 */
	public int getNbWeapons() {
		return weapons.size();
	}
	
	/**
	 * Return the weapon of this worm at the given index.
	 * 
	 * @param 	index
	 * 			The index to get the weapon from.
	 */
	public Weapon getWeaponAt(int index) {
		return weapons.get(index).clone();
	}
	
	private void initializeWeapons() {
		Bazooka bazooka = new Bazooka(getDirection(), getPosition(), this);
		Rifle rifle = new Rifle (getDirection(), getPosition(), this);
		
		addWeapon(rifle);
		addWeapon(bazooka);
		
		setCurrentWeapon(rifle);
	}
	
	/*
	 * A variable registering the current weapon of this worm.
	 */
	private Weapon currentWeapon;
	
	/*
	 * An arraylist registering the weapons of this worm.
	 */
	private ArrayList<Weapon> weapons = new ArrayList<Weapon>();
	
	/**
	 * @return	True if this worm is terminated. <br>
	 * 			False otherwise.
	 */
	public boolean isTerminated() {
		return isTerminated;
	}
	
	/**
	 * Terminate  this worm.
	 * 
	 * @post	This worm is terminated. <br>
	 * 			| new.isTerminated 
	 * @post	This worm is no part of it's world. <br>
	 * 			| new.getWorld.hasAsWorm(this) == false
	 * @post	This worm's world is not effective. <br>
	 * 			| getWorld == null
	 * @post	This worm has no weapons anymore. <br>
	 * 			| new.getAllWeapons.isEmpty()
	 */
	public void Terminate() {
		world.removeWorm(this);
		world = null;
		weapons.clear();
		isTerminated = true;
	}
	
	/*
	 * A variable registering wether this worm is terminated or not.
	 */
	private boolean isTerminated = false;
	
	public Worm clone() {
		Worm cloned = new Worm(getWorld(), getX(), getY(), getDirection(), getRadius(), getName());
		cloned.setCurrentActionPoints(getCurrentActionPoints());
		cloned.setCurrentHitPoints(getCurrentHitPoints());
		return cloned;
	}
	
}
