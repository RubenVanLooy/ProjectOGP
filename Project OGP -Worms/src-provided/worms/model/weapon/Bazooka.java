package worms.model.weapon;

import worms.model.Position;
import worms.model.worm.Worm;

public class Bazooka extends Weapon {

	public Bazooka(double orientation, Position position, Worm worm) {
		super(orientation, position, worm);
	}
	
	public void shoot(int propulsion, double timeStep) {
		this.projectile = new BazookaProjectile(propulsion, orientation, position, getForce(propulsion), worm.getWorld());
		projectile.jump(timeStep);
	}
	
	public double getForce(int propulsion) {
		return 2.5 + 7.5 * (propulsion / 100.);
	}
	
}
