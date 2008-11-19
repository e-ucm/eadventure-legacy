package es.eucm.eadventure.engine.core.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFrame;

import es.eucm.eadventure.common.data.adventure.DescriptorData;
import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.gui.hud.HUD;
import es.eucm.eadventure.engine.core.gui.hud.contextualhud.ContextualHUD;
import es.eucm.eadventure.engine.core.gui.hud.traditionalhud.TraditionalHUD;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;

/**
 * This is the main class related with the graphics in eAdventure, including the window
 */
public class GUI implements FocusListener {
    
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
    
    public static final String DEFAULT_CURSOR="default";

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
    private Frame gameFrame;
    
    /**
     * Used to display components over the frame when it is required. (BOOKS)
     */
    private JFrame dialog;
    
    /**
     * The HUE element
     */
    private HUD hud;

    /**
     * The GUI singleton class
     */
    private static GUI instance = null;

    /**
     * The default cursor
     */
    private Cursor defaultCursor;
    
    /**
     * Background image of the scene.
     */
    private SceneImage background;
    
    /**
     * Foreground image of the scene.
     */
    private SceneImage foreground;
    
    /**
     * List of elements to be painted.
     */
    private ArrayList<ElementImage> elementsToDraw;
    
    /**
     * List of texts to be painted.
     */
    private ArrayList<Text> textToDraw;

    
    private JDialog backgroundDialog;
    /**
     * Return the GUI instance. GUI is a singleton class.
     * @return GUI sigleton instance
     */
    public static GUI getInstance( ) {
        return instance;
    }
    
    /**
     * Create the singleton instance
     */
    public static void create(){
        instance = new GUI( );
    }
    
    /**
     * Destroy the singleton instance
     */
    public static void delete(){
        if (instance!=null){
            instance.backgroundDialog.setVisible( false );
            instance.backgroundDialog.dispose();
        }
        instance = null;
    }

    /**
     * Private constructor to create the unique instace of the class
     */
    private GUI( ) {
        gameFrame = new Frame( "eAdventure" );
        backgroundDialog = new JDialog(){
            public void paint (Graphics g){
                g.setColor( Color.BLACK );
                g.fillRect( 0, 0, getSize( ).width, getSize( ).height );
            }
        };
        backgroundDialog.setUndecorated( true );
        
        
        backgroundDialog.setSize( Toolkit.getDefaultToolkit( ).getScreenSize( ).width, Toolkit.getDefaultToolkit( ).getScreenSize( ).height );
        backgroundDialog.setModal( false );
        backgroundDialog.setBackground( Color.BLACK );
        backgroundDialog.setForeground( Color.BLACK );
        //backgroundDialog.setVisible( true );
        
        background = null;
        elementsToDraw = new ArrayList<ElementImage>();
        textToDraw = new ArrayList<Text>();
        
        /*
         * Old window closing behaviour. Testing what happens when this is disabled.
         * 
        if( !ResourceHandler.getInstance( ).isRestrictedMode( ) ) {
            //gameFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        } else {
            gameFrame.addWindowListener( new WindowManager( ) );
        }
        */
        
        gameFrame.setLayout( new BorderLayout() );
        gameFrame.setUndecorated( true );
        gameFrame.setIgnoreRepaint( true );
        gameFrame.setResizable( false );

        gameFrame.setFont( new Font( "Dialog", Font.PLAIN, 18 ) );
        gameFrame.setBackground( Color.black );
        gameFrame.setForeground( Color.white );
        gameFrame.setSize( new Dimension( WINDOW_WIDTH, WINDOW_HEIGHT ) );
        
        dialog = null;
    }

    public void setFrame( Frame newFrame ){
        this.gameFrame = newFrame;
    }
    
