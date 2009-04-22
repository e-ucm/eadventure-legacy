package es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import es.eucm.eadventure.common.data.chapter.effects.ShowTextEffect;
import es.eucm.eadventure.common.data.chapter.effects.WaitTimeEffect;
import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.control.Options;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalConditions;
import es.eucm.eadventure.engine.core.gui.GUI;


/**
 * An effect that shows the given text in current scene.
 */
public class FunctionalShowTextEffect extends FunctionalEffect{

    
    /**
     * The text that will be shown
     */
    private String[] text;
    
    /**
     * The time that text will be shown
     */
    private int timeShown;
    
    /**
     * Boolean to control is effect is running
     */
    private boolean isStillRunning;
    
    /**
     * The timer which controls the time that text is shown
     */
    private Timer timer;
    
    /**
     * Constructor
     * @param effect
     */
    FunctionalShowTextEffect(ShowTextEffect effect){
	super(effect);
	// split the text if don't fit in the screen
	this.text = GUI.getInstance( ).splitText( effect.getText() );

	// obtain the text speed
        float multiplier = 1;
        if( Game.getInstance( ).getOptions( ).getTextSpeed( ) == Options.TEXT_SLOW ) multiplier = 1.5f;
        if( Game.getInstance( ).getOptions( ).getTextSpeed( ) == Options.TEXT_FAST ) multiplier = 0.8f;
        
        // calculate the time that text will be shown
        timeShown = (int)( 300 * effect.getText().split( " " ).length * multiplier );
        if( timeShown < (int)( 1400 * multiplier ) ) timeShown = (int)( 1400 * multiplier );
        
        this.isStillRunning=false;
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
    public void triggerEffect(){
	
	timer = new Timer (timeShown*2, new ActionListener ()
	{
	    public void actionPerformed(ActionEvent e) {
		isStillRunning=false;
		timer.stop();		
	    }
	}); 
	isStillRunning=true;
	timer.start();
	
    }
    
    public void draw(){
	GUI.getInstance( ).getGraphics().setFont(GUI.getInstance( ).getGraphics().getFont( ).deriveFont( 18.0f ));
	    FontMetrics fontMetrics =GUI.getInstance( ).getGraphics().getFontMetrics();
	    GUI.getInstance( ).addTextToDraw( text, ((ShowTextEffect)effect).getX()- Game.getInstance( ).getFunctionalScene( ).getOffsetX( ), ((ShowTextEffect)effect).getY() -  (fontMetrics.stringWidth( ((ShowTextEffect)effect).getText() ) /2), new Color(((ShowTextEffect)effect).getRgbFrontColor()), new Color(((ShowTextEffect)effect).getRgbBorderColor()));
	
    }
 }



