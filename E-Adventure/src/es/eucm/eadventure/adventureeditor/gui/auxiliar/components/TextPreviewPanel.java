package es.eucm.eadventure.adventureeditor.gui.auxiliar.components;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JPanel;

import es.eucm.eadventure.adventureeditor.gui.TextConstants;

/**
 * Panel to display preview colored text.
 * 
 * @author Bruno Torijano Bueno
 */
public class TextPreviewPanel extends JPanel {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Color of front of the text.
	 */
	private Color textFrontColor;

	/**
	 * Color of the border of the text.
	 */
	private Color textBorderColor;

	/**
	 * Constructor.
	 * 
	 * @param textFrontColor
	 *            Front text color
	 * @param textBorderColor
	 *            Border text color
	 */
	public TextPreviewPanel( Color textFrontColor, Color textBorderColor ) {
		this.textFrontColor = textFrontColor;
		this.textBorderColor = textBorderColor;
	}

	/**
	 * Updates the front color of the text and repaints the panel.
	 * 
	 * @param textFrontColor
	 *            Front text color
	 */
	public void setTextFrontColot( Color textFrontColor ) {
		this.textFrontColor = textFrontColor;
		repaint( );
	}

	/**
	 * Updates the border color of the text and repaints the panel.
	 * 
	 * @param textBorderColor
	 *            Border text color
	 */
	public void setTextBorderColor( Color textBorderColor ) {
		this.textBorderColor = textBorderColor;
		repaint( );
	}

	@Override
	public void paint( Graphics g ) {
		super.paint( g );

		// Get the text to display
		String displayText = TextConstants.getText( "GeneralText.PreviewText" );

		// Calculate the position to paint
		g.setFont( g.getFont( ).deriveFont( 18.0f ) );
		FontMetrics fontMetrics = g.getFontMetrics( );
		int x = ( getWidth( ) / 2 ) - ( fontMetrics.stringWidth( displayText ) / 2 );
		int y = ( getHeight( ) / 2 ) + ( fontMetrics.getAscent( ) / 2 );

		// Draw the border of the text
		g.setColor( textBorderColor );
		g.drawString( displayText, x - 1, y - 1 );
		g.drawString( displayText, x - 1, y + 1 );
		g.drawString( displayText, x + 1, y - 1 );
		g.drawString( displayText, x + 1, y + 1 );

		// Draw the text
		g.setColor( textFrontColor );
		g.drawString( displayText, x, y );
	}
}
