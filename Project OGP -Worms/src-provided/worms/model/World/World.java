package worms.model.World;

import java.util.*;

import worms.model.Position;
import worms.model.worm.Worm;
import be.kuleuven.cs.som.annotate.*;
import be.kuleuven.cs.som.taglet.*;

/**
 * A class of worlds which have a width, a height, a passable map and a set of worms.
 * 
 * @author Ruben
 */
public class World {

	/**
	 * Initializes this new world with the given width, height, passable-map and a random. <br>
	 * A world consists out of rows and columns of pixels which are either passable or not passable.
	 * 
	 * @param	width
	 * 			The width for this new world.
	 * @param	height
	 * 			The height for this new world.
	 * @param	passableMap
	 * 			The map of passable locations of this new world.
	 * @post	
	 */
	public World(double width, double height, boolean[][] passableMap, Random random) {
		setWidth(width);
		setHeight(height);
		worms = new HashSet<Worm>();
		this.passableMap = passableMap;
		this.random = random;
	}
	
	/*
	 * A variable containing the random seed for this world.
	 */
	private Random random;
	
	/**
	 * Return the width of this world.
	 */
	public double getWidth() {
		return width;
	}
	
	/**
	 * Return the height of this world.
	 */
	public double getHeight() {
		return height;
	}
	
	/**
	 * Set the width of this world.
	 * 
	 * @param	width
	 * 			The new width for this world.
	 * @post	The new width of this world is equal to the given width. <br>
	 * 			| new.getWidth == width
	 * @throws	IllegalArgumentException
	 * 			The given width is not a valid dimension. <br>
	 * 			| !isValidDimension(width)
	 */
	private void setWidth(double width) throws IllegalArgumentException {
		if (!isLegalDimension(width))
			throw new IllegalArgumentException();
		this.width = width;
	}
	
	/**
	 * Set the height of this world.
	 * 
	 * @param	height
	 * 			The new height for this world.
	 * @post	The new height of this world is equal to the given height. <br>
	 * 			| new.getHeight == height
	 * @throws	IllegalArgumentException
	 * 			The given height is not a valid dimension. <br>
	 * 			| !isValidDimension(height)
	 */
	private void setHeight(double height) throws IllegalArgumentException{
		if (!isLegalDimension(height))
			throw new IllegalArgumentException();
		this.height = height;
	}
	
	/*
	 * A variable registering the width of this world.
	 */
	double width;
	
	/*
	 * A variable registering the height of this world. 
	 */
	double height;
	
	/**
	 * Add a new worm to this world.
	 * 
	 * @param 	worm
	 * 			The worm to be added.
	 * @post	This world contains the given worm. <br>
	 * 			| new.hasAsWorm(worm) && <br>
	 * 			| isInMap(worm)
	 * @throws	IllegalArgumentException
	 * 			This world can not have the given worm. <br>
	 * 			| !canHaveAsWorm(worm)
	 */
	public void addWorm(Worm worm) throws IllegalArgumentException{
		if (!canHaveAsWorm(worm))
			throw new IllegalArgumentException();
		worms.add(worm);
		worm.setWorld(this);
		placeWormInWorld(worm);
	}
	
	/**
	 * Remove a worm of this world.
	 * 
	 * @param 	worm
	 * 			The worm to be removed.
	 * @post	The given worm does not have a connection with this world. <br>
	 * 			| (new worm).getWorld == null
	 * @post	The given worm is not in this world. <br>
	 * 			| !new.hasAsWorm(worm)
	 * @throws 	NullPointerException
	 * 			The given worm is not an effective worm. <br>
	 * 			| worm == null
	 * @throws	IllegalArgumentException
	 * 			This world does not have the given worm. <br>
	 * 			| !hasAsWorm(worm)
	 */
	public void removeWorm(Worm worm) throws IllegalArgumentException, NullPointerException{
		if (worm == null)
			throw new NullPointerException();
		if (!hasAsWorm(worm))
			throw new IllegalArgumentException();
		worm.setWorld(null);
		worm.Terminate();
		worms.remove(worm);
	}
	
	/**
	 * Check whether this world has the given worm.
	 * 
	 * @param 	worm
	 * 			The worm to be checked for.
	 * @return	getAllWorms.contains(worm)
	 */
	public boolean hasAsWorm(Worm worm) {
		return worms.contains(worm);
	}
	
	/**
	 * Check whether the given worm is a valid worm for a world. 
	 * 
	 * @param 	worm
	 * 			The worm to be checked.
	 * @return	False if the given worm is not an effective worm. <br>
	 * 			| worm == null <br>
	 * 			False if the given worm is terminated. <br>
	 * 			| worm.isTerminated == true <br>
	 * 			True otherwise.
	 */
	public static boolean isValidWorm(Worm worm) {
		if (worm == null)
			return false;
		if (worm.isTerminated())
			return false;
		return true;
	}
	
