package worms.model;

import be.kuleuven.cs.som.annotate.*;
import be.kuleuven.cs.som.taglet.*;

public class Facade implements IFacade{

	public Facade() {
		
	}
	
	/**
	 * Create a worm with the given properties.
	 * 
	 * @param	x
	 * 			The x-coordinate of the new worm.
	 * @param	y
	 * 			The y-coordinate of the new worm.
	 * @param	direction
	 * 			The direction of the new worm.
	 * @param	radius
	 * 			The radius of the new worm.
	 * @param	name
	 * 			The name of the new worm.
	 * @throws	ModelException
	 * 			The given x-coordinate is not a valid number <br>
	 * 			| !worm.isValidNumber(x)
	 * @throws	ModelException
	 * 			The given y-coordinate is not a valid number <br>
	 * 			| !worm.isValidNumber(y) 
	 * @throws	ModelException
	 * 			The given direction is not a valid number <br>
	 * 			| !worm.isValidNumber(direction)
	 * @throws	ModelException
	 * 			The given radius is not a valid number. <br>
	 * 			| !worm.isValidNumber(radius)
	 * @throws	ModelException
	 * 			The given name is not a valid name. <br>
	 * 			| !Name.isPossibleName(name)
	 * @effect	A new worm is initialized by using the given parameters. <br>
	 * 			| new Worm(x, y, direction, radius, name)
	 */
	@Override
	public Worm createWorm(double x, double y, double direction, double radius,
			String name) {
		try {
			Worm worm = new Worm(x,y,direction,radius,name);
			return worm;
		}
		catch (IllegalArgumentException exc){
			throw new ModelException("One of the given properties is invalid.");
		}
		catch (AssertionError exc) {
			throw new ModelException("One of the given properties is invalid.");
		}
		
	}

	/**
	 * Check whether the given worm can move. 
	 * 
	 * @param	worm
	 * 			The worm to be checked.
	 * @param	nbSteps
	 * 			The amount of steps to be checked.
	 * @return	true 
	 * 			If the current amount of action points of the given worm is larger then the cost of the movement, <br>
	 * 			false in all other cases. <br>
	 * 			| worm.getCurrentActionPoints() > calculateMovementCost(worm) * nbSteps
	 * @return	false 
	 * 			If the given worm is not an effective worm <br>
	 * 			| worm == null
	 */
	@Override
	public boolean canMove(Worm worm, int nbSteps) {
		try{
			return (worm.getCurrentActionPoints() > worm.getMovementCost(nbSteps)) && (nbSteps > 0);
		}
		catch (ArithmeticException exc) {
			return false;
		}
		catch (NullPointerException exc) {
			return false;
		}
	}

	/**
	 * Move the given worm the given number of steps in the worm's direction.
	 * 
	 * @param	worm
	 * 			The worm to move.
	 * @param	nbSteps
	 * 			The number of the steps to be moved.
	 * @throws	ModelException
	 * 			The given worm can not move the given number of steps. <br>
	 * 			| !canMove(worm,nbSteps)
	 * @effect	The worm has moved nbSteps steps in its current direction. <br>
	 * 			| worm.move(nbSteps)
	 */
	@Override
	public void move(Worm worm, int nbSteps) {
		try{
			if (!canMove(worm, nbSteps))
			throw new ModelException("The worm can not move.");
		
			worm.move(nbSteps);
		}
		catch(ArithmeticException exc) 
		{
			throw new ModelException("The given number of steps is not a valid number");
		}
	}
	
	/**
	 * Return whether or not the given worm can turn by the given angle.
	 * 
	 * @param 	worm
	 * 			The worm to be checked.
	 * @param 	angle
	 *			The angle to be checked.
	 * @pre		The given worm is an effective worm. <br>
	 * 			| worm != null
	 * @return	true
	 * 			If the current amount of action points of the given worm is larger then the the cost of a turn over the given angle, <br>
	 * 			false in all other cases. <br>
	 * 			| worm.getCurrentActionPoints() < calculateTurnCost(worm, angle)
	 */
	@Override
	public boolean canTurn(Worm worm, double angle) {
		assert worm != null;
		
		if (worm.getCurrentActionPoints() < worm.getTurnCost(angle))
			return false;
			
		return true;
	}

