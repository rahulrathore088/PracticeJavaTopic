package Inheritance_Test;

public interface Canis extends Animal {

	//public void wildAnimalFeatures();
	
	default void wildAnimalFeatures() {
		System.out.println("Canis Features");
	}
	
}