/**
 * 
 */
package components;

import java.awt.Color;
import java.awt.Graphics;

/**
 * @author krsof
 *
 */
public class LightedJunction extends Junction {
	
	TrafficLights lights;
	
	public LightedJunction(String name, double x, double y,boolean sequential, boolean lightsOn) {
		super(name, x,y);
		
		if(sequential) {
			lights=new SequentialTrafficLights(this.getEnteringRoads());
		}
		else {
			lights=new RandomTrafficLights(this.getEnteringRoads());
		}
		if (lightsOn) {
			lights.setTrafficLightsOn(true);
		}
		successMessage(String.format("Junction %s  (%.2f , %.2f), Lighted", getJunctionName(), getX(),getY()));
	}
	
	public LightedJunction() {
		super();
		

		if (this.getRandomBoolean()){
			lights=new SequentialTrafficLights(this.getEnteringRoads());
		}
		else {
			lights=new RandomTrafficLights(this.getEnteringRoads());
		}
		
		successMessage(String.format("Junction %s (%.2f , %.2f), Lighted", getJunctionName(), getX(),getY()));
	}
	
	public TrafficLights getLights() {
		return lights;
	}
	
	public void setLights(TrafficLights lights) {
		this.lights=lights;
	}
	
	@Override
	public double calcEstimatedTime(Object obj) {
		if (!lights.getTrafficLightsOn())
			return super.calcEstimatedTime(obj);
		else return (getEnteringRoads().size()-1)*lights.getDelay()+1;
	}
	@Override
	public String toString() {
		
		return new String(super.toString()+ " (Lighted)");
	}
	@Override
	public boolean equals(Object other) {
		if (other == null) return false; 
	    if (getClass() != other.getClass()) return false; 
	    if (! super.equals(other)) return false;
	    if (!lights.equals(((LightedJunction)other).getLights())) return false;
	    return true;
	}
	
	public boolean canLeave(Vehicle vehicle) {
		if (!lights.getTrafficLightsOn()) {
			return super.canLeave(vehicle);
		}
		if(!checkAvailability(vehicle)) {
			return false;
		}
		if (!vehicle.getLastRoad().getGreenLight()) {
			vehicle.setStatus(new String(" for green light"));
			return false;
		}
		return true;
	}
	
	
	public void drawJunction(Graphics g, int delta){
		if (this.lights.getTrafficLightsOn())
			g.setColor(Color.red);
		else
			g.setColor(Color.green);
		g.fillOval((int)getX(),(int) getY(), 2*delta, 2*delta);
    }
	
}
