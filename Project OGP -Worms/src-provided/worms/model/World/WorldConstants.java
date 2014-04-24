package worms.model.World;

import be.kuleuven.cs.som.annotate.*;

@Value
public class WorldConstants {

	public final static double WORLD_UPPER_BOUND = Double.MAX_VALUE;
	
	public final static double WORLD_LOWER_BOUND = 0;

	public final static double GRAVITATIONAL_CONSTANT = 9.08665;
	
	private WorldConstants() {
		
	}
	
}
