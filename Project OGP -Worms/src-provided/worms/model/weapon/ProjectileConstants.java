package worms.model.weapon;

import be.kuleuven.cs.som.annotate.*;

@Value
public class ProjectileConstants {

	public final static double PROJECTILE_DENSITY = 7800;
	
	public final static double TIME_OF_EXERTING_PROJECTILE_FORCE = 0.5;
	
	public final static double RIFLE_PROJECTILE_MASS = 0.010;
	
	public final static double RIFLE_PROJECTILE_FORCE = 1.5;
	
	public static final int RIFLE_PROJECTILE_HITPOINTS_REDUCTION = 20;
	
	public static final int RIFLE_PROJECTILE_ACTIONPOINTS_COST = 10;
	
	public static final double BAZOOKA_PROJECTILE_MASS = 0.300;
	
	public static final int BAZOOKA_PROJECTILE_HITPOINTS_REDUCTION = 80;
	
	public static final int BAZOOKA_PROJECTILE_ACTIONPOINTS_COST = 50;
	
	private ProjectileConstants() {
		
	}
	
}
