package es.eucm.eadventure.common.data.chapterdata.elements;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapterdata.ConversationReference;

/**
 * This class holds the data of a non playing character (npc) in eAdventure
 */
public class NPC extends Element {

	/**
	 * The front color of the text of the character
	 */
	private String textFrontColor;

	/**
	 * The border color of the text of the character
	 */
	private String textBorderColor;

	/**
	 * List of conversation references of the player
	 */
	private List<ConversationReference> conversationReferences;

	/**
	 * Creates a new NPC
	 * 
	 * @param id
	 *            the id of the npc
	 */
	public NPC( String id ) {
		super( id );
		conversationReferences = new ArrayList<ConversationReference>( );

		// Default colors are white for the front color, and black for the border color
		textFrontColor = "#FFFFFF";
		textBorderColor = "#000000";
	}

	/**
	 * Returns the front color of the character's text
	 * 
	 * @return String with the color, in format "#RRGGBB"
	 */
	public String getTextFrontColor( ) {
		return textFrontColor;
	}

	/**
	 * Returns the boder color of the character's text
	 * 
	 * @return String with the color, in format "#RRGGBB"
	 */
	public String getTextBorderColor( ) {
		return textBorderColor;
	}

	/**
	 * Returns the list of conversation references related to this npc
	 * 
	 * @return the list of conversation references related to this npc
	 */
	public List<ConversationReference> getConversationReferences( ) {
		return conversationReferences;
	}

	/**
	 * Sets the front color of the character's text
	 * 
	 * @param textFrontColor
	 *            String with the color, in format "#RRGGBB"
	 */
	public void setTextFrontColor( String textFrontColor ) {
		this.textFrontColor = textFrontColor;
	}

	/**
	 * Sets the border color of the character's text
	 * 
	 * @param textBorderColor
	 *            String with the color, in format "#RRGGBB"
	 */
	public void setTextBorderColor( String textBorderColor ) {
		this.textBorderColor = textBorderColor;
	}

	/**
	 * Adds a conversation reference to the list of conversation references
	 * 
	 * @param conversationReference
	 *            the conversation reference to add
	 */
	public void addConversationReference( ConversationReference conversationReference ) {
		conversationReferences.add( conversationReference );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString( ) {
		StringBuffer sb = new StringBuffer( 40 );

		sb.append( "\n" );
		sb.append( super.toString( ) );
		for( ConversationReference conversationReference : conversationReferences )
			sb.append( conversationReference.toString( ) );

		return sb.toString( );
	}
}
