/**
 * 
 */
package bonus;

import components.Driving;
import components.Road;

/**
 * @author krsof
 *
 */
public class BonusDriving extends Driving {
		
	public BonusDriving(int numOfJuncs, int numOfVehicles) {
		super(numOfJuncs, 0);
		this.addVehicles(numOfVehicles);
		
		
	}

	
	public void addVehicles(int numOfVehicles) {
		while(getVehicles().size()<numOfVehicles) {
			Road temp=getMap().getRoads().get(getRandomInt(0,getMap().getRoads().size()));//random road from the map
			if( temp.getEnabled())
				this.getVehicles().add(new DrivedVehicle(temp));
		
		}
		getAllTimedElements().addAll(0,getVehicles());	
	}
	
}
