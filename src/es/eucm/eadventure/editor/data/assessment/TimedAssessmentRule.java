package es.eucm.eadventure.editor.data.assessment;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.conditions.*;

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
     * Default constructor
     * @param id Id of the rule
     * @param importance Importance of the rule
     */
    public TimedAssessmentRule( String id, int importance) {
    	super (id, importance);
    	effects = new ArrayList<TimedAssessmentEffect>();
    	this.endConditions = new Conditions();
    	effectIndex = -1;
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
	 * @return the effects
	 */
	public List<TimedAssessmentEffect> getEffects( ) {
		return effects;
	}

	/**
	 * @param effects the effects to set
	 */
	public void setEffects( List<TimedAssessmentEffect> effects ) {
		this.effects = effects;
	}
	
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
}
