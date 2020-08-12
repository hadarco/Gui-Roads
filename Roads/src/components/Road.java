/**
 * 
 */
package components;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import utilities.VehicleType;
/**
 * @author krsof
 *
 */
public class Road implements RouteParts{
	private final static int[] allowedSpeedOptions= {30,40,50,55,60,70,80,90};
	Junction startJunction;
	Junction endJunction;
	private ArrayList<Vehicle> waitingVehicles;
	private boolean greenLight;
	private int maxSpeed;
	VehicleType [] vehicleTypes;
	double length;
	boolean enable;
	
	public Road(Junction start, Junction end) {
		startJunction=start;
		endJunction=end;
		waitingVehicles=new ArrayList<Vehicle>();
		greenLight=false;
		maxSpeed=allowedSpeedOptions[getRandomInt(0,7)];
		
		int numOfTypes=getRandomInt(3,VehicleType.values().length);
		
		vehicleTypes=new VehicleType[numOfTypes];
		VehicleType[] types=VehicleType.values();
		ArrayList <Integer> arr=getRandomIntArray(0,6,numOfTypes);
		
		
		for (int i=0;i<numOfTypes;i++) {
			vehicleTypes[i]=types[arr.get(i)];
		}
		this.getStartJunction().getExitingRoads().add(this);
		this.getEndJunction().getEnteringRoads().add(this);
		
		setLength();
		enable=!(getRandomBoolean()&&getRandomBoolean()&&getRandomBoolean());
		successMessage(this.toString());
	}
	
	
	public Junction getStartJunction() {
		return startJunction;
	}
	
	public void setStartJunction(Junction junc) {
		startJunction=junc;
	}
	
	public Junction getEndJunction() {
		return endJunction;
	}
	
	public void setEndJunction(Junction junc) {
		endJunction=junc;
	}
	public ArrayList<Vehicle> getWaitingVehicles() {
		return waitingVehicles; 
	}
	
	public void setWaitingVehicles(ArrayList<Vehicle> vehicles) {
		waitingVehicles=vehicles;
	}
	public void addVehicleToWaitingVehicles(Vehicle vehicle) {
		waitingVehicles.add(vehicle);
	}
	public void removeVehicleFromWaitingVehicles(Vehicle vehicle) {
		waitingVehicles.remove(vehicle);
	}
	
	public synchronized void setGreenLight(boolean isGreen) {
		this.greenLight=isGreen;
		for (Vehicle vehicle: this.waitingVehicles) {
			synchronized(vehicle) {
				vehicle.notify();
			}
		}
	}
	public synchronized boolean getGreenLight() {
		return greenLight;
	}

	public VehicleType[] getVehicleTypes() {
		return vehicleTypes;
	}
	public void setVehicleTypes(VehicleType[] types) {
		vehicleTypes=types;
	}
	public boolean getEnabled() {
		return enable;
	}
	public void setEnabled(boolean enable) {
		this.enable=enable;
	}
	public void setLength() {
		length=calcLength();
	}
	public double calcLength() {
		return startJunction.calcDistance(endJunction);
	}
	public int getMaxSpeed() {
		return maxSpeed;
	}
	public void setMaxSpeed(int speed) {
		maxSpeed=speed;
	}


	@Override
	public double calcEstimatedTime(Object obj) {
		Vehicle v=(Vehicle)obj;
		int speed=Math.min(maxSpeed, v.getVehicleType().getAverageSpeed());
		return (int)length/speed;
	}

	
	@Override
	public RouteParts findNextPart(Vehicle vehicle) {
		return endJunction;
	}
	
	@Override
	public synchronized void checkIn(Vehicle vehicle) {
		vehicle.setCurrentRoutePart(this);
		vehicle.setTimeOnCurrentPart(0);
		vehicle.setLastRoad(this);
		System.out.println("- is starting to move on "+ this + ", time to finish: " + calcEstimatedTime(vehicle)+ ".");
	}
	@Override
	public void stayOnCurrentPart(Vehicle vehicle) {
		System.out.println("- " + vehicle.getStatus() + this + ", time to arrive: "+ (calcEstimatedTime(vehicle)-vehicle.getTimeOnCurrentPart()));
	}

