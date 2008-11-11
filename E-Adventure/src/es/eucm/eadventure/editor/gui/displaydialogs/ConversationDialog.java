package es.eucm.eadventure.editor.gui.displaydialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JPanel;

import es.eucm.eadventure.common.data.chapterdata.conversation.node.ConversationNode;
import es.eucm.eadventure.common.data.chapterdata.conversation.node.ConversationNodeView;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.conversation.ConversationDataControl;
import es.eucm.eadventure.editor.gui.TextConstants;
import es.eucm.eadventure.editor.gui.auxiliar.clock.Clock;
import es.eucm.eadventure.editor.gui.auxiliar.clock.ClockListener;

public class ConversationDialog extends JDialog implements ClockListener {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constant for no mouse button clicked.
	 */
	private static final int BUTTON_CLICKED_NONE = 0;

	/**
	 * Constant for left mouse button clicked.
	 */
	private static final int BUTTON_CLICKED_LEFT = 1;

	/**
	 * Constant for right mouse button clicked.
	 */
	private static final int BUTTON_CLICKED_RIGHT = 2;

	/**
	 * Constant for idle character.
	 */
	private static final int CHARACTER_IDLE = 0;

	/**
	 * Constant for talking character.
	 */
	private static final int CHARACTER_TALKING = 1;

	/**
	 * Time for each line to be displayed, in miliseconds.
	 */
	private static final int TIME_TALKING = 3000;

	/**
	 * Maximum number of option lines to show at once.
	 */
	private static final int MAX_OPTION_NUMBER_LINES = 7;

	/**
	 * Height of the text.
	 */
	private final int TEXT_HEIGHT;

	/**
	 * Max width in the text to display.
	 */
	private static final int MAX_WIDTH_IN_TEXT = 200;

	/**
	 * X coordinates to place the characters.
	 */
	private static final int[] POSITION_CHARACTER_X = { 150, 400 };

	/**
	 * Y coordinates to place the characters.
	 */
	private static final int[] POSITION_CHARACTER_Y = { 120, 143 };

	/**
	 * X coordinate to place the response text lines.
	 */
	private static final int RESPONSE_TEXT_X = 10;

	/**
	 * Y coordinate to place the response text lines.
	 */
	private static final int RESPONSE_TEXT_Y = 275;

	/**
	 * Color of the normal response text
	 */
	private static final Color RESPONSE_TEXT_NORMAL = Color.YELLOW;

	/**
	 * Color of the highlighted response text
	 */
	private static final Color RESPONSE_TEXT_HIGHLIGHTED = Color.RED;

	/**
	 * Color of the border of the response text
	 */
	private static final Color RESPONSE_TEXT_BORDER = Color.BLACK;

	/**
	 * Stores the last mouse button pressed.
	 */
	private int mouseClickedButton = BUTTON_CLICKED_NONE;

	/**
	 * Clock for the animations.
	 */
	private Clock animationClock;

	/**
	 * Time elapsed since the current text line started.
	 */
	private int lineTimeElapsed;

	/**
	 * Current node being processed.
	 */
	private ConversationNodeView currentNode;

	/**
	 * Current line being displayed.
	 */
	private int currentLine;

	/**
	 * Current text being displayed.
	 */
	private String[] currentText;

	/**
	 * Index of the character talking; -1 is no one is talking.
	 */
	private int characterTalking;

	/**
	 * Index of the first option displayed.
	 */
	private int firstOptionDisplayed;

	/**
	 * Index of the highlighted option.
	 */
	private int optionHighlighted;

	/**
	 * Font to display the text.
	 */
	private Font textFont;

	/**
	 * Array of frames for each character, and animation.
	 */
	private Image[][][] characterFrames;

	/**
	 * Array of integer, containing the current frame of the animations of each character.
	 */
	private int[] characterCurrentFrame;

