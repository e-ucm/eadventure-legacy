package es.eucm.eadventure.common.data.assessment;


public class TimedAssessmentEffect extends AssessmentEffect {

	protected Integer minTime;
	
	protected Integer maxTime;
	
    public TimedAssessmentEffect (){
    	super();
    	minTime = 0;
    	maxTime = 120;
    }

	/**
	 * @return the minTime
	 */
	public Integer getMinTime( ) {
		return minTime;
	}

	/**
	 * @param minTime the minTime to set
	 */
	public void setMinTime( Integer minTime ) {
		this.minTime = minTime;
	}

	/**
	 * @return the maxTime
	 */
	public Integer getMaxTime( ) {
		return maxTime;
	}

	/**
	 * @param maxTime the maxTime to set
	 */
	public void setMaxTime( Integer maxTime ) {
		this.maxTime = maxTime;
	}
    
	public boolean isMinTimeSet (){
		return this.minTime != Integer.MIN_VALUE;
	}
	
	public boolean isMaxTimeSet (){
		return this.maxTime != Integer.MAX_VALUE;
	}
	
	public Object clone() throws CloneNotSupportedException {
		TimedAssessmentEffect tae = (TimedAssessmentEffect) super.clone();
		tae.maxTime = maxTime;
		tae.minTime = minTime;
		return tae;
	} 
}
