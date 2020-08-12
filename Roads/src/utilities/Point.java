/**
 * 
 */
package utilities;

/**
 * @author krsof
 *
 */
public abstract class Point implements Utilities {
	private double x;
	private double y;
	private final int minVal = 0;
	private final int maxX=800;
	private final int maxY=600;
	
	public Point(double xVal, double yVal) {
		
		if (checkValue(xVal, minVal, maxX)) {
			x=xVal;
		}
		
		else {
			x=getRandomDouble(minVal, maxX);
			correctingMessage(xVal,x,"X");
		}
		
		if (checkValue(yVal, minVal, maxY)) {
			y=yVal;
		}
		else {
			y=getRandomDouble(minVal, maxY);
			correctingMessage(yVal,y,"Y");
		}
		
		
	
			
	}
	
	public Point() {
		x=getRandomDouble(minVal, maxX);
		y=getRandomDouble(minVal, maxY);
		
	}
	  
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public boolean setX(double xVal) {
		
		if (checkValue(xVal,minVal, maxX)) {
			x=xVal;
			return true;
		}
		
		else {
			errorMessage(xVal, "X");
			System.out.println();
			return false;
			
		}
	}
	
	public boolean setY(double yVal) {
		
		if (checkValue(yVal,minVal,maxY)) {
			y=yVal;
			return true;
		}
		
		else {
			errorMessage(yVal, "Y");
			System.out.println();
			return false;
		}
	}


	public String toString() {
		
		 return new String("Point (" + x + " , " + y + ")");
		
	}
	
	public boolean equals(Point other) {
		if (this.x==other.x && this.y==other.y) {
			return true;
		}
		else return false;
	}

	public double calcDistance(Point other){
		return Math.sqrt(Math.pow(other.getX()-this.getX(),2)+Math.pow(other.getY()-this.getY(),2));
	}

	/**
	 * @return the minVal
	 */
	public int getMinVal() {
		return minVal;
	}

	/**
	 * @return the maxX
	 */
	public int getMaxX() {
		return maxX;
	}

	/**
	 * @return the maxY
	 */
	public int getMaxY() {
		return maxY;
	}
	
	
}
