package Inheritance_Test;

public interface Felis extends Animal {

//	public void wildAnimalFeatures();
	
	default void wildAnimalFeatures() {
		System.out.println("Felis Features");
	}

}