	/**
	 * Turn the given worm over the given angle.
	 * 
	 * @param	worm
	 * 			The worm to be turned
	 * @param	angle
	 * 			The angle to be turned over
	 * @pre		The given worm can turn over the given angle. <br>
	 * 			| canTurn(worm,angle)
	 * @effect	The given worm turns over the given angle. <br>
	 * 			| worm.turn(angle)
	 */
	@Override
	public void turn(Worm worm, double angle) {
		assert canTurn(worm, angle);
		worm.turn(angle);
	}

	/**
	 * Make the given worm jump in it's direction.
	 * 
	 * @param	worm
	 * 			The worm to jump.
	 * @effect	The given worm jumps. <br>
	 * 			| worm.jump()
	 * @throws	ModelException
	 * 			The given worm can not jump. <br>
	 * 			| !worm.canJump()
	 */
	@Override
	public void jump(Worm worm) {
		try {
			worm.jump();
		}
		catch(IllegalStateException exc) {
			throw new ModelException("The given worm can not jump.");
		}
	}
	
	/**
	 * Get the time the given worm jumps for.
	 * 
	 * @param	worm
	 * 			The worm to get the jump time from.
	 * 
	 * @return	The time the given worm jumps for. <br>.
	 * 			| worm.jumpTime()
	 * @throws	ModelException
	 * 			The given worm can not jump. <br>
	 * 			| !worm.canJump()
	 */
	@Override
	public double getJumpTime(Worm worm) {
		
		try{
			return worm.jumpTime();
		}
		catch(IllegalStateException exc) {
			throw new ModelException("The given worm can not jump.");
		}
		
	}

	/**
	 * Get the jump step of the given worm after the given time.
	 * 
	 * @param	worm
	 * 			The worm to get the jump step from.
	 * @param	t
	 * 			The time the given worm has jumped.
	 * @return	The jump step of the given worm after the given time, by using an array of 2 elements, <br>
	 * 			with the first element being the x-coordinate and the second the y-coordinate. <br>
	 * 			| worm.jumpStep(t)
	 * @throws	ModelException
	 * 			The given worm can not jump. <br>
	 * 			| !worm.canJump()
	 * @throws	ArithmeticException
	 * 			The given time is not a valid number. <br>
	 * 			| Double.isNaN(t)
	 */
	@Override
	public double[] getJumpStep(Worm worm, double t) {
		try{
			return worm.jumpStep(t);
		}
		catch (ArithmeticException exc) {
			throw new ModelException("The given time is not a valid number");
		}
		catch(IllegalStateException exc) {
			throw new ModelException("The given worm can not jump.");
		}
		
	}

	/**
	 * Return the x-coordinate of the given worm.
	 * 
	 * @param 	worm
	 * 			The worm to get an x coordinate from.
	 * @return	The x coordinate of the given worm. <br>
	 * 			| result = worm.getX()
	 * @throws	ModelException
	 * 			The given worm is not effective. <br>
	 * 			| worm == null
	 */
	@Override @Basic @Raw
	public double getX(Worm worm){
		
		try {
			return worm.getX();
		}
		catch (NullPointerException exc) {
			throw new ModelException("The given worm must be an effective worm.");
		}
		
	}

	/**
	 * Returns the y-coordinate of the given worm.
	 * 
	 * @param	worm
	 * 			The worm to get a y-coordinate from.
	 * @return	The y-coordinate of the given worm. <br>
	 * 			| result = worm.getY()
	 * @throws	ModelException
	 * 			The given worm is not effective. <br>
	 * 			| worm == null
	 */
	@Override
	public double getY(Worm worm) {
		
		try {
			return worm.getY();
		}
		catch (NullPointerException exc){
			throw new ModelException("The given worm must be an effective worm.");
		}
	}

	/**
	 * Return the direction of the given worm.
	 * 
	 * @param 	worm
	 * 			The worm to get the direction from.
	 * @pre		The worm is an effective worm. <br>
	 * 			| worm != null
	 * @return	The direction of the given worm. <br>
	 * 			| result = worm.getDirection()
	 */
	@Override
	public double getOrientation(Worm worm) {
		assert(worm != null);
		return worm.getDirection();
		
	}

