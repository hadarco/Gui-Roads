/**
 * 
 */
package utilities;


/**
 * @author krsof
 *
 */
public enum VehicleType {
	car(90), bus(60), bicycle(40), motorcycle(120), truck(80), tram(50), semitrailer(85);
	//car(9), bus(6), bicycle(4), motorcycle(12), truck(8), tram(5), semitrailer(8);
	
	
	private int averageSpeed;
	
	
	VehicleType(int speed) {
		averageSpeed=speed; 
		
	}

	public int getAverageSpeed() {
		return averageSpeed;
	}
}