	/**
	 * Constructor.
	 * 
	 * @param conversationDataControl
	 *            Conversation controller
	 * @param rootNode
	 *            Root node from which the conversation must be played
	 */
	public ConversationDialog( ConversationDataControl conversationDataControl, ConversationNodeView rootNode ) {

		super( Controller.getInstance( ).peekWindow( ), TextConstants.getText( "ConversationDialog.Title", conversationDataControl.getId( ) ) );

		// Load the animations for the two characters
		characterFrames = new Image[2][2][];
		characterCurrentFrame = new int[2];
		characterCurrentFrame[0] = 0;
		characterFrames[0][CHARACTER_IDLE] = loadAnimation( "img/animations/npc1_standing" );
		characterFrames[0][CHARACTER_TALKING] = loadAnimation( "img/animations/npc1_talking" );
		characterCurrentFrame[1] = 0;
		characterFrames[1][CHARACTER_IDLE] = loadAnimation( "img/animations/npc2_standing" );
		characterFrames[1][CHARACTER_TALKING] = loadAnimation( "img/animations/npc2_talking" );

		// Add the listeners
		addMouseListener( new GeneralMouseListener( ) );
		addMouseMotionListener( new GeneralMouseListener( ) );
		addWindowListener( new WindowClosingListener( ) );

		// Place the display panel
		setLayout( new BorderLayout( ) );
		add( new DisplayPanel( ), BorderLayout.CENTER );

		// Set the first node and the first line
		characterTalking = -1;
		currentNode = rootNode;
		currentLine = 0;
		firstOptionDisplayed = 0;
		optionHighlighted = -1;

		// Set the dialog and show it
		setSize( new Dimension( 650, 450 ) );
		setResizable( false );
		Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
		setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
		setVisible( true );

		// Create the font and take the height
		textFont = new Font( "Arial", Font.BOLD, 20 );
		TEXT_HEIGHT = getGraphics( ).getFontMetrics( textFont ).getHeight( );

		// Create and start the clock
		lineTimeElapsed = 0;
		animationClock = new Clock( this );
		animationClock.start( );

		// Hide the dialog, set it to modal, and show it again
		setVisible( false );
		setModal( true );
		setVisible( true );
	}

