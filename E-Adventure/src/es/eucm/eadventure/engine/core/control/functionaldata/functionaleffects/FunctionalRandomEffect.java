package es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects;

import java.util.Random;

import es.eucm.eadventure.common.data.chapter.effects.Effect;
import es.eucm.eadventure.common.data.chapter.effects.RandomEffect;

/**
 * Functional class for RandomEffect
 * @author Javier
 */
public class FunctionalRandomEffect extends FunctionalEffect{

    private static final Random r = new Random();
    
    /**
     * Functional positive effect
     */
    private FunctionalEffect positiveEffect;
    
    /**
     * Functional negative effect
     */
    private FunctionalEffect negativeEffect;
    
    private boolean positive;

    /**
     * Effect to be triggered: Points to positiveEffect or negativeEffect
     */
    private FunctionalEffect effectTriggered;
    
    public FunctionalRandomEffect (RandomEffect effect){
    	super(effect);
    	this.positiveEffect = FunctionalEffect.buildFunctionalEffect( effect.getPositiveEffect() );
    	this.negativeEffect = FunctionalEffect.buildFunctionalEffect( effect.getNegativeEffect() );
    }
    
    private FunctionalEffect getEffectToBeTriggered(){
        int number = r.nextInt( 100 ); 
        positive = number < ((RandomEffect)effect).getProbability();
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

}
