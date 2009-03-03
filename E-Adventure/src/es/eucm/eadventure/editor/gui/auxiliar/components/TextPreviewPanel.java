package es.eucm.eadventure.editor.gui.auxiliar.components;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JPanel;

import es.eucm.eadventure.common.gui.TextConstants;

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
	
	private boolean speechBubble;
	
	private Color bubbleBkgColor;
	
	private Color bubbleBorderColor;

	/**
	 * Constructor.
	 * 
	 * @param textFrontColor
	 *            Front text color
	 * @param textBorderColor
	 *            Border text color
	 */
	public TextPreviewPanel( Color textFrontColor, Color textBorderColor, boolean speechBubble, Color bubbleBkgColor, Color bubbleBorderColor) {
		this.textFrontColor = textFrontColor;
		this.textBorderColor = textBorderColor;
		this.speechBubble = speechBubble;
		this.bubbleBkgColor = bubbleBkgColor;
		this.bubbleBorderColor = bubbleBorderColor;
	}

	/**
	 * Updates the front color of the text and repaints the panel.
	 * 
	 * @param textFrontColor
	 *            Front text color
	 */
	public void setTextFrontColor( Color textFrontColor ) {
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
	
	public void setBubbleBkgColor( Color bubbleBkgColor) {
		this.bubbleBkgColor = bubbleBkgColor;
		repaint();
	}
	
	public void setBubbleBorderColor( Color bubbleBorderColor ) {
		this.bubbleBorderColor = bubbleBorderColor;
		repaint();
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
		
        int textBlockHeight = fontMetrics.getHeight( ) - fontMetrics.getLeading( );

		if (speechBubble) {
	        int maxWidth = fontMetrics.stringWidth(displayText);
	        g.setColor(bubbleBkgColor);
	        g.fillRoundRect(getWidth() / 2 - maxWidth / 2 - 5, y - textBlockHeight - 5, maxWidth + 10, textBlockHeight + 10, 20, 20);
	        g.setColor(bubbleBorderColor);
	        g.drawRoundRect(getWidth() / 2 - maxWidth / 2 - 5, y - textBlockHeight - 5, maxWidth + 10, textBlockHeight + 10, 20, 20);
	        g.setColor(bubbleBkgColor);
		}
		

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

	public void setShowsSpeechBubbles(boolean selected) {
		this.speechBubble = selected;
		repaint();
	}
}
