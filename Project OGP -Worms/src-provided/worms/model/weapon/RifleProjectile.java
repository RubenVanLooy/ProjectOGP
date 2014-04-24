package worms.model.weapon;

import worms.model.Position;
import worms.model.World.World;

public class RifleProjectile extends Projectile{
	
	public RifleProjectile(int propulsion, double orientation, Position position, double force, World world) {
		super(propulsion, orientation, position, force, world);
		mass = ProjectileConstants.RIFLE_PROJECTILE_MASS;
		actionPointsCost = ProjectileConstants.RIFLE_PROJECTILE_ACTIONPOINTS_COST;
		hitPointsReduction = ProjectileConstants.RIFLE_PROJECTILE_HITPOINTS_REDUCTION;
	}

	
}
