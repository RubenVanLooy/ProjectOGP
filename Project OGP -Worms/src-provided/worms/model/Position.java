package worms.model;


/**
 * 
 * A class of positions which have an x-coordinate and a y-coordinate.
 * 
 * @author Ruben
 *
 */
public class Position {

	
	/**
	 * Initialize this new position with the given x-coordinate and the given y-coordinate.
	 * 
	 * @param	x
	 * 			The x-coordinate for this new position.
	 * @param	y
	 * 			The y-coordinate for this new position.
	 * @post	The new x-coordinate of this position is equal to the given x-coordinate. <br>
	 * 			| new.getX() == x
	 * @post	The new y-coordinate of this position is equal to the given y-coordinate. <br>
	 * 			| new.getY() == y
	 */
	public Position(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Return the x-coordinate of this position.
	 */
	public double getX() {
		return x;
	}

	/**
	 * Return the y-coordinate of this position.
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Set the x-coordinate of this position.
	 * 
	 * @param 	x
	 * 			The new x-coordinate for this position.
	 * @post	The x-coordinate of this position is equal to the given x-coordinate. <br>
	 * 			| new.getX() == x
	 */
	public void setX(double x) {
		this.x = x;
	}
	
	/**
	 * Set the y-coordinate of this position.
	 * 
	 * @param 	y
	 * 			The new y-coordinate for this position.
	 * @post	The y-coordinate of this position is equal to the given y-coordinate. <br>
	 * 			| new.gety() == y
	 */
	public void setY(double y) {
		this.y = y;
	}
	
	/**
	 * Get the distance between this position and the given position.
	 * 
	 * @param 	position
	 * 			The position to get the distance from.
	 * @return	The distance between this position and the given position. <br>
	 * 			| Math.sqrt[ (this.getX - position.getX) ^ 2 + (this.getY - position.getY) ^ 2 ]
	 */
	public double getDistanceFrom(Position position) {
		return Math.sqrt( Math.pow( (this.getX() - position.getX()),2) + Math.pow( (this.getY() -position.getY()), 2) );
	}
	
	public Position clone() {
		Position cloned = new Position(getX(), getY());
		return cloned;
	}
	
	/*
	 * A variable describing the x-coordinate of this position.
	 */
	private double x;
	
	/*
	 * A variable describing the y-coordinate of this position.
	 */
	private double y;
	
}