    /**
     * Init the GUI class and also get focus for the mainwindow
     */
    public void initGUI( int guiType, boolean customized ) {
        // Create the hud, depending on guiType
        if( guiType == DescriptorData.GUI_TRADITIONAL )
            hud = new TraditionalHUD( );
        else if( guiType == DescriptorData.GUI_CONTEXTUAL )
            hud = new ContextualHUD( );
        
        // Center window on screen
        Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
        gameFrame.setLocation( ( screenSize.width - WINDOW_WIDTH ) / 2, ( screenSize.height - WINDOW_HEIGHT ) / 2);
        gameFrame.setEnabled( true );
        gameFrame.setVisible( true );
        gameFrame.setFocusable( true );
        // Double buffered painting
        gameFrame.setAlwaysOnTop( true );
        gameFrame.createBufferStrategy( 2 );
        gameFrame.validate( );
        
        gameFrame.addFocusListener( this );
        gameFrame.requestFocusInWindow( );
        
        hud.init( );
        
        // Load the customized default cursor
        if( Game.getInstance().getGameDescriptor().getCursorPath( DEFAULT_CURSOR )!=null ){
            //System.out.println("PATH CURSOR = "+Game.getInstance( ).getGameDescriptor( ).getCursorPath( DEFAULT_CURSOR ) );
            defaultCursor = Toolkit.getDefaultToolkit( ).createCustomCursor( MultimediaManager.getInstance( ).loadImageFromZip( Game.getInstance( ).getGameDescriptor( ).getCursorPath( DEFAULT_CURSOR ), MultimediaManager.IMAGE_MENU ), new Point( 0, 0 ), "defaultCursor" );
        // Load the default default cursor
        }else 
            defaultCursor = Toolkit.getDefaultToolkit( ).createCustomCursor( MultimediaManager.getInstance( ).loadImage( "gui/cursors/default.png", MultimediaManager.IMAGE_MENU ), new Point( 0, 0 ), "defaultCursor" );
        gameFrame.setCursor( defaultCursor );
        
        dialog=null;
    }
    
    public JFrame showComponent ( Component component ){
        if (dialog == null){

            //Hide Frame
            gameFrame.setFocusable( false );
            gameFrame.setAlwaysOnTop( false );
            gameFrame.setVisible( false );
            gameFrame.setExtendedState( Frame.ICONIFIED );

            
            dialog = new JFrame("eAdventure");
            //dialog.setModalityType( JDialog.DEFAULT_MODALITY_TYPE );
            dialog.getContentPane( ).setLayout( new BorderLayout() );
            dialog.getContentPane( ).add( component, BorderLayout.CENTER );
            dialog.setUndecorated( true );
            dialog.setResizable( false );
            dialog.setSize( GUI.getInstance( ).getFrame( ).getSize( ) );
            Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
            dialog.setLocation( ( screenSize.width - GUI.WINDOW_WIDTH ) / 2, ( screenSize.height - GUI.WINDOW_HEIGHT ) / 2);
            dialog.setEnabled( true );
            dialog.setVisible( false );
            dialog.setFocusable( true );
            dialog.addMouseListener( Game.getInstance( ) );

            dialog.setVisible( true );
            dialog.requestFocusInWindow( );
            dialog.setExtendedState( JFrame.NORMAL );
            dialog.setAlwaysOnTop( true );
        }
        
        else {
            dialog.getContentPane( ).removeAll( );
            dialog.getContentPane( ).add( component, BorderLayout.CENTER );
            //System.out.println("COMPONENT CHANGED");
            //dialog.repaint( );
            dialog.getContentPane().invalidate( );
            dialog.doLayout( );
            dialog.repaint( );
            component.repaint( );
        }
     
        if (!dialog.isFocusOwner( )){
            dialog.requestFocus( );
            dialog.requestFocusInWindow( );
        }
        
        if (dialog.getExtendedState( ) != Frame.NORMAL ){
            dialog.setExtendedState( Frame.NORMAL );    
        }
        
        if (!dialog.isVisible( ))
            dialog.setVisible( true );
        
        return dialog;
    }
    
