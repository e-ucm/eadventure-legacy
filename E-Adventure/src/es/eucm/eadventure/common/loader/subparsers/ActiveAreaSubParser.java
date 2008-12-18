package es.eucm.eadventure.common.loader.subparsers;

import org.xml.sax.Attributes;

import es.eucm.eadventure.common.data.chapter.Chapter;
import es.eucm.eadventure.common.data.chapter.conditions.Conditions;
import es.eucm.eadventure.common.data.chapter.effects.Effects;
import es.eucm.eadventure.common.data.chapter.elements.ActiveArea;
import es.eucm.eadventure.common.data.chapter.scenes.Scene;

/**
 * Class to subparse items.
 */
public class ActiveAreaSubParser extends SubParser {

	/* Attributes */

	/**
	 * Constant for reading nothing.
	 */
	private static final int READING_NONE = 0;	

	/**
	 * Constant for subparsing nothing.
	 */
	private static final int SUBPARSING_NONE = 0;

	/**
	 * Constant for subparsing condition tag.
	 */
	private static final int SUBPARSING_CONDITION = 1;

	/**
	 * Constant for subparsing effect tag.
	 */
	private static final int SUBPARSING_EFFECT = 2;

	private static final int SUBPARSING_ACTIONS = 3;
	
	/**
	 * Store the current element being parsed.
	 */
	private int reading = READING_NONE;

	/**
	 * Stores the current element being subparsed.
	 */
	private int subParsing = SUBPARSING_NONE;

	/**
	 * ActiveArea being parsed.
	 */
	private ActiveArea activeArea;

	/**
	 * Current conditions being parsed.
	 */
	private Conditions currentConditions;

	/**
	 * Current effects being parsed.
	 */
	private Effects currentEffects;

	/**
	 * Subparser for effects and conditions.
	 */
	private SubParser subParser;
	
	/**
	 * Stores the scene where the area should be attached
	 */
	private Scene scene;
	
	private int nAreas;

	/* Methods */

	/**
	 * Constructor.
	 * 
	 * @param chapter
	 *            Chapter data to store the read data
	 */
	public ActiveAreaSubParser( Chapter chapter, Scene scene, int nAreas ) {
		super( chapter );
		this.nAreas =nAreas;
		this.scene = scene;
	}

	private String generateId(){
		return "area" + Integer.toString( nAreas+1 ) + "scene" + scene.getId();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see es.eucm.eadventure.engine.cargador.subparsers.SubParser#startElement(java.lang.String, java.lang.String,
	 *      java.lang.String, org.xml.sax.Attributes)
	 */
	public void startElement( String namespaceURI, String sName, String qName, Attributes attrs ) {

		// If no element is being subparsed
		if( subParsing == SUBPARSING_NONE ) {
			// If it is a object tag, create the new object (with its id)
			if( qName.equals( "active-area" ) ) {

				int x = 0, y = 0, width = 0, height = 0;
				String id = null;
				
				for( int i = 0; i < attrs.getLength( ); i++ ) {
					if( attrs.getQName( i ).equals( "x" ) )
						x = Integer.parseInt( attrs.getValue( i ) );
					if( attrs.getQName( i ).equals( "y" ) )
						y = Integer.parseInt( attrs.getValue( i ) );
					if( attrs.getQName( i ).equals( "width" ) )
						width = Integer.parseInt( attrs.getValue( i ) );
					if( attrs.getQName( i ).equals( "height" ) )
						height = Integer.parseInt( attrs.getValue( i ) );
					if ( attrs.getQName(i).equals("id"))
						id = attrs.getValue(i);
					
				}

				activeArea = new ActiveArea( (id==null?generateId():id), x, y, width, height );
			}

			else if( qName.equals("actions")) {
				subParser = new ActionsSubParser(chapter, activeArea);
				subParsing = SUBPARSING_ACTIONS;
			}

			// If it is a condition tag, create new conditions and switch the state
			else if( qName.equals( "condition" ) ) {
				currentConditions = new Conditions( );
				subParser = new ConditionSubParser( currentConditions, chapter );
				subParsing = SUBPARSING_CONDITION;
			}

			// If it is a effect tag, create new effects and switch the state
			else if( qName.equals( "effect" ) ) {
				subParser = new EffectSubParser( currentEffects, chapter );
				subParsing = SUBPARSING_EFFECT;
			}
		}

		// If it is reading an effect or a condition, spread the call
		if( subParsing != SUBPARSING_NONE ) {
			subParser.startElement( namespaceURI, sName, qName, attrs );
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.eucm.eadventure.engine.cargador.subparsers.SubParser#endElement(java.lang.String, java.lang.String,
	 *      java.lang.String)
	 */
	public void endElement( String namespaceURI, String sName, String qName ) {

		// If no element is being subparsed
		if( subParsing == SUBPARSING_NONE ) {

			// If it is an object tag, store the object in the game data
			if( qName.equals( "active-area" ) ) {
				scene.addActiveArea( activeArea );
			}

			// If it is a name tag, store the name in the object
			else if( qName.equals( "name" ) ) {
				activeArea.setName( currentString.toString( ).trim( ) );
			}

			// If it is a documentation tag, hold the documentation in the current element
			else if( qName.equals( "documentation" ) ) {
				activeArea.setDocumentation( currentString.toString( ).trim( ) );
			}

			// If it is a brief tag, store the brief description in the object
			else if( qName.equals( "brief" ) ) {
				activeArea.setDescription( currentString.toString( ).trim( ) );
			}

			// If it is a detailed tag, store the detailed description in the object
			else if( qName.equals( "detailed" ) ) {
				activeArea.setDetailedDescription( currentString.toString( ).trim( ) );
			}

			// Reset the current string
			currentString = new StringBuffer( );
		}

		// If a condition is being subparsed
		else if( subParsing == SUBPARSING_CONDITION ) {
			// Spread the call
			subParser.endElement( namespaceURI, sName, qName );

			// If the condition tag is being closed
			if( qName.equals( "condition" ) ) {
				if (reading == READING_NONE){
					this.activeArea.setConditions( currentConditions );
				}
				// Switch state
				subParsing = SUBPARSING_NONE;
			}
		}

		// If an effect is being subparsed
		else if( subParsing == SUBPARSING_EFFECT ) {
			// Spread the call
			subParser.endElement( namespaceURI, sName, qName );

			// If the effect tag is being closed, switch the state
			if( qName.equals( "effect" ) ) {
				subParsing = SUBPARSING_NONE;
			}
		}
		
		else if (subParsing == SUBPARSING_ACTIONS) {
			subParser.endElement(namespaceURI, sName, qName);
			if (qName.equals( "actions")) {
				subParsing = SUBPARSING_NONE;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.eucm.eadventure.engine.loader.subparsers.SubParser#characters(char[], int, int)
	 */
	public void characters( char[] buf, int offset, int len ) {
		// If no element is being subparsed
		if( subParsing == SUBPARSING_NONE )
			super.characters( buf, offset, len );

		// If it is reading an effect or a condition, spread the call
		else
			subParser.characters( buf, offset, len );
	}
}
