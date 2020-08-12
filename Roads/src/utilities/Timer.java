/**
 * 
 */
package utilities;

/**
 * @author krsof
 *
 */
public interface Timer {
	public void incrementDrivingTime();
	public void setSuspend();
	public void setResume();
	public void setStop();
}

//TODO timer extends utilities, delete utilities from all doubles (for bonus task only)
