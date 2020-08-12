/**
 * 
 */
package components;

import utilities.Utilities;


/**
 * @author krsof
 *
 */
public interface RouteParts extends Utilities {

	public double calcEstimatedTime(Object obj);
	public RouteParts findNextPart(Vehicle vehicle);
	public void checkIn (Vehicle vehicle);
	public void checkOut (Vehicle vehicle);
	public boolean equals(Object other);
	public void stayOnCurrentPart(Vehicle vehicle);
	public boolean canLeave(Vehicle vehicle);
	
}


