/**
 * 
 */
package components;

import java.util.ArrayList;

/**
 * @author krsof
 *
 */
public class SequentialTrafficLights extends TrafficLights {
	public final int increment=1; 
	
	public SequentialTrafficLights(ArrayList<Road> roads) {
		super(roads);
		//successMessage(this.toString());
	}

	@Override
	public synchronized void changeIndex() {
		
		this.setGreenLightIndex((this.getGreenLightIndex()+increment)%this.getRoads().size());//increment index
	}
	
	@Override
	public String toString() {
		return new String("Sequential "+super.toString());
	}

	/**
	 * @return the increment
	 */
	public int getIncrement() {
		return increment;
	}
	
	
}
