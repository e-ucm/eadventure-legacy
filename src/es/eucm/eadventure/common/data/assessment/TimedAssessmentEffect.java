package es.eucm.eadventure.common.data.assessment;


public class TimedAssessmentEffect extends AssessmentEffect{

	protected int minTime;
	
	protected int maxTime;
	
    public TimedAssessmentEffect (){
    	super();
    	minTime = 0;
    	maxTime = 120;
    }

	/**
	 * @return the minTime
	 */
	public int getMinTime( ) {
		return minTime;
	}

	/**
	 * @param minTime the minTime to set
	 */
	public void setMinTime( int minTime ) {
		this.minTime = minTime;
	}

	/**
	 * @return the maxTime
	 */
	public int getMaxTime( ) {
		return maxTime;
	}

	/**
	 * @param maxTime the maxTime to set
	 */
	public void setMaxTime( int maxTime ) {
		this.maxTime = maxTime;
	}
    
	public boolean isMinTimeSet (){
		return this.minTime != Integer.MIN_VALUE;
	}
	
	public boolean isMaxTimeSet (){
		return this.maxTime != Integer.MAX_VALUE;
	}
}