	/**
	 * Skips one line of the dialog. Changes to the next line if there are more, or to the next conversation node if the
	 * current one has ended.
	 */
	private void playNextLine( ) {
		// If some character is talking, stop it
		if( characterTalking != -1 ) {
			characterCurrentFrame[characterTalking] = 0;
			characterTalking = -1;
		}

		// If there are more lines in the node
		if( currentLine < currentNode.getLineCount( ) ) {
			// Take the character to speak
			int characterIndex = currentNode.isPlayerLine( currentLine ) ? 0 : 1;
			characterCurrentFrame[characterIndex] = 0;
			currentText = splitText( currentNode.getLineText( currentLine ) );
			characterTalking = characterIndex;

			// Increase the current line and reset the time elapsed
			lineTimeElapsed = 0;
			currentLine++;
		}

		// If there aren't more lines
		else {
			// If the node is terminal, close the dialog
			if( currentNode.isTerminal( ) ) {
				setVisible( false );
				dispose( );
			}

			// If it is not terminal, go to the next node
			else {
				currentNode = currentNode.getChildView( 0 );
				firstOptionDisplayed = 0;
				currentLine = 0;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.eucm.eadventure.editor.gui.auxiliar.clock.ClockListener#update(long)
	 */
	public void update( long time ) {
		// Increase the elapsed time talking
		lineTimeElapsed += time;

		// Increase the frames of the animations
		for( int i = 0; i < 2; i++ ) {
			int charState = characterTalking == i ? CHARACTER_TALKING : CHARACTER_IDLE;
			characterCurrentFrame[i] += time / 100;
			characterCurrentFrame[i] %= characterFrames[i][charState].length;
		}

		// Repaint the panel
		repaint( );

		// If the current node is a dialogue node
		if( currentNode.getType( ) == ConversationNode.DIALOGUE ) {
			// If no button was pressed, check if the the next line must be played
			if( mouseClickedButton == BUTTON_CLICKED_NONE ) {
				if( characterTalking == -1 || ( characterTalking != -1 && lineTimeElapsed > TIME_TALKING ) ) {
					playNextLine( );
				}
			}

			// If the left button was clicked, skip to the next line
			else if( mouseClickedButton == BUTTON_CLICKED_LEFT ) {
				playNextLine( );
				mouseClickedButton = BUTTON_CLICKED_NONE;
			}

			// If the right button was clicked, skip to the next node
			else if( mouseClickedButton == BUTTON_CLICKED_RIGHT ) {
				currentLine = currentNode.getLineCount( );
				playNextLine( );
				mouseClickedButton = BUTTON_CLICKED_NONE;
			}
		}
	}

	/**
	 * Loads the given animation, returning an array of images.
	 * 
	 * @param animationPath
	 *            Path of the animation, without suffix
	 * @return Array of images, containing the animation frames
	 */
	private Image[] loadAnimation( String animationPath ) {
		// Create a list of images
		List<Image> framesList = new ArrayList<Image>( );

		// While the last image has not been read
		boolean end = false;
		for( int i = 1; i < 100 && !end; i++ ) {

			// If the file exists, store the image
			File file = new File( animationPath + String.format( "_%02d.png", i ) );
			if( file.exists( ) )
				framesList.add( new ImageIcon( animationPath + String.format( "_%02d.png", i ) ).getImage( ) );

			// If it doesn't exist, exit the bucle
			else
				end = true;
		}

		return framesList.toArray( new Image[] {} );
	}

	/**
	 * Splits a given text in an array of strings, so it fits the screen.
	 * 
	 * @param text
	 *            Original line text
	 * @return Array of resulting strings
	 */
	private String[] splitText( String text ) {

		// Create an array of strings, and get the font metrics
		ArrayList<String> lines = new ArrayList<String>( );
		FontMetrics fontMetrics = getGraphics( ).getFontMetrics( );

		boolean exit = false;
		while( !exit ) {
			int width = fontMetrics.stringWidth( text );

			if( width > MAX_WIDTH_IN_TEXT ) {
				int lineNumber = (int) Math.ceil( (double) width / (double) MAX_WIDTH_IN_TEXT );
				int index = text.lastIndexOf( ' ', text.length( ) / lineNumber );

				if( index == -1 )
					index = text.indexOf( ' ' );

				if( index == -1 ) {
					index = text.length( );
					exit = true;
				}

				lines.add( text.substring( 0, index ) );
				text = text.substring( index ).trim( );

			} else {
				lines.add( text );
				exit = true;
			}
		}

		return lines.toArray( new String[] {} );
	}

	/**
	 * Draws an array of strings, centered in the X axis in the given position.
	 * 
	 * @param g
	 *            Graphics surface to draw on
	 * @param strings
	 *            Array of strings to be drawn
	 * @param x
	 *            Centered X position of the text block
	 * @param y
	 *            Base Y position of the text block
	 */
	private void drawStringsOnto( Graphics g, String[] strings, int x, int y ) {
		// Calculate the base of the text block
		int realY = y - (int) TEXT_HEIGHT * ( strings.length - 1 );

		// Draw each string
		for( int i = 0; i < strings.length; i++ ) {
			drawStringOnto( g, strings[i], x, realY, true, Color.WHITE, Color.BLACK );
			realY += TEXT_HEIGHT;
		}
	}

	/**
	 * Draws a string, placed in the given position with the given front and border colors.
	 * 
	 * @param g
	 *            Graphics surface to draw on
	 * @param string
	 *            String to be drawn
	 * @param x
	 *            X position for the string
	 * @param y
	 *            Y position for the string
	 * @param centered
	 *            True if the string must be centered in the X axis
	 * @param frontColor
	 *            Front color of the text
	 * @param borderColor
	 *            Border color of the text
	 */
	private void drawStringOnto( Graphics g, String string, int x, int y, boolean centered, Color frontColor, Color borderColor ) {
		// If the text must be centered, calculate the position
		if( centered )
			x -= g.getFontMetrics( ).stringWidth( string ) / 2;

		// Draw the border
		g.setColor( borderColor );
		g.drawString( string, x - 1, y - 1 );
		g.drawString( string, x - 1, y + 1 );
		g.drawString( string, x + 1, y - 1 );
		g.drawString( string, x + 1, y + 1 );

		// Draw the front
		g.setColor( frontColor );
		g.drawString( string, x, y );
	}

	/**
	 * Panel used to display the images. If the dialog is used for display, the image usually presents some flickering.
	 */
	private class DisplayPanel extends JPanel {

		/**
		 * Required
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void paint( Graphics g ) {
			super.paint( g );

			// Set the font and take the text height
			g.setFont( textFont );

			// Draw the characters
			for( int i = 0; i < 2; i++ ) {
				int charState = characterTalking == i ? CHARACTER_TALKING : CHARACTER_IDLE;
				g.drawImage( characterFrames[i][charState][characterCurrentFrame[i]], POSITION_CHARACTER_X[i], POSITION_CHARACTER_Y[i], null );
			}

			// If some character is talking, draw the lines
			if( characterTalking != -1 )
				drawStringsOnto( g, currentText, POSITION_CHARACTER_X[characterTalking] + characterFrames[characterTalking][CHARACTER_TALKING][characterCurrentFrame[characterTalking]].getWidth( null ) / 2, POSITION_CHARACTER_Y[characterTalking] - 10 );

			// Draw the options, if it is an option node
			if( currentNode.getType( ) == ConversationNode.OPTION ) {

				// If we can paint all the lines in screen, do it
				if( currentNode.getLineCount( ) <= MAX_OPTION_NUMBER_LINES ) {
					for( int i = 0; i < currentNode.getLineCount( ); i++ ) {
						Color frontColor = ( i == optionHighlighted ? RESPONSE_TEXT_HIGHLIGHTED : RESPONSE_TEXT_NORMAL );
						drawStringOnto( g, ( i + 1 ) + ".- " + currentNode.getLineText( i ), RESPONSE_TEXT_X, RESPONSE_TEXT_Y + ( TEXT_HEIGHT * i ), false, frontColor, RESPONSE_TEXT_BORDER );
					}
				}

				// If the lines don't fit in the screen, decompose them
				else {
					int indexLastLine = Math.min( firstOptionDisplayed + MAX_OPTION_NUMBER_LINES - 1, currentNode.getLineCount( ) );
					int i;

					for( i = firstOptionDisplayed; i < indexLastLine; i++ ) {
						Color frontColor = ( ( i - firstOptionDisplayed ) == optionHighlighted ? RESPONSE_TEXT_HIGHLIGHTED : RESPONSE_TEXT_NORMAL );
						drawStringOnto( g, ( i + 1 ) + ".- " + currentNode.getLineText( i ), RESPONSE_TEXT_X, RESPONSE_TEXT_Y + ( i - firstOptionDisplayed ) * TEXT_HEIGHT, false, frontColor, RESPONSE_TEXT_BORDER );
					}

					Color frontColor = ( ( i - firstOptionDisplayed ) == optionHighlighted ? RESPONSE_TEXT_HIGHLIGHTED : RESPONSE_TEXT_NORMAL );
					drawStringOnto( g, "More...", RESPONSE_TEXT_X, RESPONSE_TEXT_Y + ( i - firstOptionDisplayed ) * TEXT_HEIGHT, false, frontColor, RESPONSE_TEXT_BORDER );
				}
			}
		}
	}

	/**
	 * Listener for closing the window. Stops the clock.
	 */
	private class WindowClosingListener extends WindowAdapter {

		@Override
		public void windowClosing( WindowEvent e ) {
			// Stop the clock
			try {
				animationClock.stopClock( );
				animationClock.join( );
				animationClock = null;
			} catch( InterruptedException e1 ) {
				e1.printStackTrace( );
			}
		}
	}

	/**
	 * Listener for the mouse. Processes the mouse clicks and movements (for option selection).
	 */
	private class GeneralMouseListener implements MouseListener, MouseMotionListener {
		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
		 */
		public void mouseClicked( MouseEvent e ) {
			// If it is a dialogue node, store the event
			if( currentNode.getType( ) == ConversationNode.DIALOGUE ) {
				if( e.getButton( ) == MouseEvent.BUTTON1 )
					mouseClickedButton = BUTTON_CLICKED_LEFT;

				else if( e.getButton( ) == MouseEvent.BUTTON3 )
					mouseClickedButton = BUTTON_CLICKED_RIGHT;
			}

			// If it is an option node, check if one option has been selected
			else if( RESPONSE_TEXT_Y <= e.getY( ) ) {
				int optionSelected = ( e.getY( ) - RESPONSE_TEXT_Y ) / TEXT_HEIGHT;

				// If all the lines are in the screen, select normally
				if( currentNode.getLineCount( ) <= MAX_OPTION_NUMBER_LINES ) {
					if( optionSelected < currentNode.getLineCount( ) ) {
						characterCurrentFrame[0] = 0;
						currentText = splitText( currentNode.getLineText( optionSelected ) );
						characterTalking = 0;
						lineTimeElapsed = 0;

						currentNode = currentNode.getChildView( optionSelected );
					}
				}

				// If there are more lines
				else {
					// Take the option selected and the last line
					optionSelected += firstOptionDisplayed;
					int indexLastLine = Math.min( firstOptionDisplayed + MAX_OPTION_NUMBER_LINES - 1, currentNode.getLineCount( ) );

					// If the last option was selected, increase the first option displayed
					if( optionSelected == indexLastLine ) {
						firstOptionDisplayed += MAX_OPTION_NUMBER_LINES - 1;
						if( firstOptionDisplayed >= currentNode.getLineCount( ) )
							firstOptionDisplayed = 0;
					}

					// If some other option was selected, show it
					else if( optionSelected < currentNode.getLineCount( ) ) {
						characterCurrentFrame[0] = 0;
						currentText = splitText( currentNode.getLineText( optionSelected ) );
						characterTalking = 0;
						lineTimeElapsed = 0;

						currentNode = currentNode.getChildView( optionSelected );
					}
				}
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.MouseAdapter#mouseMoved(java.awt.event.MouseEvent)
		 */
		public void mouseMoved( MouseEvent e ) {
			if( RESPONSE_TEXT_Y <= e.getY( ) )
				optionHighlighted = ( e.getY( ) - RESPONSE_TEXT_Y ) / TEXT_HEIGHT;
			else
				optionHighlighted = -1;
		}

		// Not implemented
		public void mouseDragged( MouseEvent e ) {}

		public void mouseEntered( MouseEvent e ) {}

		public void mouseExited( MouseEvent e ) {}

		public void mousePressed( MouseEvent e ) {}

		public void mouseReleased( MouseEvent e ) {}
	}
}
