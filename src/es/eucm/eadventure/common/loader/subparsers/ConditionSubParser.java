package es.eucm.eadventure.common.loader.subparsers;

import org.xml.sax.Attributes;

import es.eucm.eadventure.common.data.chapter.Chapter;
import es.eucm.eadventure.common.data.chapter.conditions.Condition;
import es.eucm.eadventure.common.data.chapter.conditions.Conditions;

/**
 * Class to subparse conditions
 */
public class ConditionSubParser extends SubParser {

	/* Attributes */

	/**
	 * Constant for reading nothing
	 */
	private static final int READING_NONE = 0;

	/**
	 * Constant for reading either tag
	 */
	private static final int READING_EITHER = 1;

	/**
	 * Stores the current element being parsed
	 */
	private int reading = READING_NONE;

	/**
	 * Stores the conditions
	 */
	private Conditions conditions;

	/**
	 * Stores the current either conditions
	 */
	private Conditions currentEitherCondition;

	/* Methods */

	/**
	 * Constructor
	 * 
	 * @param conditions
	 *            Structure in which the conditions will be placed
	 * @param chapter
	 *            Chapter data to store the read data
	 */
	public ConditionSubParser( Conditions conditions, Chapter chapter ) {
		super( chapter );
		this.conditions = conditions;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.eucm.eadventure.engine.loader.subparsers.SubParser#startElement(java.lang.String, java.lang.String,
	 *      java.lang.String, org.xml.sax.Attributes)
	 */
	public void startElement( String namespaceURI, String sName, String qName, Attributes attrs ) {

		// If it is an either tag, create a new either conditions and switch the state
		if( qName.equals( "either" ) ) {
			currentEitherCondition = new Conditions( );
			reading = READING_EITHER;
		}

		// If it is an active tag
		else if( qName.equals( "active" ) ) {
			for( int i = 0; i < attrs.getLength( ); i++ ) {
				if( attrs.getQName( i ).equals( "flag" ) ) {

					// Store the active flag in the conditions or either conditions
					if( reading == READING_NONE )
						conditions.addCondition( new Condition( attrs.getValue( i ), true ) );
					if( reading == READING_EITHER )
						currentEitherCondition.addCondition( new Condition( attrs.getValue( i ), true ) );
					
					chapter.addFlag( attrs.getValue( i ) );
				}
			}
		}

		// If it is an inactive tag
		else if( qName.equals( "inactive" ) ) {
			for( int i = 0; i < attrs.getLength( ); i++ ) {
				if( attrs.getQName( i ).equals( "flag" ) ) {

					// Store the inactive flag in the conditions or either conditions
					if( reading == READING_NONE )
						conditions.addCondition( new Condition( attrs.getValue( i ), false ) );
					if( reading == READING_EITHER )
						currentEitherCondition.addCondition( new Condition( attrs.getValue( i ), false ) );
					
					chapter.addFlag( attrs.getValue( i ) );
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.eucm.eadventure.engine.loader.subparsers.SubParser#endElement(java.lang.String, java.lang.String,
	 *      java.lang.String)
	 */
	public void endElement( String namespaceURI, String sName, String qName ) {
		// If it is an either tag
		if( qName.equals( "either" ) ) {
			// Store the either condition in the condition, and switch the state back to normal
			conditions.addEitherCondition( currentEitherCondition );
			reading = READING_NONE;
		}
	}

}
