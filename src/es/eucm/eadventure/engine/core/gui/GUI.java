/**
 * <e-Adventure> is an <e-UCM> research project. <e-UCM>, Department of Software
 * Engineering and Artificial Intelligence. Faculty of Informatics, Complutense
 * University of Madrid (Spain).
 * 
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J. (alphabetical order) *
 * @author L�pez Ma�as, E., P�rez Padilla, F., Sollet, E., Torijano, B. (former
 *         developers by alphabetical order)
 * @author Moreno-Ger, P. & Fern�ndez-Manj�n, B. (directors)
 * @year 2009 Web-site: http://e-adventure.e-ucm.es
 */

/*
 * Copyright (C) 2004-2009 <e-UCM> research group
 * 
 * This file is part of <e-Adventure> project, an educational game & game-like
 * simulation authoring tool, available at http://e-adventure.e-ucm.es.
 * 
 * <e-Adventure> is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * <e-Adventure> is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * <e-Adventure>; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 */
package es.eucm.eadventure.engine.core.gui;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Cursor;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

import es.eucm.eadventure.engine.core.control.TimerManager;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalElement;
import es.eucm.eadventure.engine.core.control.functionaldata.functionalhighlights.FunctionalHighlight;
import es.eucm.eadventure.engine.core.gui.hud.HUD;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;

/**
 * This is the main class related with the graphics in eAdventure, including the
 * window
 */
public abstract class GUI implements FocusListener {

    /**
     * Applet gui type id
     */
    public static final int GUI_APPLET = 1;

    /**
     * Frame (window) gui type id
     */
    public static final int GUI_FRAME = 0;

    /**
     * Type of the GUI
     */
    protected static int GUIType = 0;

    /**
     * Width of the window
     */
    public static final int WINDOW_WIDTH = 800;

    /**
     * Height of the window
     */
    public static final int WINDOW_HEIGHT = 600;

    /**
     * Max width of the text spoken in the game
     */
    public static final int MAX_WIDTH_IN_TEXT = 300;

    public static final String DEFAULT_CURSOR = "default";

    /**
     * Antialiasing of the game shapes
     */
    public boolean ANTIALIASING = true;

    /**
     * Antialiasing of the game text
     */
    public boolean ANTIALIASING_TEXT = true;

    /**
     * The frame/window of the game
     */
    protected Canvas gameFrame;

    /**
     * The HUE element
     */
    protected HUD hud;

    /**
     * Graphic configuration value
     */
    protected static int graphicConfig;

    /**
     * The GUI singleton class
     */
    protected static GUI instance = null;

    /**
     * The default cursor
     */
    protected Cursor defaultCursor;

    /**
     * Background image of the scene.
     */
    protected SceneImage background;

    /**
     * Foreground image of the scene.
     */
    protected SceneImage foreground;

    /**
     * List of elements to be painted.
     */
    protected ArrayList<ElementImage> elementsToDraw;

    /**
     * List of texts to be painted.
     */
    protected ArrayList<Text> textToDraw;
    
    /**
     * Stores the static component for show (report,book,video, etc)
     */
    protected Component component;

    private Transition transition = null;

    private boolean showsOffsetArrows = false;

    private boolean moveOffsetRight = false;

    private boolean moveOffsetLeft = false;
    
    /**
     * This attribute store the interactive elements (which can be painted) in the same order
     * that they will be painted.
     */
    private List<FunctionalElement> elementsToInteract = null;
    
    private Image loadingImage;
    
    private int loading;
    
    private Timer loadingTimer;
    
    private TimerTask loadingTask;
    
    /**
     * Return the GUI instance. GUI is a singleton class.
     * 
     * @return GUI sigleton instance
     */
    public static GUI getInstance( ) {

        if( instance == null )
            create( );
        return instance;
    }

    /**
     * Create the singleton instance
     */
    public static void create( ) {

        if( GUIType == GUI.GUI_APPLET )
            instance = new GUIApplet( );
        else
            instance = new GUIFrame( );
    }

    /**
     * Destroy the singleton instance
     */
    public static void delete( ) {

        if( instance instanceof GUIFrame )
            GUIFrame.delete( );
        if( instance instanceof GUIApplet )
            GUIApplet.delete( );
    };

    /**
     * Init the GUI class and also get focus for the mainwindow
     */
    public abstract void initGUI( int guiType, boolean customized );

    /**
     * Displays a Swing or AWT component in the game window.
     * <p>
     * To remove the component, use RestoreFrame method.
     * 
     * @param component
     * @return
     */
    public abstract JFrame showComponent( Component component );
    
    public abstract JFrame showComponent( Component component, int w, int h );

    /**
     * Restores the frame to its original state after displaying a Swing or AWT
     * component.
     * 
     */
    public abstract void restoreFrame( );

    /**
     * Returns the width of the playable area of the screen
     * 
     * @return Width of the playable area
     */
    public int getGameAreaWidth( ) {

        return hud.getGameAreaWidth( );
    }

