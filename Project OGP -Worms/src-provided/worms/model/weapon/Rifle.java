package worms.model.weapon;

import worms.model.Position;
import worms.model.worm.Worm;

public class Rifle extends Weapon {

	public Rifle(double orientation, Position position, Worm worm) {
		super(orientation, position, worm);
	}
	
	public void shoot(double timeStep) {
		this.projectile = new RifleProjectile(0, orientation, position, getForce(), worm.getWorld());
		projectile.jump(timeStep);
	}

	public double getForce() {
		return ProjectileConstants.RIFLE_PROJECTILE_FORCE;
	}
	
}
