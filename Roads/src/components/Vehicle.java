/**
 * 
 */
package components;

import java.awt.Color;
import java.awt.Graphics;

import utilities.Point;
import utilities.Timer;
import utilities.Utilities;
import utilities.VehicleType;


public class Vehicle extends Point implements Utilities, Timer, Runnable { 
	private int id;
	private VehicleType vehicleType;
	private Route currentRoute;
	private RouteParts currentRoutePart;
	private int timeFromRouteStart;
	public static int objectsCount=1;
	private int timeOnCurrentPart;
	private Road lastRoad;
	private String status;
	private Color color;
	private boolean threadSuspend = false;
	private boolean stop = false;
	
	
	public Vehicle (Road currentLocation) {// random constructor
		id=objectsCount++;
		vehicleType=currentLocation.getVehicleTypes()[getRandomInt(0,currentLocation.getVehicleTypes().length-1)];
		System.out.println();
		successMessage(this.toString());
		currentRoute=new Route(currentLocation, this); //creates a new route for the vehicle and checks it in
		lastRoad=currentLocation;
		status=null;
		color = new Color((int)(Math.random()*200),(int)(Math.random()*200),(int)(Math.random()*200));
	}
	
	public void calcPositionOnRoad() {
		Road road=(Road)currentRoutePart;
		int speed=Math.min(road.getMaxSpeed(), getVehicleType().getAverageSpeed());
		double x1=road.getStartJunction().getX();
		double y1 =road.getStartJunction().getY();
		double x2=road.getEndJunction().getX();
		double y2=road.getEndJunction().getY();
		double distance=timeOnCurrentPart*speed/10;

		double dX= (distance/road.length)*(x2-x1);
		double dY=(distance/road.length)*(y2-y1);
		setX(x1+dX);
		setY(y1+dY);
	}
	
	
	public int getObjectsCount() {
		return objectsCount;
	}
	
	public VehicleType getVehicleType() {
		return vehicleType;
	}
	
	public int getID() {
		return id;
	}
	
	public void setId(int id) {
		this.id=id;
	}
	
	public void setVehicleType(VehicleType type) {
		vehicleType=type;
	}
	public Route getCurrentRoute() {
		return currentRoute;
	}
	public void setCurrentRoute(Route newRoute) {
		currentRoute=newRoute;
	}
	public RouteParts getCurrentRoutePart() {
		return currentRoutePart;
	}
	
	public void setCurrentRoutePart(RouteParts newPart) {
		currentRoutePart=newPart;
	}
	
	public int getTimeFromRouteStart() {
		return timeFromRouteStart;
	}
	
	public void setTimeFromRouteStart(int time) {
		timeFromRouteStart=time;
	}
	
	public int getTimeOnCurrentPart () {
		return timeOnCurrentPart;
	}
	
	public void setTimeOnCurrentPart(int time) {
		timeOnCurrentPart=time;
	}
	
