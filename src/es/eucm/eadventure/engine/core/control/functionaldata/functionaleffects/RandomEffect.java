package es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects;

import java.util.Random;

public class RandomEffect implements Effect{

    private static final Random r = new Random();
    
    private Effect positiveEffect;
    
    private Effect negativeEffect;
    
    private boolean positive;
    
    private int probability;
    
    private Effect effectTriggered;
    
    public RandomEffect (int probability){
        this.probability = probability;
    }
    
    private Effect getEffectToBeTriggered(){
        int number = r.nextInt( 100 ); 
        positive = number < probability;
        if (positive){
            //System.out.println(number+" < "+probability+" : Triggering positive effect" );
        } else {
            //System.out.println(number+" >= "+probability+" : Triggering negative effect" );
        }

        if (positive)
            return positiveEffect;
        return negativeEffect;
    }
    
    public boolean isInstantaneous( ) {
        if (effectTriggered!=null)
                return effectTriggered.isInstantaneous( );
        return false;
    }

    public boolean isStillRunning( ) {
        if (effectTriggered!=null)
            return effectTriggered.isStillRunning( );
        return false;
    }

    public void triggerEffect( ) {
        effectTriggered =getEffectToBeTriggered();
        if (effectTriggered!=null){
            effectTriggered.triggerEffect( );
        }
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

}
