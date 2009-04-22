package es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;


import es.eucm.eadventure.common.data.chapter.effects.WaitTimeEffect;


/**
 * An effect that blocks game during set seconds.
 */
public class FunctionalWaitTimeEffect extends FunctionalEffect{

    private boolean isStillRunning;
   
    private Timer timer;
    
    public FunctionalWaitTimeEffect(WaitTimeEffect effect){
	super(effect);
	isStillRunning=false;
    }
    
    @Override
    public boolean isInstantaneous() {
	return false;
    }

    @Override
    public boolean isStillRunning() {
	    
	return isStillRunning;
    }

    @Override
    public void triggerEffect() {
	
	timer = new Timer (((WaitTimeEffect)effect).getTime()*1000, new ActionListener ()
	{
	    public void actionPerformed(ActionEvent e) {
		isStillRunning=false;
		timer.stop();		
	    }
	}); 
	isStillRunning=true;
	timer.start();
	//while (isStillRunning){};
	
    }

}