	/**
	 * Check whether this world can have the given worm.
	 * 
	 * @param 	worm
	 * 			The worm to be checked for.
	 * @return	False if the worm is not a valid worm. <br>
	 * 			| !isValidWorm(worm) <br>
	 * 			False if this world already has the given worm. <br>
	 * 			| hasAsWorm(worm) <br>
	 * 			True otherwise.
	 */
	public boolean canHaveAsWorm(Worm worm) {
		if (!isValidWorm(worm))
			return false;
		if (hasAsWorm(worm))
			return false;
		return true;
	}
	
	public void placeWormInWorld(Worm worm) {
		
		Position position = new Position(random.nextDouble() * (getWidth() - 2 * worm.getRadius() * 1.01) + worm.getRadius() * 1.01, random.nextDouble() * (getHeight() - 2 * worm.getRadius() * 1.01 ) + worm.getRadius() * 1.01);
		
		while (!isAdjacentToImpassableTerrain(position, worm.getRadius())) {
			position.setX(random.nextDouble() * (getWidth() - 2 * worm.getRadius() * 1.01) + worm.getRadius() * 1.01);
			position.setY(random.nextDouble() * (getHeight() - 2 * worm.getRadius() * 1.01) + worm.getRadius() * 1.01);
		}
		
		System.out.println(position.getX() + " " + position.getY());
		
	}
	
	/**
	 * Return a HashSet containing all the worms in this world.
	 */
	public HashSet<Worm> getAllWorms() {
		HashSet<Worm> toReturn = new HashSet<Worm>(worms);
		return toReturn;
	}
	
	/*
	 * A hash set registering all the worms in this world.
	 */
	private HashSet<Worm> worms;
	
	/**
	 * Return the amount of columns of this world.
	 */
	public int getAmountOfColums() {
		return passableMap[0].length;
	}
	
	/*
	 * A variable registering the amount of columns of this world.
	 */
	private int columns;
	
	/**
	 * Return the amount of rows of this world
	 */
	public int getAmountOfRows() {
		return passableMap.length;
	}

	/*
	 * A variable registering the amount of rows of this world.
	 */
	private int rows;
	
	/**
	 * Check whether the pixel on the given column and row is passable.
	 * 
	 * @param 	c
	 * 			The column in which the pixel is located.
	 * @param 	r
	 * 			The row in which the pixel is located.
	 * @return	True if the pixel is passable. <br>
	 * 			False if the pixel is not passable. <br>
	 */
	public boolean isPassablePixel(int c , int r) {
		if (c < 0 || r < 0)
			return false;
		if (c > getAmountOfColums() || r > getAmountOfRows())
			return false;
		
		return passableMap[r][c];
	}
	
	/**
	 * Check whether the given position is passable or not.
	 * 
	 * @param 	position
	 * 			The position to be checked.
	 * @return	True if the given position is passable. <br>
	 * 			| isPassablePixel(getPositionColumn(position), getPositionRow(position)) <br>
	 * 			False otherwise.
	 */
	public boolean isPassablePosition(Position position) {
		
		if  (isValidPosition(position)) {
			int row = getPositionRow(position);
			int column = getPositionColumn(position);
		
			if (isPassablePixel(column, row))
			return true;
		}
		
		return false;
	}
	
	public boolean isPassable(Position position, double radius) {
		
		double maxDistance = 0.1 * radius;
		
		for (double angle = 0.; angle <= 2 * Math.PI; angle += (Math.PI / 180)) {
			
			for (double distance = 0.; distance <= maxDistance; distance += (maxDistance / 100)) {
				
				Position toCheck = new Position(distance * Math.cos(angle) + position.getX(), distance * Math.sin(angle) + position.getY());
				if (!isPassablePosition(toCheck))
					return false;
			}
		}
		return true;
	}
	
	public boolean isImpassable(Position position, double radius) {
		
		double maxDistance = 0.1 * radius;
		
		for (double angle = 0.; angle <= 2 * Math.PI; angle += (Math.PI / 180)) {
			
			for (double distance = 0.; distance <= maxDistance; distance += (maxDistance / 100)) {
				
				Position toCheck = new Position(distance * Math.cos(angle) + position.getX(), distance * Math.sin(angle) + position.getY());
				if (isPassablePosition(toCheck))
					return false;
			}
		}
		return true;
		
	}
	
	/**
	 * Checks whether the given position is a valid position.
	 * 
	 * @param 	position
	 * 			The position to be checked.
	 * @return	False if the given position is not effective. <br>
	 * 			| position == null <br>
	 * 			False if the given position's x-coordinate is smaller then 0 or larger then the width of this world. <br>
	 * 			| position.getX < 0 || position.getX > getWidth <br>
	 * 			False if the given position's y-coordinate is smaller then 0 or larger then the height of this world. <br>
	 * 			| position.getY < 0 || position.getY > getHeight <br>
	 * 			True otherwise.
	 */
	public boolean isValidPosition(Position position) {
		if (position == null)
			return false;
		if (position.getX() < 0 || position.getX() > getWidth())
			return false;
		if (position.getY() < 0 || position.getY() > getHeight())
			return false;
		return true;
	}
	
