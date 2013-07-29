/*******************************************************************************
 * eAdventure (formerly <e-Adventure> and <e-Game>) is a research project of the e-UCM
 *         research group.
 *  
 *   Copyright 2005-2010 e-UCM research group.
 * 
 *   You can access a list of all the contributors to eAdventure at:
 *         http://e-adventure.e-ucm.es/contributors
 * 
 *   e-UCM is a research group of the Department of Software Engineering
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
 *     eAdventure is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     eAdventure is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 * 
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with Adventure.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package es.eucm.eadventure.engine.core.gui.splashscreen;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Timer;
import java.util.TimerTask;

import es.eucm.eadventure.engine.core.gui.GUI;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;



public class SplashScreenImpl1_4 implements SplashScreen{

    ///////////////////////////////////////////////////////
    // Public config fields
    ///////////////////////////////////////////////////////
    
    /**
     * Delay in milliseconds before task is to be executed.
     */
    public static final int TASK_DELAY=20;
    
    /**
     * Time in milliseconds between successive task executions.
     */
    public static final int TASK_PERIOD=10;
    
    /**
     * Default color for the loading box color. RGB or RGBA in hex format are supported.
     */
    public static final String DEFAULT_BOX_COLOR = "#ef8a08";
    
    /**
     * Default color for the loading border box color. RGB or RGBA in hex format are supported.
     */
    public static final String DEFAULT_BOX_BORDER_COLOR = "#000000";
    
    /**
     * Default color for the sphere color. RGB or RGBA in hex format are supported.
     */
    public static final String DEFAULT_SPHERE_COLOR = "#000000";
    
    /**
     * Default left upper X for the loading box.
     */
    public static final int DEFAULT_BOX_X=200;
    
    /**
     * Default left upper Y for the loading box.
     */
    public static final int DEFAULT_BOX_Y=300;

    /**
     * Default total width for the loading box.
     */
    public static final int DEFAULT_BOX_WIDTH=400;
    
    /**
     * Default total height for the loading box.
     */
    public static final int DEFAULT_BOX_HEIGHT=50;
    
    /**
     * Default arc size for the loading box.
     */
    public static final int DEFAULT_BOX_ARC_SIZE=10;
    
    /**
     * Default stroke for the loading box border.
     */
    public static final float DEFAULT_BOX_BORDER_STROKE=4.0f;
    
    /**
     * Default Maximum radius for the sphere.
     */
    public static final int DEFAULT_SPHERE_MAX_RAD=30;
    
    /**
     * Default minimum radius for the sphere.
     */
    public static final int DEFAULT_SPHERE_MIN_RAD=5;
    
    /**
     * Default step for the sphere's animation.
     */
    public static final int DEFAULT_SPHERE_RAD_INC=1;
    
    /**
     * Default left upper X for the sphere.
     */
    public static final int DEFAULT_SPHERE_X=400;
    
    /**
     * Default left upper Y for the sphere.
     */
    public static final int DEFAULT_SPHERE_Y=100;


    ///////////////////////////////////////////////////////
    // Private config fields
    ///////////////////////////////////////////////////////

    /**
     * Color for the loading box (background).
     */
    protected Color boxColor;

    /**
     * Color for the border of the loading box.
     */
    protected Color boxBorderColor;
    
    /**
     * Color for the animated sphere.
     */
    protected Color sphereColor;
    
    /**
     * Box Border stroke
     */
    protected float boxBorderStroke;

    /*
     * Config fields for deciding if the box and sphere are to be displayed
     */
    protected boolean showLoadingBox;
    protected boolean showSphere;

    
    /*
     * Coordinates for the box
     */
    protected int boxX;
    protected int boxY;
    protected int boxWidth;
    protected int boxHeight;
    protected int boxArcSize;

    /*
     * Coordinates for the animated sphere
     */
    protected int sphereMaxRadius;
    protected int sphereMinRadius;
    protected int sphereRadiusIncrement;
    protected int sphereX;
    protected int sphereY;

    /*
     * Image and precent
     */
    protected Image loadingImage;
    protected int loading;

    /*
     * TImer for updates
     */
    protected Timer loadingTimer;
    protected TimerTask loadingTask;
    
    
    public SplashScreenImpl1_4 (){
        this.boxX=DEFAULT_BOX_X;
        this.boxY=DEFAULT_BOX_Y;
        this.boxWidth=DEFAULT_BOX_WIDTH;
        this.boxHeight=DEFAULT_BOX_HEIGHT;
        this.boxArcSize=DEFAULT_BOX_ARC_SIZE;
        
        this.boxBorderStroke = DEFAULT_BOX_BORDER_STROKE;
        
        this.sphereMaxRadius=DEFAULT_SPHERE_MAX_RAD;
        this.sphereMinRadius=DEFAULT_SPHERE_MIN_RAD;
        this.sphereRadiusIncrement=DEFAULT_SPHERE_RAD_INC;
        this.sphereX=DEFAULT_SPHERE_X;
        this.sphereY=DEFAULT_SPHERE_Y;
        
        this.boxColor=fromHexToColor( DEFAULT_BOX_COLOR );
        this.boxBorderColor=fromHexToColor( DEFAULT_BOX_BORDER_COLOR );
        this.sphereColor=fromHexToColor( DEFAULT_SPHERE_COLOR );
        
        this.showLoadingBox=true;
        this.showSphere=true;
    }

    protected static Color fromHexToColor( String hex ){
        Color color=null;
        if (hex!=null && hex.startsWith( "#" ) && hex.length( )>=7){
            int red = Integer.parseInt( hex.substring( 1,3 ), 16 );
            int green = Integer.parseInt( hex.substring( 3,5 ), 16 );
            int blue = Integer.parseInt( hex.substring( 5,7 ), 16 );
            if (hex.length( )>=9){
                int alpha = Integer.parseInt( hex.substring( 7,9 ), 16 );
                color = new Color(red, green, blue, alpha);
            } else {
                color = new Color(red, green, blue);
            }
        }
        return color;
    }
    
    protected String getBackgroundImagePath(){
        return "gui/loading.jpg";
    }
    
    public void loading( Graphics2D g, int percent ) {

        if( percent == 0 ) {

            // FIXME Chapucilla que huele a pipi
            //this.loadingImage =  new ImageIcon("gui/loading.jpg").getImage();
            this.loadingImage = MultimediaManager.getInstance( ).loadImage( getBackgroundImagePath(), MultimediaManager.IMAGE_MENU );
            if( this.loadingImage == null ) {
                this.loadingImage = MultimediaManager.getInstance( ).loadImageFromZip( getBackgroundImagePath(), MultimediaManager.IMAGE_MENU );
            }
            this.loading = percent;
            loadingTimer = new Timer( );
            loadingTask = new LoadingTimerTask( g );
            loadingTimer.scheduleAtFixedRate( loadingTask, TASK_DELAY, TASK_PERIOD );
        }
        if( percent == 100 ) {
            loadingTimer.cancel( );
        }
        this.loading = percent;

        g.drawImage( loadingImage, 0, 0, null );

        if (showLoadingBox){
            g.setColor( boxColor );
            g.fillRoundRect( boxX, boxY, boxWidth*loading/100, boxHeight, boxArcSize, boxArcSize );
    
            g.setStroke( new BasicStroke( boxBorderStroke) );
            g.setColor( boxBorderColor );
            g.drawRoundRect( boxX, boxY, boxWidth, boxHeight, boxArcSize, boxArcSize );
        }

        GUI.getInstance().endDraw( );
    }


    private class LoadingTimerTask extends TimerTask{
        private Graphics2D g;

        private int cont;

        private boolean contracting;
        
        public LoadingTimerTask(Graphics2D g){
            this.g=g;
            cont = 2*sphereMinRadius;
            contracting = false;
        }
        

        @Override
        public void run( ) {

            g.drawImage( loadingImage, 0, 0, null );

            // Draw box
            if (showLoadingBox){
                g.setColor( boxColor );
                g.fillRoundRect( boxX, boxY, loading*4, boxHeight, boxArcSize, boxArcSize );
                g.setStroke( new BasicStroke( boxBorderStroke ) );
                g.setColor( boxBorderColor );
                g.drawRoundRect( boxX, boxY, boxWidth, boxHeight, boxArcSize, boxArcSize );
            }

            // Draw sphere
            if (showSphere){
                g.setColor( sphereColor );
                g.fillOval( sphereX - cont, sphereY - cont, cont*2, cont*2 );
            }

            // Update sphere
            if( !contracting ) {
                cont += sphereRadiusIncrement;
                if( cont > sphereMaxRadius )
                    contracting = true;
            }
            else {
                cont -= sphereRadiusIncrement;
                if( cont < sphereMinRadius )
                    contracting = false;
            }

            GUI.getInstance().endDraw( );
        }
    }
}