    public void restoreFrame (){
        //Hide Dialog (if not null)
        if (dialog!=null){
            dialog.setVisible( false );
            dialog.dispose( );
            dialog = null;
        }
        gameFrame.setFocusable( true );
        gameFrame.setAlwaysOnTop( true );
        gameFrame.setVisible( true );
        gameFrame.setExtendedState( Frame.NORMAL );
        
        if (!gameFrame.isFocusOwner( )){
            gameFrame.requestFocus( );
            gameFrame.requestFocusInWindow( );
        }
        
        if (gameFrame.getExtendedState( ) != Frame.NORMAL ){
            gameFrame.setExtendedState( Frame.NORMAL );    
        }
        
        if (!gameFrame.isVisible( ))
            gameFrame.setVisible( true );
            
    }
    
    /**
     * Returns the width of the playable area of the screen
     * @return Width of the playable area
     */
    public int getGameAreaWidth( ) {
        return hud.getGameAreaWidth( );
    }
    
    /**
     * Returns the height of the playable area of the screen
     * @return Height of the playable area
     */
    public int getGameAreaHeight( ) {
        return hud.getGameAreaHeight( );
    }
    
    /**
     * Returns the X point of the response block text
     * @return X point of the response block text
     */
    public int getResponseTextX( ) {
        return hud.getResponseTextX( );
    }
    
    /**
     * Returns the Y point of the response block text
     * @return Y point of the response block text
     */
    public int getResponseTextY( ) {
        return hud.getResponseTextY( );
    }
    
    /**
     * Returns the number of lines of the response text block
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
        if( g == null ) {
            //System.out.println( "Error: Graphics2D = null " );
        } else {
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
     * @return Frame the main window
     */
    public Frame getFrame( ) {
        return gameFrame;
    }

    /**
     * Gets the GraphicsConfiguration of the window
     * @return GraphicsConfiguration of the JFrame
     */
    public GraphicsConfiguration getGraphicsConfiguration( ) {
        return gameFrame.getGraphicsConfiguration( );
    }
   
    /**
     * Draw the text array with a border, given the lower-left coordinate if centeredX is false
     * or the lower-middle if it is true
     * @param g Graphics2D where make the painting
     * @param string String to be drawn
     * @param x Int coordinate of the left (or middle) of the string to be drawn
     * @param y Int coordinate of the bottom of the string to be drawn
     * @param centeredX boolean if the x is middle
     * @param textColor Color of the text
     * @param borderColor Color of the border of the text
     * @param border whether to paint a border on the text
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
            } else if( realX - width / 2 < 0 ) {
                realX = (int) ( width / 2 );
            }
            realX -= width / 2;
            //Check if the text don't go out of the window vertically
            //and if it do correct it so it's in the window
            if( realY > WINDOW_HEIGHT ) {
                realY = WINDOW_HEIGHT ;
            } else if( realY- height < 0 ) {
                realY = (int)height;
            }
        //if it's not centered
        } else {
            //Check if the text don't go out of the window horizontally
            //and if it do correct it so it's in the window
            if( realX + width > WINDOW_WIDTH ) {
                realX = (int) ( WINDOW_WIDTH - width );
            } else if( realX < 0 ) {
                realX = 0;
            }
            //Check if the text don't go out of the window vertically
            //and if it do correct it so it's in the window
            if( realY > WINDOW_HEIGHT ) {
                realY = WINDOW_HEIGHT;
            } else if( realY - height < 0 ) {
                realY = (int)height;
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
     * Draw the text array with a border, given the lower-middle position of the text
     * @param g Graphics2D where make the painting
     * @param strings String[] of Strings to be drawn
     * @param x Int coordinate of the middle of the string to be drawn
     * @param y Int coordinate of the bottom of the string to be drawn
     * @param textColor Color of the text
     * @param borderColor Color of the border of the text
     */
    public static void drawStringOnto( Graphics2D g, String[] strings, int x, int y, Color textColor, Color borderColor ) {
        //Calculate the total height of the block text
        FontMetrics fontMetrics = g.getFontMetrics( );
        int textBlockHeight = fontMetrics.getHeight( ) * strings.length - fontMetrics.getLeading( );
        
        // This is the y lower position of the first line
        int realY = y - textBlockHeight + fontMetrics.getAscent( );
        
        //Draw each line of the string array
        for( String line : strings ) {
            drawStringOnto( g, line, x, realY, true, textColor, borderColor, true );
            realY += fontMetrics.getHeight();
        }
    }
    
