package es.eucm.eadventure.adventureeditor.data.chapterdata.elements;

/**
 * This class holds the data for the player in eAdventure
 */
public class Player extends Element {

	/**
	 * Constant identifier of the player (used with conversation lines, also).
	 */
	public static final String IDENTIFIER = "Player";

	/**
	 * The front color of the text of the player
	 */
	private String textFrontColor;

	/**
	 * The border color of the text of the player
	 */
	private String textBorderColor;

	/**
	 * Creates a new player
	 */
	public Player( ) {
		super( IDENTIFIER );

		// Default colors are white for the front color, and black for the border color
		textFrontColor = "#FFFFFF";
		textBorderColor = "#000000";
	}

	/**
	 * Returns the front color of the player's text
	 * 
	 * @return String with the color, in format "#RRGGBB"
	 */
	public String getTextFrontColor( ) {
		return textFrontColor;
	}

	/**
	 * Returns the boder color of the player's text
	 * 
	 * @return String with the color, in format "#RRGGBB"
	 */
	public String getTextBorderColor( ) {
		return textBorderColor;
	}

	/**
	 * Sets the front color of the player's text
	 * 
	 * @param textFrontColor
	 *            String with the color, in format "#RRGGBB"
	 */
	public void setTextFrontColor( String textFrontColor ) {
		this.textFrontColor = textFrontColor;
	}

	/**
	 * Sets the border color of the player's text
	 * 
	 * @param textBorderColor
	 *            String with the color, in format "#RRGGBB"
	 */
	public void setTextBorderColor( String textBorderColor ) {
		this.textBorderColor = textBorderColor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString( ) {
		return super.toString( );
	}
}