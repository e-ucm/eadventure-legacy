package es.eucm.eadventure.editor.gui.elementpanels.conversation;

import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

/**
 * Status bar for being added in the lower area of a JPanel, to display information
 */
public class StatusBar extends JPanel {

	/* Attributes */

	/**
	 * Required
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Height of the
	 */
	private static final int HEIGHT = 20;

	/**
	 * Number of areas in the status bar
	 */
	private int areasCount;

	/**
	 * Set of labels included in the status bar
	 */
	private JLabel[] areas;

	/**
	 * Border for the areas
	 */
	private Border areaBorder;

	/**
	 * Constructor, with a single empty area
	 */
	public StatusBar( ) {
		this( 1 );
	}

	/**
	 * Constructor, with a single area displaying the given text
	 * 
	 * @param text
	 *            Text to be displayed in the status bar
	 */
	public StatusBar( String text ) {
		this( 1 );

		setText( text );
	}

	/**
	 * Constructor, with a number of areas equal to the number of strings recieved
	 * 
	 * @param texts
	 *            Array of strings to be displayed in the status bar
	 */
	public StatusBar( String[] texts ) {
		// Create a status bar with given length
		this( texts.length );

		// Initialize the text in each area
		for( int i = 0; i < areasCount; i++ ) {
			setText( i, texts[i] );
		}
	}

	/**
	 * Constructor, with a given number of empty areas
	 * 
	 * @param areasCount
	 *            Number of areas for the status bar
	 */
	public StatusBar( int areasCount ) {
		// Create the array of labels
		this.areasCount = areasCount;
		areas = new JLabel[areasCount];

		// Create the border
		areaBorder = new CompoundBorder( new EmptyBorder( new Insets( 0, 1, 0, 1 ) ), new BevelBorder( BevelBorder.LOWERED ) );

		// Set layout for the status bar
		setPreferredSize( new Dimension( 0, HEIGHT ) );
		BoxLayout boxLayout = new BoxLayout( this, BoxLayout.X_AXIS );
		setLayout( boxLayout );
		// For each area, create it, set the border and add it to the panel
		for( int i = 0; i < areasCount; i++ ) {
			areas[i] = new JLabel( );
			areas[i].setBorder( areaBorder );
			areas[i].setMaximumSize( new Dimension( Short.MAX_VALUE, HEIGHT ) );
			areas[i].setPreferredSize( new Dimension( 20, HEIGHT ) );
			add( areas[i] );

			// Add a separator between elements
			if( i < areasCount - 1 )
				add( Box.createRigidArea( new Dimension( 2, HEIGHT ) ) );
		}

	}

	/**
	 * Sets the width of the given area
	 * 
	 * @param areaIndex
	 *            Index of the area to be resized
	 * @param width
	 *            New width for the area
	 */
	public void setAreaWidth( int areaIndex, int width ) {
		areas[areaIndex].setMaximumSize( new Dimension( width, HEIGHT ) );
		areas[areaIndex].setPreferredSize( new Dimension( width, HEIGHT ) );
	}

	/**
	 * Resets the width of the given area
	 * 
	 * @param areaIndex
	 *            Index of the area to be resized
	 */
	public void resetAreaWidth( int areaIndex ) {
		areas[areaIndex].setMaximumSize( new Dimension( Short.MAX_VALUE, HEIGHT ) );
		areas[areaIndex].setPreferredSize( new Dimension( 20, HEIGHT ) );
	}

	/**
	 * Sets the text of the given area
	 * 
	 * @param areaIndex
	 *            Index of the area to change its text
	 * @param text
	 *            New text for the area
	 */
	public void setText( int areaIndex, String text ) {
		areas[areaIndex].setText( text );
	}

	/**
	 * Sets the text of the first area
	 * 
	 * @param text
	 *            New text for the first area
	 */
	public void setText( String text ) {
		setText( 0, text );
	}
}