    /**
     * Draws the string specified centered (in X and Y) in the given position
     * @param g Graphics2D where make the painting
     * @param string String to be drawed
     * @param x Center X position of the text
     * @param y Center Y position of the text
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
        } else if( realX - width / 2 < 0 ) {
            realX = (int) ( width / 2 );
        }
        realX -= width / 2;
        
        //Check if the text don't go out of the window vertically
        //and if it do correct it so it's in the window
        if( realY + height / 2 > WINDOW_HEIGHT ) {
            realY = (int) ( WINDOW_HEIGHT - height / 2 );
        } else if( realY < 0 ) {
            realY = 0;
        }
        realY += height / 2;

        //Draw the string
        g.drawString( string, realX, realY );
    }
    
    /**
     * Draws the given block text centered in the given position
     * @param g Graphics2D where make the painting
     * @param strings Array of strings to be painted
     * @param x Centered X position of the text block
     * @param y Centered Y position of the text block
     */
    public static void drawString( Graphics2D g, String[] strings, int x, int y ) {
        //Calculate the total height of the block text
        FontMetrics fontMetrics = g.getFontMetrics( );
        int textBlockHeight = fontMetrics.getHeight( ) * strings.length-fontMetrics.getLeading( ) - fontMetrics.getDescent( );
        
        // This is the y center position of the first line
        int realY = y - textBlockHeight / 2 + fontMetrics.getAscent( ) / 2;
        
        //Draw each line of the string array
        for( String line : strings ) {
            drawString( g, line, x, realY );
            realY += fontMetrics.getHeight( );
        }
    }

    /**
     * Split a text in various lines using the font width and an max width for each line
     * @param text String that contains all the text to be used
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

            } else {
                line = currentLine;
                exit = true;
            }

            lines.add( line );
        } while( !exit );
        return lines.toArray( new String[ 1 ] );
    }

    /**
     * Creates a Color from the red, green and blue component
     * @param r Red int
     * @param g Green int
     * @param b Blue int
     * @return Color corresponding to R,G,B components
     */
    public Color getColor( int r, int g, int b ) {
        float[] hsbvals = Color.RGBtoHSB( r, g, b, null );
        return Color.getHSBColor( hsbvals[0], hsbvals[1], hsbvals[2] );
    }

    /**
     * Draws the scene buffer given a Graphics2D
     * @param g Graphics2D to be used by the scene buffer
     */
    public void drawScene( Graphics2D g ) {
        if(background != null){
            background.draw( g );
            background = null;
        }
        for(ElementImage element : elementsToDraw)
            element.draw( g );
        elementsToDraw.clear();
        
        if(foreground != null){
            foreground.draw( g );
            foreground = null;
        }
        
        for(Text text : textToDraw)
            text.draw( g );
        textToDraw.clear();
    }
    
    /**
     * Draws the HUD given a Graphics2D
     * @param g Graphics2D to be used by the HUD
     */
    public void drawHUD( Graphics2D g ) {             
        hud.draw( g );
    }

    /**
     * Set the current cursor to the new Cursor
     * @param cursor the new cursor
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
     * @param e Mouse event
     * @return boolean If the move is in the HUD
     */
    public boolean mouseClickedInHud( MouseEvent e ) {
        return hud.mouseClicked( e );
    }

    /**
     * There has been a mouse moved in the HUD
     * @param e Mouse event
     * @return boolean If the move is in the HUD
     */
    public boolean mouseMovedinHud( MouseEvent e ) {
        return hud.mouseMoved( e );
    }
    
    /**
     * There is a new action selected
     */
    public void newActionSelected(){
        hud.newActionSelected( );
    }
    
