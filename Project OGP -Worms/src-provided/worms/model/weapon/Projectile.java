package worms.model.weapon;

import worms.model.Position;
import worms.model.World.World;
import worms.model.World.WorldConstants;
import worms.model.worm.WormConstants;

public class Projectile {

	public Projectile(int propulsion, double orientation, Position position, double force, World world) {
		this.propulsion = propulsion;
		this.world = world;
		setPosition(position);
		this.force = force;
	}
	
	public double getDensity() {
		return ProjectileConstants.PROJECTILE_DENSITY;
	}
	
	public double getForce() {
		return force;
	}
	
	/*
	 * A variable registering the force this projectile is shot with.
	 */
	protected double force;
	
	public int getHitPointsReduction() {
		return hitPointsReduction;
	}
	
	/*
	 * A variable registering the amount of hit points to be deduced when a worm is hit with this projectile.
	 */
	protected int hitPointsReduction;
	
	public int getActionPointsCost() {
		return actionPointsCost;
	}
	
	/*
	 * A variable registering the cost of shooting this projectile.
	 */
	protected int actionPointsCost;
	
	public int getPropulsion() {
		return propulsion;
	}
	
	/*
	 * A variable registering the propulsion yield this projectile is shot with.
	 */
	protected int propulsion;
	
	public double getMass() {
		return mass;
	}
	
	/*
	 * A variable registering the mass of this projectile.
	 */
	protected double mass;
	
	public double getRadius() {
		return Math.pow( (3./4.) * (1 / Math.PI) * ( getMass() / getDensity() ) , 1./3. );
	}
	
	/*
	 * A variable registering the radius of this projectile.
	 */
	protected double radius;
	
	public double getInitialJumpVelocity() {
		return (getForce() / getMass()) * ProjectileConstants.TIME_OF_EXERTING_PROJECTILE_FORCE;
	}
	
	public void jump(double timeStep) {
		Position newPosition = jumpStep(jumpTime(timeStep));
		setPosition(newPosition);
	}
	
	public double jumpTime(double timeStep) {
		
		double t = 0.;
		Position currentPosition = jumpStep(t);
		
		while (!world.isAdjacentToImpassableTerrain(getPosition(), getRadius())) {
			t += timeStep;
			currentPosition = jumpStep(t);
		}
		
		return t;
	}
	
	public Position jumpStep(double t) {
		Position jumpStep = new Position(getNewXAfterTime(t), getNewYAfterTime(t));
		return jumpStep;
	}
	
	public double getInitialHorizontalVelocity() {
		return getInitialJumpVelocity() * Math.cos(orientation);
	}
	
	public double getInitialVerticalVelocity() {
		return getInitialJumpVelocity() * Math.sin(orientation);
	}
	
	public double getNewXAfterTime(double t) {
		return getPosition().getX() + (getInitialHorizontalVelocity() * t);
	}
	
	public double getNewYAfterTime(double t) {
		return getPosition().getY() + (getInitialVerticalVelocity() * t - (1./2.) * WorldConstants.GRAVITATIONAL_CONSTANT * t * t);
	}
	
	private void setPosition(Position position) {
		this.position = position;
	}
	
	public Position getPosition() {
		return position.clone();
	}
	
	/*
	 * A variable registering the position of this projectile.
	 */
	protected Position position;
	
	
	/*
	 * A variable registering the orientation of this projectile.
	 */
	protected double orientation;
	
	protected World world;
	
	public Projectile clone() {
		Projectile cloned = new Projectile(propulsion, orientation, position, force, world);
		cloned.setPosition(position);
		return cloned;
	}
	
}
