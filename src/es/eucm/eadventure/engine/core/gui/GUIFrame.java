/**
 * <e-Adventure> is an <e-UCM> research project. <e-UCM>, Department of Software
 * Engineering and Artificial Intelligence. Faculty of Informatics, Complutense
 * University of Madrid (Spain).
 * 
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J. (alphabetical order) *
 * @author López Mañas, E., Pérez Padilla, F., Sollet, E., Torijano, B. (former
 *         developers by alphabetical order)
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
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

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import es.eucm.eadventure.common.data.adventure.DescriptorData;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.engine.core.control.DebugLog;
import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.gui.hud.contextualhud.ContextualHUD;
import es.eucm.eadventure.engine.core.gui.hud.traditionalhud.TraditionalHUD;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;

public class GUIFrame extends GUI implements FocusListener {

    /*
     * The frame to keep the screen black behind the game
     */
    private JFrame bkgFrame;

    private static DisplayMode originalDisplayMode;

    private Component component = null;

    /**
     * Create the singleton instance
     */
    public static void create( ) {

        instance = new GUIFrame( );
    }

    /**
     * Destroy the singleton instance
     */
    public static void delete( ) {

        if( originalDisplayMode != null && instance != null && ( (GUIFrame) instance ).bkgFrame != null ) {
            GraphicsDevice gm;
            GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment( );
            gm = environment.getDefaultScreenDevice( );
            gm.setFullScreenWindow( ( (GUIFrame) instance ).bkgFrame );
            gm.setDisplayMode( originalDisplayMode );
            originalDisplayMode = null;
        }
        if( instance != null && ( (GUIFrame) instance ).bkgFrame != null ) {
            ( (GUIFrame) instance ).bkgFrame.setVisible( false );
            ( (GUIFrame) instance ).bkgFrame.dispose( );
        }
        instance = null;
    }

    //private JPanel panel;

    /**
     * Private constructor to create the unique instace of the class
     */
    protected GUIFrame( ) {

        bkgFrame = new JFrame( "eadventure" ) {

            private static final long serialVersionUID = 3648656167576771790L;

            @Override
            public void paint( Graphics g ) {

                g.setColor( Color.BLACK );
                g.fillRect( 0, 0, getSize( ).width, getSize( ).height );
                if( GUIFrame.this.component != null )
                    GUIFrame.this.component.repaint( );
            }
        };

        // Create the list of icons of the window
        List<Image> icons = new ArrayList<Image>( );

        icons.add( new ImageIcon( "gui/Icono-Motor-16x16.png" ).getImage( ) );
        icons.add( new ImageIcon( "gui/Icono-Motor-32x32.png" ).getImage( ) );
        icons.add( new ImageIcon( "gui/Icono-Motor-64x64.png" ).getImage( ) );
        icons.add( new ImageIcon( "gui/Icono-Motor-128x128.png" ).getImage( ) );
        bkgFrame.setIconImages( icons );

        bkgFrame.setUndecorated( true );

        Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
        if( graphicConfig == DescriptorData.GRAPHICS_BLACKBKG && !Game.getInstance( ).isDebug( ) ) {
            bkgFrame.setSize( screenSize.width, screenSize.height );
            bkgFrame.setLocation( 0, 0 );
        }
        else {
            if( !Game.getInstance( ).isDebug( ) ) {
                bkgFrame.setSize( WINDOW_WIDTH, WINDOW_HEIGHT );
                bkgFrame.setLocation( ( screenSize.width - WINDOW_WIDTH ) / 2, ( screenSize.height - WINDOW_HEIGHT ) / 2 );
            }
            else {
                bkgFrame.setSize( screenSize.width, screenSize.height );
                bkgFrame.setLocation( 0, ( System.getProperty( "os.name" ).contains( "Mac" ) ? 15 : 0 ) );
            }
        }

        bkgFrame.setBackground( Color.BLACK );
        bkgFrame.setForeground( Color.BLACK );
        bkgFrame.setLayout( new GUILayout( ) );//new BorderLayout());

        gameFrame = new Canvas( );
        background = null;
        elementsToDraw = new ArrayList<ElementImage>( );
        textToDraw = new ArrayList<Text>( );

        gameFrame.setIgnoreRepaint( true );
        gameFrame.setFont( new Font( "Dialog", Font.PLAIN, 18 ) );
        gameFrame.setBackground( Color.black );
        gameFrame.setForeground( Color.white );
        gameFrame.setSize( new Dimension( WINDOW_WIDTH, WINDOW_HEIGHT ) );
        if( !Game.getInstance( ).isDebug( ) )
            gameFrame.setLocation( ( screenSize.width - WINDOW_WIDTH ) / 2 - (int) bkgFrame.getLocation( ).getX( ), ( screenSize.height - WINDOW_HEIGHT ) / 2 - (int) bkgFrame.getLocation( ).getY( ) );
        else
            gameFrame.setLocation( (int) bkgFrame.getLocation( ).getX( ), (int) bkgFrame.getLocation( ).getY( ) );

        bkgFrame.setVisible( true );

        bkgFrame.setIgnoreRepaint( true );

        bkgFrame.add( gameFrame );//, BorderLayout.CENTER);

        String os = System.getProperty( "os.name" );
        if (os.contains( "Mac" ))
            System.setProperty( "apple.awt.rendering", "speed" );
        
        if( os.contains( "Windows" ) && graphicConfig == DescriptorData.GRAPHICS_FULLSCREEN && !Game.getInstance( ).isDebug( ) ) {
            GraphicsEnvironment environment;
            GraphicsDevice gm = null;
            boolean changed = false;
            try {
                // Set fullscreen... Runs into compatibility issues in non-Windows systems
                environment = GraphicsEnvironment.getLocalGraphicsEnvironment( );
                gm = environment.getDefaultScreenDevice( );
                originalDisplayMode = gm.getDisplayMode( );
                DisplayMode dm = new DisplayMode( WINDOW_WIDTH, WINDOW_HEIGHT, 32, DisplayMode.REFRESH_RATE_UNKNOWN );
                DisplayMode[] dmodes = gm.getDisplayModes( );
                for( int i = 0; i < dmodes.length && !changed; i++ ) {
                    if( dmodes[i].getBitDepth( ) == dm.getBitDepth( ) && dmodes[i].getHeight( ) == dm.getHeight( ) && dmodes[i].getWidth( ) == dm.getWidth( ) ) {
                        gm.setFullScreenWindow( bkgFrame );
                        gm.setDisplayMode( dm );
                        changed = true;
                    }
                }
            }
            catch( Exception e ) {
                if( gm != null && originalDisplayMode != null ) {
                    gm.setDisplayMode( originalDisplayMode );
                    originalDisplayMode = null;
                }
            }
            if( !changed ) {
                originalDisplayMode = null;
                JOptionPane.showMessageDialog( bkgFrame, TextConstants.getText( "GUI.NoFullscreenTitle" ), TextConstants.getText( "GUI.NoFullscreenContent" ), JOptionPane.WARNING_MESSAGE );
            }
        }
        else if( !os.contains( "Windows" ) && graphicConfig == DescriptorData.GRAPHICS_FULLSCREEN ) {
            JOptionPane.showMessageDialog( bkgFrame, TextConstants.getText( "GUI.NoFullscreenTitle" ), TextConstants.getText( "GUI.NoFullscreenContent" ), JOptionPane.WARNING_MESSAGE );
        }

    }

    /**
     * Init the GUI class and also get focus for the mainwindow
     */
    @Override
    public void initGUI( int guiType, boolean customized ) {

        // Create the hud, depending on guiType
        if( guiType == DescriptorData.GUI_TRADITIONAL )
            hud = new TraditionalHUD( );
        else if( guiType == DescriptorData.GUI_CONTEXTUAL )
            hud = new ContextualHUD( );

        gameFrame.setEnabled( true );
        gameFrame.setVisible( true );
        gameFrame.setFocusable( true );
        // Double buffered painting
        // Set always on top only in full-screen mode
        bkgFrame.setAlwaysOnTop( graphicConfig != DescriptorData.GRAPHICS_WINDOWED );
        gameFrame.createBufferStrategy( 2 );
        gameFrame.validate( );

        gameFrame.addFocusListener( this );
        gameFrame.requestFocusInWindow( );

        hud.init( );

        if( Game.getInstance( ).getGameDescriptor( ).getCursorPath( DEFAULT_CURSOR ) != null ) {
            try {
                defaultCursor = Toolkit.getDefaultToolkit( ).createCustomCursor( MultimediaManager.getInstance( ).loadImageFromZip( Game.getInstance( ).getGameDescriptor( ).getCursorPath( DEFAULT_CURSOR ), MultimediaManager.IMAGE_MENU ), new Point( 0, 0 ), "defaultCursor" );
            }
            catch( Exception e ) {
                DebugLog.general( "Cound't find default cursor " );
                defaultCursor = Toolkit.getDefaultToolkit( ).createCustomCursor( MultimediaManager.getInstance( ).loadImage( "gui/cursors/nocursor.png", MultimediaManager.IMAGE_MENU ), new Point( 0, 0 ), "defaultCursor" );
            }
        }
        else {
            try {
                defaultCursor = Toolkit.getDefaultToolkit( ).createCustomCursor( MultimediaManager.getInstance( ).loadImage( "gui/cursors/default.png", MultimediaManager.IMAGE_MENU ), new Point( 0, 0 ), "defaultCursor" );
            }
            catch( Exception e ) {
                DebugLog.general( "Cound't find custom default cursor " );
                defaultCursor = Toolkit.getDefaultToolkit( ).createCustomCursor( MultimediaManager.getInstance( ).loadImage( "gui/cursors/nocursor.png", MultimediaManager.IMAGE_MENU ), new Point( 0, 0 ), "defaultCursor" );
            }
        }
        gameFrame.setCursor( defaultCursor );
    }

    /**
     * Displays a Swing or AWT component in the game window.
     * <p>
     * To remove the component, use RestoreFrame method.
     * 
     * @param component
     * @return
     */
    @Override
    public JFrame showComponent( Component component ) {

        gameFrame.setVisible( false );
        bkgFrame.setIgnoreRepaint( true );
        if( this.component != null )
            bkgFrame.remove( this.component );
        this.component = component;
        component.setBackground( Color.BLACK );
        component.setForeground( Color.BLACK );
        bkgFrame.add( component );
        bkgFrame.validate( );
        component.repaint( );
        //System.out.println("IS DISPLAYABLE: " + component.isDisplayable() + "\n");

        return bkgFrame;
    }

    /**
     * Restores the frame to its original state after displaying a Swing or AWT
     * component.
     * 
     */
    @Override
    public void restoreFrame( ) {

        if( component != null ) {
            bkgFrame.remove( component );
        }
        component = null;

        if( Game.getInstance( ).isDebug( ) ) {
            //bkgFrame.setIgnoreRepaint(false);
            //bkgFrame.repaint();
            gameFrame.setVisible( true );
            bkgFrame.validate( );
            Game.getInstance( ).repaintDebug( );
        }
        else {
            bkgFrame.setIgnoreRepaint( true );
            bkgFrame.repaint( );
            gameFrame.setVisible( true );
            bkgFrame.validate( );
        }
    }

    @Override
    public Frame getJFrame( ) {

        return bkgFrame;
    }

    private class GUILayout implements LayoutManager {

        public void addLayoutComponent( String arg0, Component arg1 ) {

        }

        public void layoutContainer( Container container ) {

            Component[] components = container.getComponents( );
            for( int i = 0; i < components.length; i++ ) {
                Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
                if( bkgFrame != null ) {
                    int posX = ( screenSize.width - GUI.WINDOW_WIDTH ) / 2 - (int) bkgFrame.getLocation( ).getX( );
                    int posY = ( screenSize.height - GUI.WINDOW_HEIGHT ) / 2 - (int) bkgFrame.getLocation( ).getY( );
                    if( Game.getInstance( ).isDebug( ) ) {
                        posX = ( screenSize.width - GUI.WINDOW_WIDTH );
                        posY = ( System.getProperty( "os.name" ).contains( "Mac" ) ? 15 : 0 );
                    }

                    if( components[i] instanceof DebugLogPanel ) {
                        components[i].setBounds( 0, GUI.WINDOW_HEIGHT + ( System.getProperty( "os.name" ).contains( "Mac" ) ? 15 : 0 ), screenSize.width, screenSize.height - GUI.WINDOW_HEIGHT - ( System.getProperty( "os.name" ).contains( "Mac" ) ? 15 : 0 ) );
                    }
                    else if( components[i] instanceof DebugValuesPanel ) {
                        components[i].setBounds( 0, ( System.getProperty( "os.name" ).contains( "Mac" ) ? 15 : 0 ), screenSize.width - GUI.WINDOW_WIDTH, GUI.WINDOW_HEIGHT );
                    }
                    else
                        components[i].setBounds( posX, posY, GUI.WINDOW_WIDTH, GUI.WINDOW_HEIGHT );
                }
                else {
                    components[i].setLocation( 0, 0 );
                    components[i].setSize( GUI.WINDOW_WIDTH, GUI.WINDOW_HEIGHT );
                }
            }
        }

        public Dimension minimumLayoutSize( Container arg0 ) {

            return arg0.getSize( );
        }

        public Dimension preferredLayoutSize( Container arg0 ) {

            return arg0.getSize( );
        }

        public void removeLayoutComponent( Component arg0 ) {

        }
    }
}
