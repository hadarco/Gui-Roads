/**
 * 
 */
package components;
import java.util.ArrayList;

/**
 * @author krsof
 *
 */
public class RandomTrafficLights extends TrafficLights {
	
	public RandomTrafficLights(ArrayList<Road> roads) {
		super(roads);
	//	successMessage(this.toString());
	}

	@Override
	public synchronized void changeIndex() {	
		this.setGreenLightIndex((this.getGreenLightIndex()+getRandomInt(1,200))%this.getRoads().size());//increment index
		
	}
	
	@Override
	public String toString() {
		return new String("Random "+super.toString());
	}
	
	

}