	/**
	 * Return the radius of the given worm.
	 * 
	 * @param	worm
	 * 			The worm to get the radius from.
	 * @return	The radius of the given worm. <br>
	 * 			| result = worm.getRadius()
	 * @throws	ModelException
	 * 			The given worm is not effective. <br>
	 * 			| worm == null
	 */
	@Override
	public double getRadius(Worm worm) {
		try {
			return worm.getRadius();
		}
		catch (NullPointerException exc) {
			throw new ModelException("The given worm must be an effective worm.");
		}
	}

	/**
	 * set the radius of the given worm.
	 * 
	 * @param	worm
	 * 			The worm to set the radius for.
	 * @param	newRadius
	 * 			The new radius to be set.
	 * @post	The radius of the given worm is equal to the given radius. <br>
	 * 			| (new worm).getRadius() = newRadius
	 * @throws	ModelException
	 * 			The given radius is not a valid radius. <br>
	 * 			| !worm.IsValidNumber(newRadius)
	 * @throws	ModelException
	 * 			The given worm is not an effective worm. <br>
	 * 			| worm == null
	 */
	@Override
	public void setRadius(Worm worm, double newRadius) {
		try {
			worm.setRadius(newRadius);
		}
		catch (IllegalArgumentException exc) {
			throw new ModelException("The given radius must be a valid radius.");
		}
		catch (NullPointerException exc) {
			throw new ModelException("The given worm must be an effective worm.");
		}
	
	}

	/**
	 * Return the minimal radius of the given worm.
	 * 
	 * @param	worm
	 * 			The worm to get the minimal radius from.
	 * @return	The minimal radius of the given worm. <br>
	 * 			| result = worm.getMinimalRadius()
	 */
	@Override
	public double getMinimalRadius(Worm worm) {
		try {
			return worm.getMinimalRadius();
		}
		catch (NullPointerException exception) {
			throw new ModelException("The given worm must be an effective worm.");
		}
	}
	
	/**
	 * Return the amount of action points of the given worm.
	 * 
	 * @param	worm
	 * 			The worm to get the amount of action points from.
	 * @return	The amount of action points of the given worm. <br>
	 * 			| result = worm.getCurrentActionPoints()
	 */
	@Override
	public int getActionPoints(Worm worm) {
		
		if (worm != null) {
			return worm.getCurrentActionPoints();
		}
		return 0;
	}
	
	/**
	 * Return the maximum amount of action points of the given worm.
	 * 
	 * @param	worm
	 * 			The worm to get the maximum amount of action points from.
	 * @return	The maximum amount of action points of the given worm. <br>
	 * 			| result = worm.getMaxActionPoints()
	 */
	@Override
	public int getMaxActionPoints(Worm worm) {
		
		if (worm != null) {
			return worm.getMaxActionPoints();
		}
		return 0;
	}
	
	/**
	 * Return the name of the given worm.
	 * 
	 * @param	worm
	 * 			The worm to get the name from.
	 * @return	The name  of the given worm. <br>
	 * 			| result = worm.getName()
	 * @throws	ModelException
	 * 			The given worm is not an effective worm. <br>
	 * 			| worm == null
	 */
	@Override
	public String getName(Worm worm) {
		try {
			return worm.getName();
		}
		catch (NullPointerException exc) {
			throw new ModelException("The given worm must be an effective worm.");
		}
	}
	
	/**
	 * set the name of the given worm to the given name.
	 * 
	 * @param	worm
	 * 			The worm to set the name for.
	 * @param	newName
	 * 			The new name to be set.
	 * @post	The given worm's name is equal to the given name. <br>
	 * 			| (new worm).getName = newName
	 * @throws	ModelException
	 * 			The given worm is not an effective worm. <br>
	 * 			| worm == null
	 */			
	@Override
	public void rename(Worm worm, String newName) {
		try {
			worm.setName(newName);
		}
		catch (IllegalArgumentException exc) {
			throw new ModelException("The given name is not a valid name for a worm");
		}
		catch (NullPointerException exc) {
			throw new ModelException("The given worm must be an effective worm.");
		}
		
	}
	
	/**
	 * Return the mass of the given worm.
	 * 
	 * @param	worm
	 * 			The worm to get the mass from.
	 * @return	The mass of the given worm. <br>
	 * 			| result = worm.getMass()
	 */
	@Override
	public double getMass(Worm worm) {
		
		try {
			return worm.getMass();
		}
		catch (NullPointerException exc) {
			throw new ModelException("The given worm must be an effective worm.");
		}
		
	}

}
