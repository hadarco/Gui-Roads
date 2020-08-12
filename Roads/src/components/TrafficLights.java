/**
 * 
 */
package components;

import java.util.ArrayList;

import utilities.Timer;
import utilities.Utilities;

/**
 * @author krsof
 *
 */
public abstract class TrafficLights  implements Timer, Utilities, Runnable{
	private int id;
	private final int maxDelay=6;
	private final int minDelay=2;
	private boolean trafficLightsOn;
	private int greenLightIndex;
	private int delay;
	private int workingTime;
	private ArrayList<Road> roads; 
	private static int objectsCount=1;
	private boolean threadSuspend = false;
	private boolean stop = false;
	
	public TrafficLights(ArrayList<Road> roads) {
		id=objectsCount++;
		trafficLightsOn=false;
		greenLightIndex=-1;
		delay=0;
		workingTime=0;
		this.roads=roads;
		
	}
	
	public boolean getTrafficLightsOn() {
		return trafficLightsOn;
	}
	
	public void setTrafficLightsOn(boolean on) {
		if (on) {
			if (roads.size()<1) {
				System.out.println(this + "Lights can not be turned on at junction with no entering roads");
				return;
			}
			trafficLightsOn=true;
			delay=getRandomInt(minDelay,maxDelay);
			System.out.println(this+ " turned ON, delay time: "+ delay);
			changeLights();
		}
		else {
			trafficLightsOn=false;
			delay=0;
			for(Road road: roads) {
				road.setGreenLight(false);
			}
		}
	}
	
	public synchronized int getGreenLightIndex() {
		return greenLightIndex;
	}
	
	public synchronized void setGreenLightIndex(int index) {
		greenLightIndex=index;
	}
	
	public synchronized int getDelay() {
		return delay;
	}
	
	public synchronized void setDelay (int delay) {
		this.delay=delay;
	}
	
	public void setWorkingTime(int time) {
		workingTime=time;
	}
	
	public int getWorkingTime() {
		return workingTime;
	}
	
	public ArrayList<Road> getRoads(){
		return roads;
	}
	
	public void setRoads(ArrayList<Road> roads) {
		this.roads=roads;
	}
	
	public void incrementDrivingTime() {
		if (trafficLightsOn) {
			workingTime++;
			if (workingTime%delay==0) {
				changeLights();
			}
		}
	}
	
	public synchronized void changeLights() {
		if (!trafficLightsOn) {
			System.out.println("- Traffic lights are off and can't be changed");
		}
		else {
			for (Road road:roads) {
					road.setGreenLight(false);
			}
			changeIndex();
			this.getRoads().get(this.getGreenLightIndex()).setGreenLight(true);//set green light to the next road
			System.out.println("- "+ this.getRoads().get(this.getGreenLightIndex())+": green light.");//print message
			
		}
	}
	
	public abstract void changeIndex();
	
	public boolean equals(Object obj) {
		if (obj == null) return false; 
	    if (getClass() != obj.getClass()) return false; 
	    if (! super.equals(obj)) return false;
	    TrafficLights other=(TrafficLights)obj;
	    if (this.delay!=other.delay||
	    	this.greenLightIndex!=other.greenLightIndex||
	    	this.roads!=other.roads||
	    	this.trafficLightsOn!=other.trafficLightsOn||
	    	this.workingTime!=other.workingTime
	    		
	    	) return false;
	    return true;
	}
	
	
	public String toString() {
//		String status;
//		if (getTrafficLightsOn())
//			status= new String("ON");
//		else status=new String("OFF");
		return new String("traffic lights "+ id);
	}

	/**
	 * @return the id
	 */
	public int getID() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
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
		TrafficLights.objectsCount = objectsCount;
	}

	/**
	 * @return the maxDelay
	 */
	public int getMaxDelay() {
		return maxDelay;
	}

	/**
	 * @return the minDelay
	 */
	public int getMinDelay() {
		return minDelay;
	}
	
	
	@Override
	public void run() {
		if (!trafficLightsOn) return;
		
		while(true) {
			if (stop) return;
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
			changeLights();
			try {
				Thread.sleep(100*delay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//workingTime++;
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


