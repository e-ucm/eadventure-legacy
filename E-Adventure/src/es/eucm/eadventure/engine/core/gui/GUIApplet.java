package es.eucm.eadventure.engine.core.gui;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JLabel;

import es.eucm.eadventure.common.data.adventure.DescriptorData;
import es.eucm.eadventure.engine.EAdventureApplet;
import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.gui.GUI.ElementImage;
import es.eucm.eadventure.engine.core.gui.GUI.Text;
import es.eucm.eadventure.engine.core.gui.hud.contextualhud.ContextualHUD;
import es.eucm.eadventure.engine.core.gui.hud.traditionalhud.TraditionalHUD;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;

public class GUIApplet extends GUI {

	private static JApplet applet;
	
	private Component component;
	
	@Override
	public Frame getJFrame() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void create() {
		instance = new GUIApplet();
	}

	@Override
	public void initGUI(int guiType, boolean customized) {
	    //JLabel label = new JLabel(
        //        "You are successfully running a Swing applet!");
	    //label.setHorizontalAlignment(JLabel.CENTER);
	    //label.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.black));
	    
		gameFrame = new Canvas();
	    background = null;
        elementsToDraw = new ArrayList<ElementImage>();
        textToDraw = new ArrayList<Text>();

		
        gameFrame.setIgnoreRepaint( true );
        gameFrame.setFont( new Font( "Dialog", Font.PLAIN, 18 ) );
        gameFrame.setBackground( Color.black );
        gameFrame.setForeground( Color.white );
        gameFrame.setSize( new Dimension( WINDOW_WIDTH, WINDOW_HEIGHT ) );

        applet.getContentPane().add(gameFrame, BorderLayout.CENTER);

        gameFrame.createBufferStrategy( 2 );
        
        
        if( guiType == DescriptorData.GUI_TRADITIONAL )
            hud = new TraditionalHUD( );
        else if( guiType == DescriptorData.GUI_CONTEXTUAL )
            hud = new ContextualHUD( );
  

        gameFrame.setEnabled( true );
        gameFrame.setVisible( true );
        gameFrame.setFocusable( true );

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

	}
	
	public static void delete() {
		instance = null;
	};

	@Override
	public void restoreFrame() {
    	if (component != null) {
    		applet.remove(component);
    	}
    	component = null;
    	applet.setIgnoreRepaint(true);
    	applet.repaint();
    	gameFrame.setVisible(true);
    	applet.validate();
	}

	@Override
	public JFrame showComponent(Component component) {
    	gameFrame.setVisible(false);
    	if (this.component != null)
    		applet.remove(this.component);
    	this.component = component;
    	component.setBackground(Color.BLACK);
    	component.setForeground(Color.BLACK);
    	applet.add(component);
    	applet.validate();
       	component.repaint();
        //System.out.println("IS DISPLAYABLE: " + component.isDisplayable() + "\n");

    	return null;
	}

	public static void setApplet(JApplet adventureApplet) {
		applet = adventureApplet;
	}
	
}