	public Road getLastRoad( ) {
		return lastRoad;
	}
	public void setLastRoad(Road road) {
		lastRoad=road;
	}
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status=status;
	}
	
	@Override
	public void incrementDrivingTime() {
		timeFromRouteStart++;
		timeOnCurrentPart++;
		move();
	}
	
	public void move() {
		if (currentRoutePart.canLeave(this)) {
			currentRoutePart.checkOut(this);
			currentRoute.findNextPart(this).checkIn(this);
		}
		else {
			currentRoutePart.stayOnCurrentPart(this);
		}
	}
	
	
	@Override
	public String toString() {
		return new String("Vehicle "+id+": "+ getVehicleType().name()+", average speed: "+getVehicleType().getAverageSpeed());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false; 
	    if (getClass() != obj.getClass()) return false; 
	    if (! super.equals(obj)) return false;
		Vehicle other=(Vehicle)obj;
		if (this.currentRoute!=other.currentRoute||
			this.currentRoutePart!=other.currentRoutePart||
			this.id!=other.id||
			this.lastRoad!=other.lastRoad||
			this.status!=other.status||
			this.timeFromRouteStart!=other.timeFromRouteStart||
			this.timeOnCurrentPart!=other.timeOnCurrentPart||
			this.vehicleType!=other.vehicleType)
				return false;
		return true;
	}

	/**
	 * @param objectsCount the objectsCount to set
	 */
	public static void setObjectsCount(int objectsCount) {
		Vehicle.objectsCount = objectsCount;
	}
	
	private void drawRotetedVehicle(Graphics g, int x1, int y1, int x2, int y2, int d, int h) {
		int delta = 10;
	    int dx = x2 - x1, dy = y2 - y1;
	    double D = Math.sqrt(dx*dx + dy*dy);
	    double xm = delta, xn = xm, ym = h, yn = -h, x;
	    double xm1 = delta + d, xn1 = xm1, ym1 = h, yn1 = -h, xx; 
	    double sin = dy / D, cos = dx / D;

	    x = xm*cos - ym*sin + x1;
	    xx = xm1*cos - ym1*sin + x1;
	    
	    ym = xm*sin + ym*cos + y1;
	    ym1 = xm1*sin + ym1*cos + y1;
	    
	    xm = x;
	    xm1 = xx; 

	    x = xn*cos - yn*sin + x1;
	    xx = xn1*cos - yn1*sin + x1; 
	    
	    yn = xn*sin + yn*cos + y1;
	    yn1 = xn1*sin + yn1*cos + y1;
	    
	    xn = x;
	    xn1 = xx;

	    int[] xpoints = {(int) xm1, (int) xn1,  (int) xn, (int) xm};
	    int[] ypoints = {(int) ym1, (int) yn1, (int) yn, (int) ym};
	    

	    g.fillPolygon(xpoints, ypoints, 4);
	    g.setColor(Color.BLACK);
	    g.fillOval((int) xm1-2,(int) ym1-2,4,4);
	    g.fillOval((int) xn1-2,(int) yn1-2,4,4);
	    g.fillOval((int) xm-2,(int) ym-2,4,4);
	    g.fillOval((int) xn-2,(int) yn-2,4,4);
	    
	}
	

	public void drawVehicle(Graphics g, int delta, int colorInd) {
	    if (colorInd == 0)
	    	g.setColor(Color.blue); 
	    else if (colorInd == 1)
	    	g.setColor(Color.magenta);
	    else if (colorInd == 2)
	    	g.setColor(Color.orange);
	    else
	    	g.setColor(color);
	    
		if (currentRoutePart instanceof Junction) {
			Junction junc =(Junction) currentRoutePart;
			g.fillRect((int)junc.getX()+delta-4,(int) junc.getY()+delta-4, 8, 8);	
		    g.setColor(Color.BLACK);
		    g.fillOval((int)junc.getX() +delta-6 ,(int) junc.getY()+delta-6,4,4);
		    g.fillOval((int)junc.getX() +delta-6+8 ,(int) junc.getY()+delta-6,4,4);
		    g.fillOval((int)junc.getX() +delta-6 ,(int) junc.getY()+delta-6+8,4,4);
		    g.fillOval((int)junc.getX() +delta-6+8 ,(int) junc.getY()+delta-6+8,4,4);

		}
		else {
			calcPositionOnRoad();
			
			Road road=(Road) currentRoutePart;
			double x2=road.getEndJunction().getX();
			double y2=road.getEndJunction().getY();
	
			drawRotetedVehicle(g, (int)getX()+delta, (int)getY()+delta,  (int) x2+delta,  (int) y2+delta, 10,  4);
			//g.fillRect((int)getX()+delta-4, (int)getY()+delta-4, 8, 8);
		}
	}
	
	@Override
	public void run() {
		while(true) {
			
			//System.out.println("- on delay");
			try {
				Thread.sleep(600);
				synchronized(this) {
	                while (threadSuspend)
	                	wait();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (stop) return;
			incrementDrivingTime();
		}
	}
	

	

	@Override
	public void setSuspend() {
		threadSuspend = true;
	}
	
	@Override
	public synchronized void setResume() {
		threadSuspend = false;
		notify();
	}

	@Override
	public void setStop() {
		stop = true;
	}
}