    /**
     * Toggle the HUD on or off
     * @param show If the Hud is shown or not
     */
    public void toggleHud(boolean show){
        hud.toggleHud( show );
    }
    
    /**
     * Set the background image
     * @param background Background image
     * @param offsetX Offset of the background
     */
    public void addBackgroundToDraw(Image background, int offsetX){
        this.background = new SceneImage(background, offsetX);
    }
    
    /**
     * Set the foreground image
     * @param foreground Background image
     * @param offsetX Offset of the background
     */
    public void addForegroundToDraw(Image foreground, int offsetX){
        this.foreground = new SceneImage(foreground, offsetX);
    }
    
    /**
     * Adds the image to the array image buffer sorted by its Y coordinate
     * @param image Image
     * @param x X coordinate
     * @param y Y coordinate
     * @param z Depth of the image
     */
    public void addElementToDraw( Image image, int x, int y, int depth ){
        boolean added = false;
        int i = 0;
        
        // Create the image to store it 
        ElementImage element = new ElementImage( image, x, y, depth );
        
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
     * Adds the string to the array text buffer sorted by its Y coordinate
     * @param string Array string
     * @param x X coordinate
     * @param y Y coordinate
     * @param textColor Color of the string
     * @param borderColor Color if the border of the string
     */
    public void addTextToDraw(String[] string, int x, int y, Color textColor, Color borderColor){
        boolean added = false;
        int i=0;
        Text text = new Text(string, x, y, textColor, borderColor);
        while(!added && i<textToDraw.size()){
            if(y <= textToDraw.get(i).getY() ){
                textToDraw.add(i, text);
                added = true;
            }
            i++;
        }
        if(!added)
            textToDraw.add(text);
    }
    
    /**
     * Background class that store the image of the background and a screen offset
     */
    private class SceneImage{
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
         * @param background Background image
         * @param offsetX Offset
         */
        public SceneImage(Image background, int offsetX){
            this.background = background;
            this.offsetX = offsetX;
        }
        
        /**
         * Draw the background with the offset
         * @param g Graphics2D to draw the background
         */
        public void draw(Graphics2D g){
            g.drawImage( background, 0, 0, GUI.WINDOW_WIDTH, GUI.WINDOW_HEIGHT, offsetX, 0, offsetX+GUI.WINDOW_WIDTH, GUI.WINDOW_HEIGHT, gameFrame );
        }
    }
    
    /**
     * Store a image with its position in the scene
     */
    private class ElementImage{
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
         * Depth of the image (to be painted).
         */
        private int depth;
        
        /**
         * Constructor of the class
         * @param image Image
         * @param x X coordinate
         * @param y Y coordinate
         * @param depth Depth to draw the image
         */
        public ElementImage( Image image, int x, int y, int depth ) {
            this.image = image;
            this.x = x;
            this.y = y;
            this.depth = depth;
        }
        
        /**
         * Draw the image in the position
         * @param g Graphics2D to draw the image
         */
        public void draw(Graphics2D g){
            g.drawImage( image, x, y, null );
        }
        
        /**
         * Returns the depth of the element.
         * @return Depth of the element
         */
        public int getDepth( ) {
            return depth;
        }
    }
    
    /**
     * Store the array string, its color and border and it's position to be draw onto.
     */
    private class Text{
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
        
        /**
         * Constructor of the class
         * @param text Array string
         * @param x X coordinate
         * @param y Y coordinate
         * @param textColor Color of the text
         * @param borderColor Color of the borde of the text
         */
        public Text(String[] text, int x, int y, Color textColor, Color borderColor){
            this.text = text;
            this.x = x;
            this.y = y;
            this.textColor = textColor;
            this.borderColor = borderColor;
        }
        
        /**
         * Draw the text onto the position
         * @param g Graphics2D to draw the text
         */
        public void draw(Graphics2D g){
            GUI.drawStringOnto(g, text, x, y, textColor, borderColor);
        }
        
        /**
         * Returns the Y coordinate
         * @return Y coordinate
         */
        public int getY(){
            return y;
        }
    }
 
}
