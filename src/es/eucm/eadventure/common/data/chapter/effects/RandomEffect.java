package es.eucm.eadventure.common.data.chapter.effects;

/**
 * Data representation of a random effect. According to a probability defined by user, the system chooses between two effects which is to be launched
 * So the behaviour is:
 * 		PROBABILITY times POSITIVE EFFECT is triggered
 *      100-PROBABILITY times NEGATIVE EFFECT is triggered
 * @author Javier
 */
public class RandomEffect implements Effect{

	/** 
	 * Effect to be triggered PROBABILITY% of the times
	 */
    private Effect positiveEffect;

    /** 
	 * Effect to be triggered 100-PROBABILITY% of the times
	 */
    private Effect negativeEffect;
    
    /**
     * Probability in range 0%-100%
     */
    private int probability;
	
    /**
     * Constructor
     * @param probability
     */
    public RandomEffect ( int probability ){
    	this.probability = probability;
    }
    
    /**
     * Default constructor. Sets probability to 50%
     */
    public RandomEffect (){
    	this(50);
    }
    
	public int getType( ) {
		return Effect.RANDOM_EFFECT;
	}

    /**
     * @param positiveEffect the positiveEffect to set
     */
    public void setPositiveEffect( Effect positiveEffect ) {
        this.positiveEffect = positiveEffect;
    }

    /**
     * @param negativeEffect the negativeEffect to set
     */
    public void setNegativeEffect( Effect negativeEffect ) {
        this.negativeEffect = negativeEffect;
    }

	/**
	 * @return the probability
	 */
	public int getProbability( ) {
		return probability;
	}

	/**
	 * @param probability the probability to set
	 */
	public void setProbability( int probability ) {
		this.probability = probability;
	}

	/**
	 * @return the positiveEffect
	 */
	public Effect getPositiveEffect( ) {
		return positiveEffect;
	}

	/**
	 * @return the negativeEffect
	 */
	public Effect getNegativeEffect( ) {
		return negativeEffect;
	}

    
}
