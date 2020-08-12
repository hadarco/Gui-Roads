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
public class Junction extends utilities.Point implements RouteParts {
	
	private String junctionName;
	private ArrayList <Road> enteringRoads;
	private ArrayList <Road> exitingRoads;
	private static int objectsCount=1;
	
	
	public Junction (String name, double x, double y) {
		super(x,y);
		objectsCount++;
		junctionName=new String(name);
		enteringRoads= new ArrayList<Road>();
		exitingRoads= new ArrayList<Road>();
		if(!(this instanceof LightedJunction)) {
		successMessage(String.format("Junction %s (%.2f , %.2f)", junctionName, getX(),getY()));
		}
	}
	
	public Junction () {
		super();
		junctionName=new String(""+objectsCount);
		objectsCount++;
		enteringRoads= new ArrayList<Road>();
		exitingRoads= new ArrayList<Road>();
		if(!(this instanceof LightedJunction)) {
			successMessage(String.format("Junction %s (%.2f , %.2f)", junctionName, getX(),getY()));
		}
		
	}
		
	public ArrayList<Road> getEnteringRoads(){
		return enteringRoads;
	}
	
	public ArrayList<Road> getExitingRoads(){
		return exitingRoads;
	}
	
	public void setJunctionName(String name) {
		junctionName=new String(name);
	}
	
	public void setEnteringRoads(ArrayList<Road>roads) {
		enteringRoads=new ArrayList<Road>(roads);
	}
	
	public void setExitingRoads(ArrayList<Road>roads) {
		exitingRoads=new ArrayList<Road>(roads);
	}
	public String getJunctionName() {
		return junctionName;
	}
	public void addEnteringRoad(Road road) {
		enteringRoads.add(road);
	}
	
	public void addExitingRoad(Road road) {
		exitingRoads.add(road);
	}
	@Override
	public String toString() {
		return  new String("Junction "+ junctionName); 
	}
	
	public boolean equals(Object other) {
		if (other == null) return false; 
	    if (getClass() != other.getClass()) return false; 
	    if (! super.equals(other)) return false;
		Junction otherJunc=(Junction)other;
		if (this.enteringRoads.equals(otherJunc.enteringRoads)&&
			this.exitingRoads.equals(otherJunc.exitingRoads)&&
			this.junctionName.equals(otherJunc.junctionName)){
		
				return true;
		}
		return false;
	}

	@Override
	public double calcEstimatedTime(Object obj) {
		Vehicle vehicle=(Vehicle)obj;
		Road road=vehicle.getLastRoad();
		return enteringRoads.indexOf(road)+1;
	}

	@Override
	public RouteParts findNextPart(Vehicle vehicle) {
		VehicleType type=vehicle.getVehicleType();
		ArrayList<Road> roads=new ArrayList<Road>();
		
		for (Road road:exitingRoads) {
			ArrayList<VehicleType> types=new ArrayList<VehicleType>();
			for (VehicleType t: road.getVehicleTypes()) {
				types.add(t);
				
			}
			if (types.contains(type)&&road.getEnabled()) {
				roads.add(road);
				
			}
		}		
		
		if (roads.size()>0) {
			if (roads.size()==1) return roads.get(0);
			return roads.get(getRandomInt(0, roads.size()-1));
		}
		return null;
	}

	

	@Override
	public synchronized void checkIn(Vehicle vehicle) {
		vehicle.setTimeOnCurrentPart(0);
		System.out.println("- has arrived to "+ this);
		vehicle.setCurrentRoutePart(this);
		vehicle.getCurrentRoute().findNextPart(vehicle);//change route if current one ended
		
	}

	@Override
	public synchronized void checkOut(Vehicle vehicle) {
		System.out.println("- has left the "+this+".");
		vehicle.setCurrentRoutePart(this);
		vehicle.setTimeOnCurrentPart(0);
		vehicle.getLastRoad().removeVehicleFromWaitingVehicles(vehicle);
		
		for (int i=0; i<enteringRoads.size();i++) {
			for (Vehicle veh: enteringRoads.get(i).getWaitingVehicles())
				synchronized(veh) {
					veh.notify();
				}
		}
	}

	
	@Override
	public void stayOnCurrentPart(Vehicle vehicle) {
		System.out.println("- is waiting at "+this+ vehicle.getStatus() +".");
		try {
			synchronized(vehicle) {
				vehicle.wait();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	
	public boolean checkAvailability(Vehicle vehicle) {
		//check if there are exiting roads
		if (exitingRoads.size()==0) {
			vehicle.setStatus(new String(" - no exiting roads"));
			return false;
		}
		boolean flag=false;
		
		for (Road road:exitingRoads) {
			for (VehicleType type: road.getVehicleTypes()) {
				if (type.equals(vehicle.getVehicleType())&&!flag) {
					flag=true;
					break;
				}
			}
		}
		if (!flag) return false; //there are no exiting roads for this vehicle type
			
		//check if there are previous cars on same road
		if (vehicle.getLastRoad().getWaitingVehicles().indexOf(vehicle)>0) {
			vehicle.setStatus(new String("- there are previous cars on the same road"));
			return true;
		}
		return true;			
	}
	
	@Override
	public boolean canLeave(Vehicle vehicle) {
		if(!checkAvailability(vehicle)) {
			return false;
		}
		//check priority
		for (int i=0; i<enteringRoads.indexOf(vehicle.getLastRoad());i++) {
			if (enteringRoads.get(i).getWaitingVehicles().size()>0) {
				vehicle.setStatus(new String(" - there are cars on roads with higher priority"));
				return false;
			}
		}
		return true;
	}

	/**
	 * @return the objectsCount
	 */
	public static int getObjectsCount() {
		return objectsCount;
	}

	/**
	 * @param objectsCount the objectsCount to set
	 */
	public static void setObjectsCount(int objectsCount) {
		Junction.objectsCount = objectsCount;
	}
	
	public void drawJunction(Graphics g,int delta){
		g.setColor(Color.black);
		g.fillOval((int)getX(),(int) getY(), 2*delta, 2*delta);
    }
	

}


