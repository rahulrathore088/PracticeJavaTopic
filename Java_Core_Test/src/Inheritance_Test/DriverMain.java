package Inheritance_Test;

public class DriverMain implements Canis, Felis {
	@Override
	public void wildAnimalFeatures() {
		System.out.println("Inside Driver");
	}


	public static void main(String[] args) {
		Felis f = new DriverMain();
		Canis c  = new DriverMain();
		f.wildAnimalFeatures();
		c.wildAnimalFeatures();

	}

}
