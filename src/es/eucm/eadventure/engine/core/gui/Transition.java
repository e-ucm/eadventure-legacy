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
package es.eucm.eadventure.engine.core.gui;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import es.eucm.eadventure.common.data.chapter.NextScene;

/**
 * This class creates and manages the graphic transitions between scenes of the
 * game
 * 
 * @author Eugenio Marchiori
 */
public class Transition {

    /**
     * Total time of the current transition
     */
    private int totalTime;

    /**
     * Type of the current transition
     */
    private int type;

    /**
     * Elapsed time for the current transition
     */
    private long elapsedTime;

    /**
     * True if the current transition has already started
     */
    private boolean started;

    /**
     * The bufferd image of the transition
     */
    private Image transitionImage;

    /**
     * Temporary image for the transition
     */
    private static BufferedImage tempImage = new BufferedImage( GUI.WINDOW_WIDTH, GUI.WINDOW_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR );

    public Transition( int transitionTime, int transitionType ) {
        this.totalTime = transitionTime;
        this.type = transitionType;
        this.elapsedTime = 0;
        this.started = false;
        this.transitionImage = new BufferedImage( GUI.WINDOW_WIDTH, GUI.WINDOW_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR );
    }

    public boolean hasFinished( long elapsedTime ) {

        this.elapsedTime += elapsedTime;
        if( started && this.elapsedTime > totalTime ) {
            return true;
        }
        return false;
    }

    public boolean hasStarted( ) {

        return started;
    }

    public Graphics getGraphics( ) {

        return transitionImage.getGraphics( );
    }

    public void start( Graphics2D g ) {

        started = true;
        g.drawImage( transitionImage, 0, 0, null );
        this.elapsedTime = 0;
    }

    public void update( Graphics2D g ) {
        
        if (tempImage == null || transitionImage == null || g == null) {
            elapsedTime = totalTime + 1;
            return;
        }
        
        if( started ) {
            Graphics2D g2 = tempImage.createGraphics( );
            GUI.getInstance( ).drawToGraphics( g2 );
            g2.dispose( );

            float temp = (float) this.elapsedTime / (float) totalTime;
            if( type == NextScene.RIGHT_TO_LEFT ) {
                float temp2 = GUI.WINDOW_WIDTH * temp;
                g.drawImage( transitionImage, (int) ( -temp2 ), 0, null );
                g.drawImage( tempImage, (int) ( GUI.WINDOW_WIDTH - temp2 ), 0, null );
            }
            else if( type == NextScene.LEFT_TO_RIGHT ) {
                float temp2 = GUI.WINDOW_WIDTH * temp;
                g.drawImage( transitionImage, (int) ( temp2 ), 0, null );
                g.drawImage( tempImage, (int) ( temp2 - GUI.WINDOW_WIDTH ), 0, null );
            }
            else if( type == NextScene.TOP_TO_BOTTOM ) {
                float temp3 = GUI.WINDOW_HEIGHT * temp;
                g.drawImage( transitionImage, 0, (int) temp3, null );
                g.drawImage( tempImage, 0, (int) ( temp3 - GUI.WINDOW_HEIGHT ), null );
            }
            else if( type == NextScene.BOTTOM_TO_TOP ) {
                float temp3 = GUI.WINDOW_HEIGHT * temp;
                g.drawImage( transitionImage, 0, (int) -temp3, null );
                g.drawImage( tempImage, 0, (int) ( GUI.WINDOW_HEIGHT - temp3 ), null );
            }
            else if( type == NextScene.FADE_IN ) {
                g.drawImage( tempImage, 0, 0, null );
                AlphaComposite alphaComposite = AlphaComposite.getInstance( AlphaComposite.SRC_OVER, 1 - temp );
                g.setComposite( alphaComposite );
                g.drawImage( transitionImage, 0, 0, null );
            }
        }
    }

}
