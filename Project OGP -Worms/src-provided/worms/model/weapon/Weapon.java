package worms.model.weapon;

import worms.model.Position;
import worms.model.worm.Worm;

public class Weapon {

	public Weapon (double orientation, Position position, Worm worm) {
		
	}
	
	public boolean isTerminated() {
		return isTerminated;
	}
	
	public void terminate() {
		isTerminated = true;
	}
	
	public Weapon clone() {
		Weapon cloned = new Weapon(this.orientation, this.position, worm);
		return cloned;
	}
	
	protected boolean isTerminated = false;
	
	public void setOrientation(double orientation) {
		this.orientation = orientation;
	}
	
	/**
	 * Return the orientation of this weapon.
	 */
	public double getOrientation() {
		return orientation;
	}
	
	/*
	 * A variable registering the orientation of this weapon.
	 */
	protected double orientation;
	
	public Position getPosition() {
		return position;
	}
	
	/*
	 * A variable registering the position of this weapon.
	 */
	protected Position position;
	
	public Projectile getProjectile() {
		return projectile.clone();
	}
	
	/*
	 * A variable registering the projectile of this weapon.
	 */
	protected Projectile projectile;
	
	private void setWorm(Worm worm) {
		this.worm = worm;
	}
	
	public Worm getWorm() {
		return worm.clone();
	}
	
	/*
	 * A variable registering the worm this weapon is attached too.
	 */
	protected Worm worm;
	
}
