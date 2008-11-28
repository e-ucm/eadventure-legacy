package es.eucm.eadventure.common.loader.subparsers;

import org.xml.sax.Attributes;

import es.eucm.eadventure.common.data.chapter.Chapter;
import es.eucm.eadventure.common.data.chapter.conditions.Condition;
import es.eucm.eadventure.common.data.chapter.conditions.Conditions;
import es.eucm.eadventure.common.data.chapter.conditions.VarCondition;

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
                        conditions.addCondition( new Condition( attrs.getValue( i ), Condition.FLAG_ACTIVE ) );
                    if( reading == READING_EITHER )
                        currentEitherCondition.addCondition( new Condition( attrs.getValue( i ), Condition.FLAG_ACTIVE ) );
                    
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
                        conditions.addCondition( new Condition( attrs.getValue( i ), Condition.FLAG_INACTIVE ) );
                    if( reading == READING_EITHER )
                        currentEitherCondition.addCondition( new Condition( attrs.getValue( i ), Condition.FLAG_INACTIVE ) );
                    
                    chapter.addFlag( attrs.getValue( i ) );
                }
            }
        }
        
        // If it is a greater-than tag
        else if( qName.equals( "greater-than" ) ) {
        	// The var
        	String var = null;
        	// The value
        	int value = 0;
        	
            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getQName( i ).equals( "var" ) ) {
                	var = attrs.getValue( i );
                } else if( attrs.getQName( i ).equals( "value" ) ) {
                	value = Integer.parseInt( attrs.getValue( i ) );
                }
            }
            // Store the inactive flag in the conditions or either conditions
            if( reading == READING_NONE )
                conditions.addCondition( new VarCondition( var, Condition.VAR_GREATER_THAN, value ) );
            if( reading == READING_EITHER )
                currentEitherCondition.addCondition( new VarCondition( var, Condition.VAR_GREATER_THAN, value ) );
            chapter.addVar( var );
        }

        // If it is a greater-equals-than tag
        else if( qName.equals( "greater-equals-than" ) ) {
        	// The var
        	String var = null;
        	// The value
        	int value = 0;
        	
            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getQName( i ).equals( "var" ) ) {
                	var = attrs.getValue( i );
                } else if( attrs.getQName( i ).equals( "value" ) ) {
                	value = Integer.parseInt( attrs.getValue( i ) );
                }
            }
            // Store the inactive flag in the conditions or either conditions
            if( reading == READING_NONE )
                conditions.addCondition( new VarCondition( var, Condition.VAR_GREATER_EQUALS_THAN, value ) );
            if( reading == READING_EITHER )
                currentEitherCondition.addCondition( new VarCondition( var, Condition.VAR_GREATER_EQUALS_THAN, value ) );
            chapter.addVar( var );
        }

        // If it is a less-than tag
        else if( qName.equals( "less-than" ) ) {
        	// The var
        	String var = null;
        	// The value
        	int value = 0;
        	
            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getQName( i ).equals( "var" ) ) {
                	var = attrs.getValue( i );
                } else if( attrs.getQName( i ).equals( "value" ) ) {
                	value = Integer.parseInt( attrs.getValue( i ) );
                }
            }
            // Store the inactive flag in the conditions or either conditions
            if( reading == READING_NONE )
                conditions.addCondition( new VarCondition( var, Condition.VAR_LESS_THAN, value ) );
            if( reading == READING_EITHER )
                currentEitherCondition.addCondition( new VarCondition( var, Condition.VAR_LESS_THAN, value ) );
            chapter.addVar( var );
        }

        // If it is a less-equals-than tag
        else if( qName.equals( "less-equals-than" ) ) {
        	// The var
        	String var = null;
        	// The value
        	int value = 0;
        	
            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getQName( i ).equals( "var" ) ) {
                	var = attrs.getValue( i );
                } else if( attrs.getQName( i ).equals( "value" ) ) {
                	value = Integer.parseInt( attrs.getValue( i ) );
                }
            }
            // Store the inactive flag in the conditions or either conditions
            if( reading == READING_NONE )
                conditions.addCondition( new VarCondition( var, Condition.VAR_LESS_EQUALS_THAN, value ) );
            if( reading == READING_EITHER )
                currentEitherCondition.addCondition( new VarCondition( var, Condition.VAR_LESS_EQUALS_THAN, value ) );
            chapter.addVar( var );
        }

        // If it is a equals-than tag
        else if( qName.equals( "equals" ) ) {
        	// The var
        	String var = null;
        	// The value
        	int value = 0;
        	
            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getQName( i ).equals( "var" ) ) {
                	var = attrs.getValue( i );
                } else if( attrs.getQName( i ).equals( "value" ) ) {
                	value = Integer.parseInt( attrs.getValue( i ) );
                }
            }
            // Store the inactive flag in the conditions or either conditions
            if( reading == READING_NONE )
                conditions.addCondition( new VarCondition( var, Condition.VAR_EQUALS, value ) );
            if( reading == READING_EITHER )
                currentEitherCondition.addCondition( new VarCondition( var, Condition.VAR_EQUALS, value ) );
            chapter.addVar( var );
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
