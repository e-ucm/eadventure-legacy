package es.eucm.eadventure.common.loader.subparsers;

import org.xml.sax.Attributes;

import es.eucm.eadventure.common.data.chapter.Chapter;
import es.eucm.eadventure.common.data.chapter.Timer;
import es.eucm.eadventure.common.data.chapter.conditions.Conditions;
import es.eucm.eadventure.common.data.chapter.effects.Effects;

/**
 * Class to subparse timers
 */
public class TimerSubParser extends SubParser {

	/* Attributes */

	/**
	 * Constant for reading nothing
	 */
	private static final int READING_NONE = 0;

	/**
	 * Constant for subparsing nothing
	 */
	private static final int SUBPARSING_NONE = 0;

	/**
	 * Constant for subparsing condition tag
	 */
	private static final int SUBPARSING_CONDITION = 1;

	/**
	 * Constant for subparsing effect tag
	 */
	private static final int SUBPARSING_EFFECT = 2;

	/**
	 * Stores the current element being parsed
	 */
	private int reading = READING_NONE;

	/**
	 * Stores the current element being subparsed
	 */
	private int subParsing = SUBPARSING_NONE;

	/**
	 * Stores the current timer being parsed
	 */
	private Timer timer;

	/**
	 * Stores the current conditions being parsed
	 */
	private Conditions currentConditions;

	/**
	 * Stores the current effects being parsed
	 */
	private Effects currentEffects;

	/**
	 * The subparser for the condition or effect tags
	 */
	private SubParser subParser;

	/* Methods */

	/**
	 * Constructor
	 * 
	 * @param chapter
	 *            Chapter data to store the read data
	 */
	public TimerSubParser( Chapter chapter ) {
		super( chapter );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.eucm.eadventure.engine.loader.subparsers.SubParser#startElement(java.lang.String, java.lang.String,
	 *      java.lang.String, org.xml.sax.Attributes)
	 */
	public void startElement( String namespaceURI, String sName, String qName, Attributes attrs ) {

		// If no element is being subparsed
		if( subParsing == SUBPARSING_NONE ) {

			// If it is a timer tag, create a new timer with its time
			if( qName.equals( "timer" ) ) {
				String time = "";
				boolean initialScene = false;

				for( int i = 0; i < attrs.getLength( ); i++ ) {
					if( attrs.getQName( i ).equals( "time" ) )
						time = attrs.getValue( i );
				}

				timer = new Timer( Long.parseLong( time ) );
			}

			// If it is a condition tag, create the new condition, the subparser and switch the state
			else if( qName.equals( "init-condition" ) ||  qName.equals( "end-condition" )) {
				currentConditions = new Conditions( );
				subParser = new ConditionSubParser( currentConditions, chapter );
				subParsing = SUBPARSING_CONDITION;
			}

			// If it is a effect tag, create the new effect, the subparser and switch the state
			else if( qName.equals( "effect" ) ||  qName.equals( "post-effect" )) {
				currentEffects = new Effects( );
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
	 * @see es.eucm.eadventure.engine.loader.subparsers.SubParser#endElement(java.lang.String, java.lang.String,
	 *      java.lang.String)
	 */
	public void endElement( String namespaceURI, String sName, String qName ) {

		// If no element is being subparsed
		if( subParsing == SUBPARSING_NONE ) {

			// If it is a timer tag, add it to the game data
			if( qName.equals( "timer" ) ) {
				chapter.addTimer( timer );
			}

			// If it is a documentation tag, hold the documentation in the slidescene
			else if( qName.equals( "documentation" ) ) {
				timer.setDocumentation( currentString.toString( ).trim( ) );
			}

			// Reset the current string
			currentString = new StringBuffer( );
		}

		// If a condition is being subparsed
		else if( subParsing == SUBPARSING_CONDITION ) {
			// Spread the call
			subParser.endElement( namespaceURI, sName, qName );

			// If the condition tag is being closed
			if( qName.equals( "init-condition" ) ) {
				timer.setInitCond( currentConditions );
				
				// Switch the state
				subParsing = SUBPARSING_NONE;
			}
			
			// If the condition tag is being closed
			if( qName.equals( "end-condition" ) ) {
				timer.setEndCond( currentConditions );
				
				// Switch the state
				subParsing = SUBPARSING_NONE;
			}
		}

		// If an effect is being subparsed
		else if( subParsing == SUBPARSING_EFFECT ) {
			// Spread the call
			subParser.endElement( namespaceURI, sName, qName );

			// If the effect tag is being closed, store the effect in the next scene and switch the state
			if( qName.equals( "effect" ) ) {
				timer.setEffects( currentEffects );
				subParsing = SUBPARSING_NONE;
			}

			// If the effect tag is being closed, add the post-effects to the current next scene and switch the state
			if( qName.equals( "post-effect" ) ) {
				timer.setPostEffects( currentEffects );
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

		// If it is reading an effect or a condition
		else
			subParser.characters( buf, offset, len );
	}
}