	/**
	 * Get the column where the given position is located in this world.
	 * 
	 * @param 	position
	 * 			The position to get the column for.
	 * @return	The column in which the position is located.
	 */
	public int getPositionColumn(Position position) {
		
		double pixelWidth =  getWidth() / getAmountOfColums();
		int column = 0;
		
		for (double columns = pixelWidth; columns <= getWidth(); columns += pixelWidth) {
			if (position.getX() <= columns)
				break;
			column++;
		}
		
		return column;
	}
	
	/**
	 * Get the row where the given position is located in this world
	 * 
	 * @param 	position
	 * 			The position to get the row for.
	 * @return	The row in which the position is located.
	 */
	public int getPositionRow(Position position) {
		double pixelHeight = getHeight() / getAmountOfRows();
		int row = 0;
		
		for (double rows = pixelHeight; rows <= getHeight(); rows += pixelHeight) {
			if (position.getY() <= rows)
				break;
			row++;
		}
		
		return row;
	}
	
	/**
	 * Check whether the given position is in the boundaries of this map.
	 * 
	 * @param 	position
	 * 			The position to be checked.
	 * @return	False if the given position's coordinates are smaller then 0. <br>
	 * 			| position.getX < 0 || position.getY < 0 <br>
	 * 			False if the given position's x-coordinate is larger then this world's width. <br>
	 * 			| position.getX > getWidth <br>
	 * 			False if the given position's y-coordinate is larger then this world's height. <br>
	 * 			| position.getY > getHeight <br>
	 * 			True otherwise.
	 */
	public boolean isInMap(Position position, double radius) {
		
		if (position.getX() - radius < 0 || position.getY() - radius < 0)
			return false;
		if (position.getX() + radius > getWidth() || position.getY() + radius > getHeight())
			return false;
		
		return true;
	}
	
	/**
	 * Check whether the given position in this world is adjacent to impassable terrain. <br>
	 * 		A circle, chosen according to the given radius, around the given position is checked. 
	 * 
	 * @param 	position
	 * 			The position to check the surrounding terrain for.
	 * @param 	radius
	 * 			The radius of the circle that is checked.
	 * @return	True if there is impassable terrain adjacent to the given position and if the given position is a passable position <br>
	 * 			| isPassablePosition(position) && <br>
	 * 			| for some (Position toCheck) <br>
	 * 			|	(position.getDistanceFrom(toCheck) <= 0.1 * radius) && <br>
	 * 			| 	(!isPassablePosition(toCheck))
	 */
	public boolean isAdjacentToImpassableTerrain(Position position, double radius) {
		
		if (isPassablePosition(position)) {
			
			double maxDistance = 0.1 * radius + radius;
			
			for (double angle = 0.; angle <= 2 * Math.PI; angle += (Math.PI / 90)) {

				for (double distance = radius; distance <= maxDistance; distance += (0.1 * radius) / 10) {
					
					Position toCheck = new Position(distance * Math.cos(angle) + position.getX(), distance * Math.sin(angle) + position.getY());
					if (!isPassablePosition(toCheck))
						return true;
					
				}
				
			}
			
		}
		
		return false;
	}
	
	/*
	 * A 2 dimensional matrix of booleans registering a map of passable and not-passable locations of this world. <br>
	 */
	private boolean[][] passableMap;
	
	/**
	 * Check whether the given dimension is a valid dimension or not.
	 * 
	 * @param	dimension
	 * 			The dimension to be checked.
	 * @return	False if the given dimension is smaller or equal to zero. <br>
	 * 			False if the given dimension is larger or equal to Double.Max_Value. <br>
	 * 			True otherwise. <br>
	 * 			| (dimension > 0) && (dimension < Double.Max_value)
	 */
	public boolean isLegalDimension(double dimension) {
		if (dimension <= WorldConstants.WORLD_LOWER_BOUND)
			return false;
		if (dimension >= WorldConstants.WORLD_UPPER_BOUND)
			return false;
		return true;
	}
	
	/*
	 * A variable registering whether this world is terminated or not.
	 */
	private boolean isTerminated = false;
	
	/**
	 * @return	True if this world is terminated. <br>
	 * 			False otherwise.
	 */
	public boolean isTermniated() {
		return isTerminated;
	}
	
	/**
	 * Terminate this world.
	 * 
	 * @post	Every worm in this world is terminated. <br>
	 * 			| for (Worm worm : getAllWorms) <br>
	 * 			| 	worm.isTerminated == true
	 * @post	This world does not contain any worms. <br>
	 * 			| getAllWorms.isEmpty == true
	 * @post	This world is terminated. <br>
	 * 			| isTerminated == true
	 */
	public void Terminate() {
		
		for (Worm worm : worms) {
			worm.setWorld(null);
		}
		
		worms.clear();
		
		isTerminated = true;
	}
}
