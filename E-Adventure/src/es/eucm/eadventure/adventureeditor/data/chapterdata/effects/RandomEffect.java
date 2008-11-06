package es.eucm.eadventure.adventureeditor.data.chapterdata.effects;


public class RandomEffect implements Effect{

    private Effect positiveEffect;
    
    private Effect negativeEffect;
    
    private int probability;
	
    public RandomEffect ( int probability ){
    	this.probability = probability;
    }
    
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