	@Override
	public synchronized void checkOut(Vehicle vehicle) {
		System.out.println("- has finished "+ this+ ", time spent on the road: "+vehicle.getTimeOnCurrentPart()+".");
		addVehicleToWaitingVehicles(vehicle);
		
	}

	@Override
	public String toString() {
		return new String("Road from "+getStartJunction()+" to "+getEndJunction()+ ", length: "+ (int)length+ ", max speed "+this.maxSpeed);
	}

	@Override
	public boolean equals(Object other) {
		
		if (other == null) return false; 
	    if (getClass() != other.getClass()) return false; 
	    if (! super.equals(other)) return false;
	    Road otherRoad=(Road)other;
	    if (this.enable!=otherRoad.enable || 
	    	!this.endJunction.equals(otherRoad.endJunction) ||
	    	this.length!=otherRoad.length ||
	    	this.maxSpeed!=otherRoad.maxSpeed||
	    	this.startJunction!=otherRoad.startJunction||
	    	this.vehicleTypes!=otherRoad.vehicleTypes //compare by reference 
	    	) return false;
	    return true;
		}


	@Override
	public boolean canLeave(Vehicle vehicle) {
		/*if (calcEstimatedTime(vehicle)-vehicle.getTimeOnCurrentPart()>0){
			vehicle.setStatus(new String("is still moving on "));
			return false;
		}*/
		vehicle.calcPositionOnRoad();
		if(vehicle.calcDistance(this.endJunction)>14)
			return false;
		return true;
	}


	/**
	 * @return the length
	 */
	public double getLength() {
		return length;
	}


	/**
	 * @param length the length to set
	 */
	public void setLength(double length) {
		this.length = length;
	}


	/**
	 * @return the enable
	 */
	public boolean isEnable() {
		return enable;
	}


	/**
	 * @param enable the enable to set
	 */
	public void setEnable(boolean enable) {
		this.enable = enable;
	}


	/**
	 * @return the allowedspeedoptions
	 */
	public static int[] getAllowedspeedoptions() {
		return allowedSpeedOptions;
	}


	public void drawRoads(Graphics g, int delta) {
		//if (!enable) return;
		g.setColor(Color.black); 
		g.drawLine((int) startJunction.getX()+delta, (int) startJunction.getY()+delta,(int) endJunction.getX()+delta, (int) endJunction.getY()+delta);
		//drawArrowLine(g,(int) startJunction.getX()+delta, (int) startJunction.getY()+delta,(int) endJunction.getX()+delta, (int) endJunction.getY()+delta,25,3);
	}
	
	public void drawRoadGreenLight(Graphics g, int delta) {
		//if (!enable) return;
		g.setColor(Color.black); 
		//g.drawLine((int) startJunction.getX()+delta, (int) startJunction.getY()+delta,(int) endJunction.getX()+delta, (int) endJunction.getY()+delta);
		if (this.greenLight)
			drawArrowLine(g,(int) startJunction.getX()+delta, (int) startJunction.getY()+delta,(int) endJunction.getX()+delta, (int) endJunction.getY()+delta,28,3);
	}

	private void drawArrowLine(Graphics g, int x1, int y1, int x2, int y2, int d, int h) {
		    int dx = x2 - x1, dy = y2 - y1;
		    double D = Math.sqrt(dx*dx + dy*dy);
		    double xm = D - d, xn = xm, ym = h, yn = -h, x;
		    double sin = dy / D, cos = dx / D;

		    x = xm*cos - ym*sin + x1;
		    ym = xm*sin + ym*cos + y1;
		    xm = x;

		    x = xn*cos - yn*sin + x1;
		    yn = xn*sin + yn*cos + y1;
		    xn = x;

		    int[] xpoints = {x2, (int) xm, (int) xn};
		    int[] ypoints = {y2, (int) ym, (int) yn};

		    //g.drawLine(x1, y1, x2, y2);
		    
		    g.setColor(Color.green);
		    g.fillPolygon(xpoints, ypoints, 3);
	}
	
}
