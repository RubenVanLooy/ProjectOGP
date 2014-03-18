package worms.model;

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
 * @version 1.0
 * 
 * @author Ruben Van Looy
 */

public class Worm {

	
	/**
	 * Initialize this new worm with the given x-coordinate, y-coordinate, direction, radius and name.
	 * 
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
	public Worm(double x, double y, double direction, double radius, String name) {
		setX(x);
		setY(y);
		setDirection(direction);
		setRadius(radius);
		setName(name);
		setMass(calculateMass());
		setMaxActionPoints();
		setCurrentActionPoints(maxActionPoints);
	}
	
	/**
	 * Return the x-coordinate of this worm.
	 */
	@Basic @Raw
	public double getX() {
		return xCoordinate;
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
	private void setX(double xCoordinate) throws IllegalArgumentException {
		
		if (!isValidNumber(xCoordinate))
			throw new IllegalArgumentException();
		
		this.xCoordinate = xCoordinate;
	}
	
	/**
	 * Return the y-coordinate of this worm.
	 */
	@Basic @Raw
	public double getY() {
		return yCoordinate;
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
	private void setY(double yCoordinate) throws IllegalArgumentException {
		
		if (!isValidNumber(yCoordinate))
			throw new IllegalArgumentException();
		
		this.yCoordinate = yCoordinate;
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
	public void move(int nbSteps) {
		try {
			doSteps(nbSteps);
			subtractMovementCost(nbSteps);
		}
		catch (ArithmeticException exc) {
			throw exc;
		}
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
	public void jump() throws IllegalStateException{
		if (!canJump())
			throw new IllegalStateException();
		
		double jumpTime = jumpTime();
		double[] jumpStep = jumpStep(jumpTime);
		setX(jumpStep[0]);
		setY(jumpStep[1]);
		
		setCurrentActionPoints(0);
	}
	
	/**
	 * Get the jump time of this worm.
	 * 
	 * @return	The time it takes for this worm to complete a jump in its direction. <br>
	 * 			| jumpDistance() / ( getInitialJumpVelocity() * cos(direction))
	 */
	public double jumpTime() throws IllegalStateException{
		if (!canJump())
			throw new IllegalStateException();
		
		return getJumpDistance() / (getInitialJumpVelocity() * Math.cos(direction));
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
	public double[] jumpStep (double t) throws IllegalStateException{
		if (!canJump())
			throw new IllegalStateException();
		
		double newX = getNewXAfterTime(t);
		double newY = getNewYAfterTime(t);

		double[] vector = {newX,newY};
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
	 * 			| (5 * currentActionPoints) + (mass * WormConstants.GRAVITATIONAL_CONSTANT)
	 */
	public double getJumpForce() {
		return (5 * currentActionPoints) + (mass * WormConstants.GRAVITATIONAL_CONSTANT);
	}
	
	/**
	 * Get the initial velocity from a jumping worm.
	 * 
	 * @return	The initial velocity of this worm when jumping. <br>
	 * 			| (getJumpForce / mass) * WormConstants.Time_OF_EXERTING_FORCE
	 */
	public double getInitialJumpVelocity() {
		return (getJumpForce() / mass) * WormConstants.TIME_OF_EXERTING_FORCE;
	}
	
	/**
	 * Get the distance this worm jumps.
	 * 
	 * @return	The distance this worm jumps. <br>
	 * 			| (getInitialJumpVelocity ^ 2 * sin(2 * direction)) / WormConstants.GRAVITATIONAL_CONSTANT
	 */
	public  double getJumpDistance() {
		double initV = getInitialJumpVelocity();
		return (initV * initV * Math.sin(2 * direction)) / WormConstants.GRAVITATIONAL_CONSTANT;
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
	 * 			| getY + getInitalVerticalVelocity * t - (1/2) * WormConstants.GRAVITATIONAL_CONSTANT * t ^ 2
	 */
	public double getNewYAfterTime(double t) {
		return getY() + (getInitialVerticalVelocity() * t - (1./2.) * WormConstants.GRAVITATIONAL_CONSTANT * t * t);
	}
	
	/*
	 * Variable registering the current x-coordinate of this worm.
	 */
	private double xCoordinate;
	
	/*
	 * Variable registering the current y-coordinate of this worm.
	 */
	private double yCoordinate;
	
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
		setMass(calculateMass());
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
	 * @return The mass of this worm.
	 */
	@Basic @Raw
	public double getMass() {
		return mass;
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
	private void setMass(double mass) {
		this.mass = mass;
		setMaxActionPoints();
	}
	
	/**
	 * Calculate the mass of this worm using its radius.
	 * 
	 * @return 	The mass of this worm. <br>
	 * 			| WormConstants.DENSITY * (4 / 3) * PI * radius ^ 3
	 */
	public double calculateMass() {
		return WormConstants.DENSITY * ( (4. / 3.) * Math.PI * Math.pow(radius, 3) );
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
	 * 			| Character.isLetter(character) || <br>
	 * 			| for each symbol <br>
	 * 			|	if (character == symbol) <br>
	 * 			|		return true
	 */
	public  boolean isPossibleCharacter(char character) {
		
		for (int index = 0; index < symbols.length; index++) {
			if (character == symbols[index])
				return true;
		}
		
		return Character.isLetter(character);
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
		return maxActionPoints;
	}
	
	/**
	 * set the maximum amount of action points, using this worm's mass.
	 * 
	 * @post	The maximum amount of action points is equal to this worm's mass. <br>
	 * 			| new.getMaxActionPoints == getMass()
	 * @effect	If the current amount of action points of this worm is larger then the maximum amount of action points of this worm, <br>
	 * 			the current amount of action points of this worm will be set to the maximum. <br>
	 * 			| if (currentActionPoints > maxActionPoints) <br>
	 *			| currentActionPoints = maxActionPoints;
	 */
	private void setMaxActionPoints() {
		maxActionPoints = (int) Math.round(mass);
		
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
}
