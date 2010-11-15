/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *         research group.
 *  
 *   Copyright 2005-2010 <e-UCM> research group.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *         http://e-adventure.e-ucm.es/contributors
 * 
 *   <e-UCM> is a research group of the Department of Software Engineering
 *         and Artificial Intelligence at the Complutense University of Madrid
 *         (School of Computer Science).
 * 
 *         C Profesor Jose Garcia Santesmases sn,
 *         28040 Madrid (Madrid), Spain.
 * 
 *         For more info please visit:  <http://e-adventure.e-ucm.es> or
 *         <http://www.e-ucm.es>
 * 
 * ****************************************************************************
 * 
 * This file is part of <e-Adventure>, version 1.2.
 * 
 *     <e-Adventure> is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     <e-Adventure> is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 * 
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.util.Hashtable;

import javax.swing.Timer;

import es.eucm.eadventure.common.data.chapter.effects.ShowTextEffect;
import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.control.Options;
import es.eucm.eadventure.engine.core.gui.GUI;

/**
 * An effect that shows the given text in current scene.
 */
public class FunctionalShowTextEffect extends FunctionalEffect {

    /**
     * The text that will be shown
     */
    private String text;

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
     * Value to control when the user skip the text
     */
    private boolean skipByUser;

    /**
     * Constructor
     * 
     * @param effect
     */
    FunctionalShowTextEffect( ShowTextEffect effect ) {

        super( effect );
        // split the text if don't fit in the screen
        this.text = effect.getText( );

        // obtain the text speed
        float multiplier = 1;
        if( Game.getInstance( ).getOptions( ).getTextSpeed( ) == Options.TEXT_SLOW )
            multiplier = 1.5f;
        if( Game.getInstance( ).getOptions( ).getTextSpeed( ) == Options.TEXT_FAST )
            multiplier = 0.8f;

        // calculate the time that text will be shown
        timeShown = (int) ( 300 * effect.getText( ).split( " " ).length * multiplier );
        if( timeShown < (int) ( 1400 * multiplier ) )
            timeShown = (int) ( 1400 * multiplier );

        this.isStillRunning = false;
        skipByUser = false;
    }

    @Override
    public boolean isInstantaneous( ) {

        return false;
    }

    @Override
    public boolean isStillRunning( ) {

        return isStillRunning;
    }

    @Override
    public void triggerEffect( ) {

        // FIXME: Convendría cambiar esto para que no se use un timer
        timer = new Timer( timeShown * 2, new ActionListener( ) {

            public void actionPerformed( ActionEvent e ) {

                finish();
                
                
              
            }
        } );
        isStillRunning = true;
        timer.start( );

    }

    public void draw( ) {

        GUI.getInstance( ).getGraphics( ).setFont( GUI.getInstance( ).getGraphics( ).getFont( ).deriveFont( 18.0f ) );
        Hashtable attributes = new Hashtable();
        attributes.put(TextAttribute.WIDTH, TextAttribute.WIDTH_SEMI_EXTENDED);
        GUI.getInstance( ).getGraphics( ).setFont( GUI.getInstance( ).getGraphics( ).getFont( ).deriveFont( attributes ) );
        GUI.getInstance( ).addTextToDraw( text, ( (ShowTextEffect) effect ).getX( ) - Game.getInstance( ).getFunctionalScene( ).getOffsetX( ), ( (ShowTextEffect) effect ).getY( ), new Color( ( (ShowTextEffect) effect ).getRgbFrontColor( ) ), new Color( ( (ShowTextEffect) effect ).getRgbBorderColor( ) ) );

    }
    
    private void finish(){
        if (Game.getInstance( ).getGameDescriptor( ).isKeepShowing( )&&!skipByUser)
            timer.restart( );
        else{    
                isStillRunning = false;
                timer.stop( );
        }
    }
    
    @Override
    public boolean canSkip( ) {
        return true;
    }

    @Override
    public void skip( ) {
       skipByUser = true;
       finish();
    }
}