    /**
     * Returns the height of the playable area of the screen
     * 
     * @return Height of the playable area
     */
    public int getGameAreaHeight( ) {

        return hud.getGameAreaHeight( );
    }

    /**
     * Repaint call to the "component"
     */
    public void componentRepaint(){
	if (component!=null)
		component.repaint();
    }
    
    /**
     * Returns the X point of the response block text
     * 
     * @return X point of the response block text
     */
    public int getResponseTextX( ) {

        return hud.getResponseTextX( );
    }

    /**
     * Returns the Y point of the response block text
     * 
     * @return Y point of the response block text
     */
    public int getResponseTextY( ) {

        return hud.getResponseTextY( );
    }

    /**
     * Returns the number of lines of the response text block
     * 
     * @return Number of response lines
     */
    public int getResponseTextNumberLines( ) {

        return hud.getResponseTextNumberLines( );
    }

    /**
     * Gets the graphics context for the display. The ScreenManager uses double
     * buffering, so applications must call update() to show any graphics drawn.
     * The application must dispose of the graphics object.
     */
    public Graphics2D getGraphics( ) {

        BufferStrategy strategy = gameFrame.getBufferStrategy( );
        Graphics2D g = (Graphics2D) strategy.getDrawGraphics( );
        //Graphics2D g = (Graphics2D)panel.getGraphics();

        if( g == null ) {
            //System.out.println( "Error: Graphics2D = null " );
        }
        else {
            // Load antialiasing if not loaded and is requested
            if( ANTIALIASING && !g.getRenderingHints( ).containsValue( RenderingHints.VALUE_ANTIALIAS_ON ) ) {
                g.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
            }
            // Unload antialiasing if loaded and is not requested 
            if( !ANTIALIASING && !g.getRenderingHints( ).containsValue( RenderingHints.VALUE_ANTIALIAS_OFF ) ) {
                g.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF );
            }
            // Load antialiased text if not loaded and is requested
            if( ANTIALIASING_TEXT && !g.getRenderingHints( ).containsValue( RenderingHints.VALUE_TEXT_ANTIALIAS_ON ) ) {
                g.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON );
            }
            // Unload antialiased text if loaded and is not requested
            if( !ANTIALIASING_TEXT && !g.getRenderingHints( ).containsValue( RenderingHints.VALUE_TEXT_ANTIALIAS_OFF ) ) {
                g.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF );
            }
        }
        return g;
    }

    /**
     * The last call to draw in the display.
     */
    public void endDraw( ) {

        BufferStrategy strategy = gameFrame.getBufferStrategy( );
        strategy.show( );
        Toolkit.getDefaultToolkit( ).sync( );
    }

    /**
     * Updates the GUI.
     */
    public void update( long elapsedTime ) {

        hud.update( elapsedTime );
    }

    /**
     * Returns the frame that is the window
     * 
     * @return Frame the main window
     */
    public Canvas getFrame( ) {

        return gameFrame;
    }

    /**
     * Gets the GraphicsConfiguration of the window
     * 
     * @return GraphicsConfiguration of the JFrame
     */
    public GraphicsConfiguration getGraphicsConfiguration( ) {

        return gameFrame.getGraphicsConfiguration( );
    }

    /**
     * Draw the text array with a border, given the lower-left coordinate if
     * centeredX is false or the lower-middle if it is true
     * 
     * @param g
     *            Graphics2D where make the painting
     * @param string
     *            String to be drawn
     * @param x
     *            Int coordinate of the left (or middle) of the string to be
     *            drawn
     * @param y
     *            Int coordinate of the bottom of the string to be drawn
     * @param centeredX
     *            boolean if the x is middle
     * @param textColor
     *            Color of the text
     * @param borderColor
     *            Color of the border of the text
     * @param border
     *            whether to paint a border on the text
     */
    public static void drawStringOnto( Graphics2D g, String string, int x, int y, boolean centeredX, Color textColor, Color borderColor, boolean border ) {

        //Get the current text font metrics (width and hegiht)
        FontMetrics fontMetrics = g.getFontMetrics( );
        double width = fontMetrics.stringWidth( string );
        double height = fontMetrics.getAscent( );
        int realX = x;
        int realY = y;

        //If the text is centered in its X coordinate
        if( centeredX ) {
            //Check if the text don't go out of the window horizontally
            //and if it do correct it so it's in the window
            if( realX + width / 2 > WINDOW_WIDTH ) {
                realX = (int) ( WINDOW_WIDTH - width / 2 );
            }
            else if( realX - width / 2 < 0 ) {
                realX = (int) ( width / 2 );
            }
            realX -= width / 2;
            //Check if the text don't go out of the window vertically
            //and if it do correct it so it's in the window
            if( realY > WINDOW_HEIGHT ) {
                realY = WINDOW_HEIGHT;
            }
            else if( realY - height < 0 ) {
                realY = (int) height;
            }
            //if it's not centered
        }
        else {
            //Check if the text don't go out of the window horizontally
            //and if it do correct it so it's in the window
            //FIXME nuevo, a ver si funciona
            /*if( realX + width > WINDOW_WIDTH ) {
                realX = (int) ( WINDOW_WIDTH - width );
            } else if( realX < 0 ) {
                realX = 0;
            }*/
            if( realX + width > WINDOW_WIDTH ) {
                realX = 0;
                //To know the width of one character
                double w = fontMetrics.stringWidth( new String( "A" ) );
                int position = (int) ( WINDOW_WIDTH / w ) + 18;
                string = string.substring( 0, position );
                string = string + "...";
            }
            //Check if the text don't go out of the window vertically
            //and if it do correct it so it's in the window
            if( realY > WINDOW_HEIGHT ) {
                realY = WINDOW_HEIGHT;
            }
            else if( realY - height < 0 ) {
                realY = (int) height;
            }
        }
        //If the text has border, draw it
        if( border ) {
            g.setColor( borderColor );
            g.drawString( string, realX - 1, realY - 1 );
            g.drawString( string, realX - 1, realY + 1 );
            g.drawString( string, realX + 1, realY - 1 );
            g.drawString( string, realX + 1, realY + 1 );
            g.setColor( textColor );
        }
        //Draw the text
        g.drawString( string, realX, realY );
    }

    /**
     * Draw the text array with a border, given the lower-middle position of the
     * text
     * 
     * @param g
     *            Graphics2D where to draw
     * @param strings
     *            String[] of Strings to be drawn
     * @param x
     *            Int coordinate of the middle of the string to be drawn
     * @param y
     *            Int coordinate of the bottom of the string to be drawn
     * @param textColor
     *            Color of the text
     * @param borderColor
     *            Color of the border of the text
     */
    public static void drawStringOnto( Graphics2D g, String[] strings, int x, int y, Color textColor, Color borderColor ) {

        //Calculate the total height of the block text
        FontMetrics fontMetrics = g.getFontMetrics( );
        int textBlockHeight = fontMetrics.getHeight( ) * strings.length - fontMetrics.getLeading( );

        // This is the y lower position of the first line
        int realY = y - textBlockHeight + fontMetrics.getAscent( );
        if( realY < fontMetrics.getAscent( ) )
            realY = fontMetrics.getAscent( );

        //Draw each line of the string array
        for( String line : strings ) {
            drawStringOnto( g, line, x, realY, true, textColor, borderColor, true );
            realY += fontMetrics.getHeight( );
        }
    }

    /**
     * Draw the text array with a border, given the lower-middle position of the
     * text with a speech bubble of the given colors as background
     * 
     * @param g
     *            Graphics2D where to draw
     * @param strings
     *            String[] of the strings to be drawn
     * @param x
     *            int coordinate of the middle of the string to be drawn
     * @param y
     *            int coordinate of the bottom of the string to be drawn
     * @param textColor
     *            Color of the text
     * @param borderColor
     *            Color of the border of the text
     * @param bkgColor
     *            Color of the background of the bubble
     * @param bubbleBorder
     *            Color of the border of the bubble
     */
    public static void drawStringOnto( Graphics2D g, String[] strings, int x, int y, Color textColor, Color borderColor, Color bkgColor, Color bubbleBorder, boolean showArrow ) {

        FontMetrics fontMetrics = g.getFontMetrics( );
        int textBlockHeight = fontMetrics.getHeight( ) * strings.length - fontMetrics.getLeading( );

        int maxWidth = 25;
        for( String line : strings )
            maxWidth = ( fontMetrics.stringWidth( line ) > maxWidth ? (int) fontMetrics.stringWidth( line ) : maxWidth );

        int tempX = x;
        int tempY = y;
        if( tempX - maxWidth / 2 < 0 )
            tempX = maxWidth / 2;
        if( tempY - textBlockHeight < 0 )
            tempY = textBlockHeight;
        if( tempX + maxWidth / 2 > GUI.WINDOW_WIDTH )
            tempX = GUI.WINDOW_WIDTH - maxWidth / 2;

        AlphaComposite alphaComposite = AlphaComposite.getInstance( AlphaComposite.SRC_OVER, 0.8f );
        Composite temp = g.getComposite( );
        g.setComposite( alphaComposite );
        g.setColor( bkgColor );
        g.fillRoundRect( tempX - maxWidth / 2 - 5, tempY - textBlockHeight - 5, maxWidth + 10, textBlockHeight + 10, 20, 20 );

        g.setComposite( temp );
        g.setColor( bubbleBorder );
        g.drawRoundRect( tempX - maxWidth / 2 - 5, tempY - textBlockHeight - 5, maxWidth + 10, textBlockHeight + 10, 20, 20 );

        if (showArrow) {
            g.setComposite( alphaComposite );
            g.setColor( bkgColor );
            int x_p[] = new int[] { tempX - 10, tempX + 10, tempX };
            int y_p[] = new int[] { tempY + 5, tempY + 5, tempY + 15 };
            g.fillPolygon( x_p, y_p, 3 );
    
            g.setComposite( temp );
            g.setColor( bubbleBorder );
            g.drawLine( x_p[0], y_p[0], x_p[2], y_p[2] );
            g.drawLine( x_p[1], y_p[1], x_p[2], y_p[2] );
        }
        
        drawStringOnto( g, strings, x, y, textColor, borderColor );
    }

    /**
     * Draws the string specified centered (in X and Y) in the given position
     * 
     * @param g
     *            Graphics2D where make the painting
     * @param string
     *            String to be drawed
     * @param x
     *            Center X position of the text
     * @param y
     *            Center Y position of the text
     */
    public static void drawString( Graphics2D g, String string, int x, int y ) {

        // Get the current text font metrics (width and hegiht)
        FontMetrics fontMetrics = g.getFontMetrics( );
        double width = fontMetrics.stringWidth( string );
        double height = fontMetrics.getAscent( );

        int realX = x;
        int realY = y;

        //Check if the text don't go out of the window horizontally
        //and if it do correct it so it's in the window
        if( realX + width / 2 > WINDOW_WIDTH ) {
            realX = (int) ( WINDOW_WIDTH - width / 2 );
        }
        else if( realX - width / 2 < 0 ) {
            realX = (int) ( width / 2 );
        }
        realX -= width / 2;

        //Check if the text don't go out of the window vertically
        //and if it do correct it so it's in the window
        if( realY + height / 2 > WINDOW_HEIGHT ) {
            realY = (int) ( WINDOW_HEIGHT - height / 2 );
        }
        else if( realY < 0 ) {
            realY = 0;
        }
        realY += height / 2;

        //Draw the string
        g.drawString( string, realX, realY );
    }

    /**
     * Draws the given block text centered in the given position
     * 
     * @param g
     *            Graphics2D where make the painting
     * @param strings
     *            Array of strings to be painted
     * @param x
     *            Centered X position of the text block
     * @param y
     *            Centered Y position of the text block
     */
    public static void drawString( Graphics2D g, String[] strings, int x, int y ) {

        //Calculate the total height of the block text
        FontMetrics fontMetrics = g.getFontMetrics( );
        int textBlockHeight = fontMetrics.getHeight( ) * strings.length - fontMetrics.getLeading( ) - fontMetrics.getDescent( );

        // This is the y center position of the first line
        int realY = y - textBlockHeight / 2 + fontMetrics.getAscent( ) / 2;

        //Draw each line of the string array
        for( String line : strings ) {
            drawString( g, line, x, realY );
            realY += fontMetrics.getHeight( );
        }
    }

    /**
     * Split a text in various lines using the font width and an max width for
     * each line
     * 
     * @param text
     *            String that contains all the text to be used
     * @return String[] with the various lines splited from the text
     */
    public String[] splitText( String text ) {

        ArrayList<String> lines = new ArrayList<String>( );
        String currentLine = text;
        boolean exit = false;
        String line;

        int width;
        FontMetrics fontMetrics = getGraphics( ).getFontMetrics( );

        do {
            width = fontMetrics.stringWidth( currentLine );

            if( width > MAX_WIDTH_IN_TEXT ) {
                int lineNumber = (int) Math.ceil( (double) width / (double) MAX_WIDTH_IN_TEXT );
                int index = currentLine.lastIndexOf( ' ', text.length( ) / lineNumber );

                if( index == -1 ) {
                    index = currentLine.indexOf( ' ' );
                }

                if( index == -1 ) {
                    index = currentLine.length( );
                    exit = true;
                }

                line = currentLine.substring( 0, index );
                currentLine = currentLine.substring( index ).trim( );

            }
            else {
                line = currentLine;
                exit = true;
            }

            lines.add( line );
        } while( !exit );
        return lines.toArray( new String[ 1 ] );
    }

    /**
     * Creates a Color from the red, green and blue component
     * 
     * @param r
     *            Red int
     * @param g
     *            Green int
     * @param b
     *            Blue int
     * @return Color corresponding to R,G,B components
     */
    public Color getColor( int r, int g, int b ) {

        float[] hsbvals = Color.RGBtoHSB( r, g, b, null );
        return Color.getHSBColor( hsbvals[0], hsbvals[1], hsbvals[2] );
    }

    /**
     * Draws the scene buffer given a Graphics2D
     * 
     * @param g
     *            Graphics2D to be used by the scene buffer
     */
    public void drawScene( Graphics2D g, long elapsedTime ) {
        if( transition != null && !transition.hasStarted( ) ) {
            drawToGraphics( (Graphics2D) transition.getGraphics( ) );
            transition.start( this.getGraphics( ) );
        }
        else if( transition == null || transition.hasFinished( elapsedTime ) ) {
            transition = null;
            drawToGraphics( g );
        }
        else {
            transition.update( g );
        }
    }
    
    private void recalculateInteractiveElementsOrder(){
        elementsToInteract = new ArrayList<FunctionalElement>();
        for (ElementImage ei:elementsToDraw){
            if (ei.getFunctionalElement( )!=null)
            elementsToInteract.add( ei.getFunctionalElement( ) );
        }
    }

    public void drawToGraphics( Graphics2D g ) {
        if( background != null ) {
            background.draw( g );
            //background = null;
        }
 
        for( ElementImage element : elementsToDraw )
            element.draw( g );
        recalculateInteractiveElementsOrder();
        elementsToDraw.clear( );

 
        if( foreground != null ) {
            foreground.draw( g );
            foreground = null;
        }


        TimerManager timerManager = TimerManager.getInstance( );
        if( timerManager != null ) {
            timerManager.draw( g );
        }


        if( showsOffsetArrows ) {
            g.setColor( Color.BLACK );
            int ytemp = GUI.WINDOW_HEIGHT / 2;
            Composite old = g.getComposite( );
            AlphaComposite alphaComposite = AlphaComposite.getInstance( AlphaComposite.SRC_OVER, 0.6f );

            if( !this.moveOffsetLeft )
                g.setComposite( alphaComposite );
            g.fillOval( -30, ytemp - 30, 60, 60 );

            if( this.moveOffsetRight )
                g.setComposite( old );
            else
                g.setComposite( alphaComposite );
            g.fillOval( GUI.WINDOW_WIDTH - 30, ytemp - 30, 60, 60 );

            g.setComposite( old );
            g.setColor( Color.WHITE );
            Stroke oldStroke = g.getStroke( );
            g.setStroke( new BasicStroke( 5 ) );
            g.drawLine( 5, ytemp, 15, ytemp - 15 );
            g.drawLine( 5, ytemp, 15, ytemp + 15 );
            g.drawLine( GUI.WINDOW_WIDTH - 5, ytemp, GUI.WINDOW_WIDTH - 15, ytemp - 15 );
            g.drawLine( GUI.WINDOW_WIDTH - 5, ytemp, GUI.WINDOW_WIDTH - 15, ytemp + 15 );
            g.setStroke( oldStroke );
        }

        for( Text text : textToDraw )
            text.draw( g );
        textToDraw.clear( );

    }

    public void clearBackground( ) {

        this.background = null;
    }

    /**
     * Draws the HUD given a Graphics2D
     * 
     * @param g
     *            Graphics2D to be used by the HUD
     */
    public void drawHUD( Graphics2D g ) {

        hud.draw( g );
    }

    /**
     * Set the current cursor to the new Cursor
     * 
     * @param cursor
     *            the new cursor
     */
    public void setCursor( Cursor cursor ) {

        gameFrame.setCursor( cursor );
    }

    /**
     * Set the default cursor of the aplication
     */
    public void setDefaultCursor( ) {

        gameFrame.setCursor( defaultCursor );
    }

    /**
     * Function that is called when the window has gained the focus
     */
    public void focusGained( FocusEvent e ) {

        //Do nothing
    }

    /**
     * Function that is called when the window has lost the focus
     */
    public void focusLost( FocusEvent e ) {

        //Request focus
        gameFrame.requestFocusInWindow( );
    }

    /**
     * There has been a click in the HUD
     * 
     * @param e
     *            Mouse event
     * @return boolean If the move is in the HUD
     */
    public boolean mouseClickedInHud( MouseEvent e ) {

        return hud.mouseClicked( e );
    }

    /**
     * There has been a mouse moved in the HUD
     * 
     * @param e
     *            Mouse event
     * @return boolean If the move is in the HUD
     */
    public boolean mouseMovedinHud( MouseEvent e ) {

        return hud.mouseMoved( e );
    }

    /**
     * KeyEvent on HUD
     * 
     * @return boolean True if something done, false otherwise
     */
    public boolean keyInHud( KeyEvent e ) {

        return hud.keyTyped( e );
    }

    /**
     * There is a new action selected
     */
    public void newActionSelected( ) {

        hud.newActionSelected( );
    }

    /**
     * Toggle the HUD on or off
     * 
     * @param show
     *            If the Hud is shown or not
     */
    public void toggleHud( boolean show ) {

        hud.toggleHud( show );
    }

    /**
     * Set the background image
     * 
     * @param background
     *            Background image
     * @param offsetX
     *            Offset of the background
     */
    public void addBackgroundToDraw( Image background, int offsetX ) {
        this.background = new SceneImage( background, offsetX );
    }

    /**
     * Set the foreground image
     * 
     * @param foreground
     *            Background image
     * @param offsetX
     *            Offset of the background
     */
    public void addForegroundToDraw( Image foreground, int offsetX ) {

        this.foreground = new SceneImage( foreground, offsetX );
    }

    /**
     * Adds the image to the array image buffer sorted by its Y coordinate, or
     * its layer, depending on the element has layer or not.
     * 
     * @param image
     *            Image
     * @param x
     *            X coordinate
     * @param y
     *            Y coordinate
     * @param depth
     *            Depth of the image
     */
    public void addElementToDraw( Image image, int x, int y, int depth, int originalY, FunctionalHighlight highlight, FunctionalElement fe ) {

        boolean added = false;
        int i = 0;

        // Create the image to store it 
        ElementImage element = new ElementImage( image, x, y, depth, originalY, highlight, fe );

        // While the element has not been added, and
        // we haven't checked every previous element
        while( !added && i < elementsToDraw.size( ) ) {

            // Insert the element in the correct position
            if( depth <= elementsToDraw.get( i ).getDepth( ) ) {
                elementsToDraw.add( i, element );
                added = true;
            }
            i++;
        }

        // If the element wasn't added, add it in the last position
        if( !added )
            elementsToDraw.add( element );
    }

    /**
     * Adds the image to the array image buffer sorted by its Y coordinate, or
     * its layer, depending on the element has layer or not. This method compare
     * the depth parameter with all elements in elementsToDraw y position. This
     * method is use to insert player without layer in a scene where the rest
     * has layer or not.
     * 
     * @param image
     *            Image
     * @param x
     *            X coordinate
     * @param y
     *            Y coordinate
     * @param depth
     *            Depth of the image
     */
    public void addPlayerToDraw( Image image, int x, int y, int depth, int originalY ) {

        boolean added = false;
        int i = 0;

        // Create the image to store it 
        ElementImage element = new ElementImage( image, x, y, depth, originalY );
        //prepareVirtualElemetsToDraw();
        // While the element has not been added, and
        // we haven't checked every previous element
        while( !added && i < elementsToDraw.size( ) ) {

            // Insert the element in the correct position
            // TODO: Hey guys, watch this carefully!! I've changed this line
            if( depth <= elementsToDraw.get( i ).getOriginalY( ) ) {
                // if( depth <= getRealMinY(elementsToDraw.get( i ), x)+elementsToDraw.get( i ).y) {
                element.setDepth( i );
                elementsToDraw.add( i, element );
                added = true;
            }
            i++;
        }

        // If the element wasn't added, add it in the last position
        if( !added ) {
            element.setDepth( elementsToDraw.size( ) - 1 );
            elementsToDraw.add( element );

        }
    }

    /**
     * Returns the highest Y value of the ElementImage given as argument which
     * is not transparent in the column x. Useful to determine if the player
     * must be painted before the element
     * 
     * @param element
     * @param x
     * @return
     */
    public int getRealMinY( ElementImage element, int x ) {

        boolean isInside = false;

        //int mousex = (int)( x - ( this.x - getWidth( ) *scale/ 2 ) );
        //int mousey = (int)( y - ( this.y - getHeight( ) *scale) );
        int width = element.image.getWidth( null );
        int transformedX = ( x - element.x );
        int minY = element.originalY;
        if( transformedX >= 0 && transformedX < width ) {
            minY = element.image.getHeight( null ) - 1;
            try {
                BufferedImage bufferedImage = (BufferedImage) element.image;
                do {
                    int alpha = bufferedImage.getRGB( transformedX, minY ) >>> 24;
                    minY--;
                    isInside = alpha > 128;
                } while( !isInside && minY > 0 );
            }
            catch( Exception e ) {

            }
        }

        return minY;
    }

    /**
     * Adds the string to the array text buffer sorted by its Y coordinate
     * 
     * @param string
     *            Array string
     * @param x
     *            X coordinate
     * @param y
     *            Y coordinate
     * @param textColor
     *            Color of the string
     * @param borderColor
     *            Color if the border of the string
     */
    public void addTextToDraw( String[] string, int x, int y, Color textColor, Color borderColor) {
        boolean added = false;
        int i = 0;
        Text text = new Text( string, x, y, textColor, borderColor );
        while( !added && i < textToDraw.size( ) ) {
            if( y <= textToDraw.get( i ).getY( ) ) {
                textToDraw.add( i, text );
                added = true;
            }
            i++;
        }
        if( !added )
            textToDraw.add( text );
    }

    /**
     * Adds the string to the array text buffer sorted by its Y coordinate
     * 
     * @param string
     *            Array string
     * @param x
     *            X coordinate
     * @param y
     *            Y coordinate
     * @param textColor
     *            Color of the string
     * @param borderColor
     *            Color if the border of the string
     * @param bubbleBkgColor
     *            Color of the bubbles background
     * @param bubbleBorderColor
     *            Color of the bubbles border
     */
    public void addTextToDraw( String[] string, int x, int y, Color textColor, Color borderColor, Color bubbleBkgColor, Color bubbleBorderColor, boolean showArrow  ) {

        boolean added = false;
        int i = 0;
        Text text = new Text( string, x, y, textColor, borderColor, bubbleBkgColor, bubbleBorderColor, showArrow );
        while( !added && i < textToDraw.size( ) ) {
            if( y <= textToDraw.get( i ).getY( ) ) {
                textToDraw.add( i, text );
                added = true;
            }
            i++;
        }
        if( !added )
            textToDraw.add( text );
    }

    /**
     * Background class that store the image of the background and a screen
     * offset
     */
    protected class SceneImage {

        /**
         * Background image
         */
        private Image background;

        /**
         * Offset of the background
         */
        private int offsetX;

        /**
         * Constructor of the class
         * 
         * @param background
         *            Background image
         * @param offsetX
         *            Offset
         */
        public SceneImage( Image background, int offsetX ) {

            this.background = background;
            this.offsetX = offsetX;
        }

        /**
         * Draw the background with the offset
         * 
         * @param g
         *            Graphics2D to draw the background
         */
        public void draw( Graphics2D g ) {
            g.drawImage( background, -offsetX, 0, null );
//            g.drawImage( background, 0, 0, GUI.WINDOW_WIDTH, GUI.WINDOW_HEIGHT, offsetX, 0, offsetX + GUI.WINDOW_WIDTH, GUI.WINDOW_HEIGHT, gameFrame );
        }
    }

    /**
     * Store a image with its position in the scene
     */
    protected class ElementImage {

        /**
         * Image
         */
        private Image image;

        /**
         * X coordinate
         */
        private int x;

        /**
         * Y coordinate
         */
        private int y;

        /**
         * Original y, without pertinent transformations to fir the original
         * image to scene reference image.
         */
        private int originalY;

        /**
         * Depth of the image (to be painted).
         */
        private int depth;

        private FunctionalHighlight highlight;
        
        private FunctionalElement functionalElement;
        
        /**
         * Constructor of the class
         * 
         * @param image
         *            Image
         * @param x
         *            X coordinate
         * @param y
         *            Y coordinate
         * @param depth
         *            Depth to draw the image
         */
        public ElementImage( Image image, int x, int y, int depth, int originalY ) {
            this.image = image;
            this.x = x;
            this.y = y;
            this.depth = depth;
            this.originalY = originalY;
            this.highlight = null;
        }
        
        public ElementImage( Image image, int x, int y, int depth, int originalY, FunctionalHighlight highlight, FunctionalElement fe) {
            this(image, x, y, depth, originalY);
            this.highlight = highlight;
            this.functionalElement = fe;
        }

        /**
         * Draw the image in the position
         * 
         * @param g
         *            Graphics2D to draw the image
         */
        public void draw( Graphics2D g ) {
            if (highlight != null) {
                g.drawImage( highlight.getHighlightedImage( image ), x + highlight.getDisplacementX( ), y + highlight.getDisplacementY( ), null );
            } else {
                g.drawImage( image, x, y, null );
            }
        }

        /**
         * Returns the depth of the element.
         * 
         * @return Depth of the element
         */
        public int getDepth( ) {

            return depth;
        }

        /**
         * Returns the y of the element
         * 
         * @return The y element�s position
         */
        public int getY( ) {

            return y;
        }

        /**
         * Changes the element�s depth
         * 
         * @param depth
         */
        public void setDepth( int depth ) {

            this.depth = depth;
        }

        /**
         * Returns original Y position, without transformations.
         * 
         * @return
         */
        public int getOriginalY( ) {

            return originalY;
        }

        
        /**
         * @return the functionalElement
         */
        public FunctionalElement getFunctionalElement( ) {
        
            return functionalElement;
        }
    }

    /**
     * Store the array string, its color and border and it's position to be draw
     * onto.
     */
    protected class Text {

        /**
         * Array string
         */
        private String[] text;

        /**
         * X coordinate
         */
        private int x;

        /**
         * Y coordinate
         */
        private int y;

        /**
         * Color of the text
         */
        private Color textColor;

        /**
         * Color of the borde of the text
         */
        private Color borderColor;

        private Color bubbleBkgColor;

        private Color bubbleBorderColor;
        
        private boolean showArrow = true;

        private boolean showBubble = false;

        /**
         * Constructor of the class
         * 
         * @param text
         *            Array string
         * @param x
         *            X coordinate
         * @param y
         *            Y coordinate
         * @param textColor
         *            Color of the text
         * @param borderColor
         *            Color of the borde of the text
         */
        public Text( String[] text, int x, int y, Color textColor, Color borderColor ) {

            this.text = text;
            this.x = x;
            this.y = y;
            this.textColor = textColor;
            this.borderColor = borderColor;
        }

        /**
         * Constructor of the class
         * 
         * @param text
         *            Array string
         * @param x
         *            X coordinate
         * @param y
         *            Y coordinate
         * @param textColor
         *            Color of the text
         * @param borderColor
         *            Color of the borde of the text
         */
        public Text( String[] text, int x, int y, Color textColor, Color borderColor, Color bubbleBkgColor, Color bubbleBorderColor, boolean showArrow ) {

            this.text = text;
            this.x = x;
            this.y = y;
            this.textColor = textColor;
            this.borderColor = borderColor;
            this.showBubble = true;
            this.bubbleBkgColor = bubbleBkgColor;
            this.bubbleBorderColor = bubbleBorderColor;
            this.showArrow = showArrow;
        }

        /**
         * Draw the text onto the position
         * 
         * @param g
         *            Graphics2D to draw the text
         */
        public void draw( Graphics2D g ) {

            if( showBubble )
                GUI.drawStringOnto( g, text, x, y, textColor, borderColor, bubbleBkgColor, bubbleBorderColor, showArrow );
            else
                GUI.drawStringOnto( g, text, x, y, textColor, borderColor );
        }

        /**
         * Returns the Y coordinate
         * 
         * @return Y coordinate
         */
        public int getY( ) {

            return y;
        }
    }

    public static void setGraphicConfig( int newGraphicConfig ) {

        graphicConfig = newGraphicConfig;
    }

    public abstract Frame getJFrame( );

    /**
     * @param type
     *            the gUIType to set
     */
    public static void setGUIType( int type ) {

        GUI.GUIType = type;
    }

    public boolean mouseReleasedinHud( MouseEvent e ) {

        return hud.mouseReleased( e );
    }

    public boolean mousePressedinHud( MouseEvent e ) {

        return hud.mousePressed( e );
    }

    public boolean mouseDraggedinHud( MouseEvent e ) {

        return hud.mouseDragged( e );
    }

    public void setTransition( int transitionTime, int transitionType, long elapsedTime ) {

        if( transitionTime > 0 && transitionType > 0 ) {
            this.transition = new Transition( transitionTime, transitionType );
        }

    }

    public boolean hasTransition( ) {

        return transition != null && !transition.hasFinished( 0 );
    }

    public void setShowsOffestArrows( boolean showsOffsetArrows, boolean moveOffsetRight, boolean moveOffsetLeft ) {

        this.showsOffsetArrows = showsOffsetArrows;
        this.moveOffsetRight = moveOffsetRight;
        this.moveOffsetLeft = moveOffsetLeft;
    }

    public void setLastMouseMove( MouseEvent e ) {
        hud.setLastMouseMove( e );
    }

    public void setDragElement( FunctionalElement dragElement ) {

    }

    
    /**
     * @return the elementsToDraw
     */
    public ArrayList<ElementImage> getElementsToDraw( ) {
    
        return elementsToDraw;
    }

    
    /**
     * @return the elementsToInteract
     */
    public List<FunctionalElement> getElementsToInteract( ) {
    
        return elementsToInteract;
    }

    public void loading( int percent ) {
        if (percent == 0) {
            
            // FIXME Chapucilla que huele a pipi
            //this.loadingImage =  new ImageIcon("gui/loading.jpg").getImage();
            this.loadingImage = MultimediaManager.getInstance( ).loadImage( "gui/loading.jpg", MultimediaManager.IMAGE_MENU );
            if (this.loadingImage == null ){
                this.loadingImage = MultimediaManager.getInstance( ).loadImageFromZip( "gui/loading.jpg", MultimediaManager.IMAGE_MENU );
            }
            this.loading = percent;
            loadingTimer = new Timer();
            loadingTask = new TimerTask() {
                private int cont = 20;
                private boolean contracting = false;
                
                @Override
                public void run( ) {
                    Graphics2D g = GUI.this.getGraphics( );
                    g.drawImage( loadingImage, 0, 0, null);
                    
                    g.setColor( new Color(250, 173, 6) );
                    g.fillRoundRect( 200, 300, loading * 4, 50, 10, 10 );
//                    g.setColor( Color.BLUE );
//                    g.fillArc( 350, 250, 50, 50, cont, 20 );
                    
                    g.setStroke( new BasicStroke(4.0f) );
                    g.setColor( new Color(90, 32, 2) );
                    g.drawRoundRect( 200, 300, 400, 50, 10, 10 );

                    g.setColor( new Color(247, 215, 105) );
                    g.fillOval( 400 - cont / 2, 100 - cont / 2, cont, cont );
                    
//                    g.setColor( Color.BLACK );
//                    g.drawOval( 350, 250, 50, 50 );

                    if (!contracting) {
                        cont += 1;
                        if (cont > 60)
                            contracting = true;
                    } else {
                        cont -= 1;
                        if (cont < 10)
                            contracting = false;
                    }
                    
                    GUI.this.endDraw( );
                }
            };
            loadingTimer.scheduleAtFixedRate( loadingTask, 20, 20 );
        }
        if (percent == 100) {
            loadingTimer.cancel( );
        }
        this.loading = percent;
        
        Graphics2D g = this.getGraphics( );
        g.drawImage( loadingImage, 0, 0, null);

        g.setColor( new Color(250, 173, 6) );
        g.fillRoundRect( 200, 300, loading * 4, 50, 10, 10 );
        
        g.setStroke( new BasicStroke(4.0f) );
        g.setColor( new Color(90, 32, 2) );
        g.drawRoundRect( 200, 300, 400, 50, 10, 10 );
        
        this.endDraw( );
    }

}
