package es.eucm.eadventure.engine.assessment;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapterdata.conditions.Conditions;


/**
 * Timed Rule for the assesment engine
 */
public class TimedAssessmentRule extends AssessmentRule {
    
	/**
	 * End conditions
	 */
	protected Conditions endConditions;
	
	/**
	 * List of timed effects
	 */
	protected List<TimedAssessmentEffect> effects;
	
	/**
	 * For loading purpose, only
	 */
	protected int effectIndex;
	
	/**
	 * True if the assessmentRule is Done
	 */
	protected boolean isDone;
	
	/**
	 * Time the rule took 
	 */
	protected long elapsedTime;

    private long startTime;
	
    /**
     * Default constructor
     * @param id Id of the rule
     * @param importance Importance of the rule
     */
    public TimedAssessmentRule( String id, int importance) {
    	super (id, importance);
    	effects = new ArrayList<TimedAssessmentEffect>();
    	this.endConditions = new Conditions();
    	effectIndex = -1;
    	elapsedTime = 0;
    	isDone = false;
    }
    
    /**
     * Sets the conditions of the rule
     * @param conditions Conditions of the rule
     */
    public void setInitConditions( Conditions initConditions ) {
        this.conditions = initConditions;
    }

    /**
     * Sets the text of the rule
     * @param text Text of the rule
     */
    public void setText( String text, int effectBlock ) {
    	if (effectBlock >=0 && effectBlock<effects.size( ))
    		effects.get( effectBlock ).setText( text );
    }
    
    /**
     * Adds a new assessment property
     * @param property Assessment property to be added
     */
    public void addProperty( AssessmentProperty property, int effectBlock ) {
        if (effectBlock >=0 && effectBlock<effects.size( ))
        	effects.get( effectBlock ).getAssessmentProperties( ).add( property );
    }
    
    /**
     * Adds a new assessment property
     * @param property Assessment property to be added
     */
    public AssessmentProperty getProperty( int property, int effectBlock ) {
        if (effectBlock >=0 && effectBlock<effects.size( ))
        	return effects.get( effectBlock ).getAssessmentProperties( ).get( property );
        return null;
    }
    
    public void setMinTime (int time, int effectBlock){
    	if (effectBlock >=0 && effectBlock<effects.size( ))
        	effects.get( effectBlock ).setMinTime( time );
    }
    
    public void setMaxTime (int time, int effectBlock){
    	if (effectBlock >=0 && effectBlock<effects.size( ))
        	effects.get( effectBlock ).setMaxTime( time );
    }
    
    public int getMinTime (int effectBlock){
    	if (effectBlock >=0 && effectBlock<effects.size( ))
        	return effects.get( effectBlock ).getMinTime( );
    	return Integer.MIN_VALUE;
    }
    
    public int getMaxTime (int effectBlock){
    	if (effectBlock >=0 && effectBlock<effects.size( ))
        	return effects.get( effectBlock ).getMaxTime( );
    	return Integer.MAX_VALUE;
    }
    
    public int getEffectsCount(){
    	return this.effects.size( );
    }

    
	/**
	 * @return the conditions
	 */
	public Conditions getInitConditions( ) {
		return conditions;
	}

	/**
	 * @return the endConditions
	 */
	public Conditions getEndConditions( ) {
		return endConditions;
	}

	/**
	 * @param endConditions the endConditions to set
	 */
	public void setEndConditions( Conditions endConditions ) {
		this.endConditions = endConditions;
	}

	/**
	 * @param effects the effects to set
	 */
	public void addEffect(){
		this.effectIndex++;
		effects.add( new TimedAssessmentEffect() );
	}
	
	   public void addEffect(int min, int max){
	        this.effectIndex++;
	        TimedAssessmentEffect newEffect = new TimedAssessmentEffect();
	        newEffect.setMinTime( min );
	        newEffect.setMaxTime( max );
	        effects.add( newEffect );
	    }

	/**
     * Sets the text of the rule
     * @param text Text of the rule
     */
    public void setText( String text ) {
        setText ( text, effectIndex );
    }
    
    /**
     * Adds a new assessment property
     * @param property Assessment property to be added
     */
    public void addProperty( AssessmentProperty property ) {
    	addProperty ( property, effectIndex );
    }
    
    /**
     * Returns true if the rule is active
     * @return True if the rule is active, false otherwise
     */
    public boolean isActive( ) {
        return isDone;
    }
    
    public void ruleStarted( long currentTime ){
        isDone = false;
        this.startTime = currentTime;
    }
    
    public void ruleDone (long currentTime) {
        this.isDone = true;
        this.elapsedTime = currentTime-this.startTime;
        
        // Evaluate the rule
        for (TimedAssessmentEffect effect  : this.effects){
            if (elapsedTime >=effect.getMinTime( ) && elapsedTime <=effect.getMaxTime( )){
                this.properties = effect.getAssessmentProperties( );
                this.text = "[ELAPSED TIME = "+getTimeHhMmSs()+" ] "+effect.getText( );
                //System.out.println( "[RULE EVALUATION] "+text );
                break;
            }
        }

    }
    
    /**
     * Returns the time of the timer represented as hours:minutes:seconds. The string returned will look like:
     * HHh:MMm:SSs
     * @return The time as HHh:MMm:SSs
     */
    private String getTimeHhMmSs (){
        String time = "";
        
        // Less than 60 seconds
        if (elapsedTime <60 && elapsedTime>=0){
            time = Long.toString( elapsedTime )+"s";
        }
        
        // Between 1 minute and 60 minutes
        else if (elapsedTime < 3600 && elapsedTime>=60){
            long minutes = elapsedTime / 60;
            long lastSeconds = elapsedTime % 60;
            time = Long.toString( minutes )+"m:"+Long.toString( lastSeconds )+"s";
        }
        
        // One hour or more
        else if (elapsedTime>=3600){
            long hours = elapsedTime / 3600;
            long minutes = (elapsedTime % 3600) / 60;
            long lastSeconds = (elapsedTime % 3600) % 60;
            time = Long.toString( hours )+"h:"+Long.toString( minutes )+"m:"+Long.toString( lastSeconds )+"s";
        }
        
        return time;
    }

}
