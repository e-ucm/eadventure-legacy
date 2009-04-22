package es.eucm.eadventure.editor.gui.otherpanels.positionimagepanels;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class TextImagePanel extends PositionImagePanel{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    
    private String text;
    
    /**
     * Color of front of the text.
     */
    private Color textFrontColor;	
    
    /**
     * Color of the border of the text.
     */
    private Color textBorderColor;
    
    public TextImagePanel(String imagePath,String text) {
	super(imagePath);
	this.text = text;
	this.textFrontColor = Color.white;
	this.textBorderColor =  Color.yellow;
	selectedX = 0;
	selectedY = 0;
	
    }
    
    public TextImagePanel(String text,Color textFrontColor, Color textBorderColor){
	super(null);
	this.text = text;
	this.textFrontColor = textFrontColor;
	this.textBorderColor =  textBorderColor;
	selectedX = 0;
	selectedY = 0;
    }
    
    
    

	/**
     * @return the text
     */
    public String getText() {
        return text;
    }

	/**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @param textFrontColor the textFrontColor to set
     */
    public void setTextFrontColor(Color textFrontColor) {
        this.textFrontColor = textFrontColor;
    }

    /**
     * @param textBorderColor the textBorderColor to set
     */
    public void setTextBorderColor(Color textBorderColor) {
        this.textBorderColor = textBorderColor;
    }

	@Override
	public void paint( Graphics g ) {
		super.paint( g );
		
		// Calculate the position to paint
		g.setFont( g.getFont( ).deriveFont( 18.0f ) );
		FontMetrics fontMetrics = g.getFontMetrics( );
		
		int x = ( selectedX/2 ) - ( fontMetrics.stringWidth( text ) /2 );
		int y = ( selectedY/2 ) - ( fontMetrics.getAscent( ) /2);

		// Draw the border of the text
		g.setColor( textBorderColor );
		g.drawString( text, x - 1, y - 1 );
		g.drawString( text, x - 1, y + 1 );
		g.drawString( text, x + 1, y - 1 );
		g.drawString( text, x + 1, y + 1 );

		// Draw the text
		g.setColor( textFrontColor );
		g.drawString( text, x, y );
		
	}
   
}
