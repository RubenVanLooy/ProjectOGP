package worms.model.weapon;

import worms.model.Position;
import worms.model.World.World;

public class BazookaProjectile extends Projectile {

	public BazookaProjectile(int propulsion, double orientation, Position position, double force, World world) {
		super(propulsion, orientation, position, force, world);
		mass = ProjectileConstants.BAZOOKA_PROJECTILE_MASS;
		actionPointsCost = ProjectileConstants.BAZOOKA_PROJECTILE_ACTIONPOINTS_COST;
		hitPointsReduction = ProjectileConstants.BAZOOKA_PROJECTILE_HITPOINTS_REDUCTION;
	}
	
}
