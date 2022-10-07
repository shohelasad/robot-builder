package de.tech26.robotfactory.component;

import java.util.Set;

public interface ComponentInterface {
	public static Set<String> faceComponent = Set.of("Humanoid", "LCD","Steampunk");
	public static Set<String> materialComponent = Set.of("Bioplastic","Metallic");
	public static Set<String> armsComponent = Set.of("Hands", "Grippers");
	public static Set<String> mobilityComponent = Set.of("Wheels", "Legs","Tracks");
}
