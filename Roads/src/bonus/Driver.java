/**
 * 
 */
package bonus;

import components.Vehicle;

/**
 * @author krsof
 *
 */
public class Driver implements utilities.Utilities{
	
  public Driver() {
	  System.out.println(this + " is driving this Vehicle");
  }
	
  public boolean canLeave(Vehicle vehicle) {
	  return vehicle.getCurrentRoutePart().canLeave(vehicle);
	  

  }
  
  public void stayOnCurrentPart(Vehicle vehicle) {
	 vehicle.getCurrentRoutePart().stayOnCurrentPart(vehicle);
  }
  
  public String toString() {
	  return "Regular Driver";
  }
}
