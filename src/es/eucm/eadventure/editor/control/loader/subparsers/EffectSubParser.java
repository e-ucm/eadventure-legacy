package es.eucm.eadventure.editor.control.loader.subparsers;

import org.xml.sax.Attributes;

import es.eucm.eadventure.common.data.chapterdata.Chapter;
import es.eucm.eadventure.common.data.chapterdata.effects.ActivateEffect;
import es.eucm.eadventure.common.data.chapterdata.effects.CancelActionEffect;
import es.eucm.eadventure.common.data.chapterdata.effects.ConsumeObjectEffect;
import es.eucm.eadventure.common.data.chapterdata.effects.DeactivateEffect;
import es.eucm.eadventure.common.data.chapterdata.effects.Effect;
import es.eucm.eadventure.common.data.chapterdata.effects.Effects;
import es.eucm.eadventure.common.data.chapterdata.effects.GenerateObjectEffect;
import es.eucm.eadventure.common.data.chapterdata.effects.MoveNPCEffect;
import es.eucm.eadventure.common.data.chapterdata.effects.MovePlayerEffect;
import es.eucm.eadventure.common.data.chapterdata.effects.PlayAnimationEffect;
import es.eucm.eadventure.common.data.chapterdata.effects.PlaySoundEffect;
import es.eucm.eadventure.common.data.chapterdata.effects.RandomEffect;
import es.eucm.eadventure.common.data.chapterdata.effects.SpeakCharEffect;
import es.eucm.eadventure.common.data.chapterdata.effects.SpeakPlayerEffect;
import es.eucm.eadventure.common.data.chapterdata.effects.TriggerBookEffect;
import es.eucm.eadventure.common.data.chapterdata.effects.TriggerConversationEffect;
import es.eucm.eadventure.common.data.chapterdata.effects.TriggerCutsceneEffect;
import es.eucm.eadventure.common.data.chapterdata.effects.TriggerLastSceneEffect;
import es.eucm.eadventure.common.data.chapterdata.effects.TriggerSceneEffect;

/**
 * Class to subparse effects
 */
public class EffectSubParser extends SubParser {

	/* Attributes */

	/**
	 * Stores the current id target
	 */
	private String currentCharIdTarget;

	/**
	 * Stores the effects being parsed
	 */
	private Effects effects;
	
    /**
     * Constants for reading random-effect
     */
    private boolean positiveBlockRead = false;
    private boolean readingRandomEffect = false;
    private RandomEffect randomEffect;

	/* Methods */

	/**
	 * Constructor
	 * 
	 * @param effects
	 *            Structure in which the effects will be placed
	 * @param chapter
	 *            Chapter data to store the read data
	 */
	public EffectSubParser( Effects effects, Chapter chapter ) {
		super( chapter );
		this.effects = effects;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.eucm.eadventure.engine.loader.subparsers.SubParser#startElement(java.lang.String, java.lang.String,
	 *      java.lang.String, org.xml.sax.Attributes)
	 */
	public void startElement( String namespaceURI, String sName, String qName, Attributes attrs ) {
		Effect newEffect = null;
		
		// If it is a cancel-action tag
		if( qName.equals( "cancel-action" ) ) {
			newEffect= new CancelActionEffect( ) ;
		}

		// If it is a activate tag
		else if( qName.equals( "activate" ) ) {
			for( int i = 0; i < attrs.getLength( ); i++ )
				if( attrs.getQName( i ).equals( "flag" ) ) {
					newEffect=  new ActivateEffect( attrs.getValue( i ) ) ;
				}
		}

		// If it is a deactivate tag
		else if( qName.equals( "deactivate" ) ) {
			for( int i = 0; i < attrs.getLength( ); i++ )
				if( attrs.getQName( i ).equals( "flag" ) ) {
					newEffect=  new DeactivateEffect( attrs.getValue( i ) ) ;
				}
		}

		// If it is a consume-object tag
		else if( qName.equals( "consume-object" ) ) {
			for( int i = 0; i < attrs.getLength( ); i++ )
				if( attrs.getQName( i ).equals( "idTarget" ) )
					newEffect=  new ConsumeObjectEffect( attrs.getValue( i ) ) ;
		}

		// If it is a generate-object tag
		else if( qName.equals( "generate-object" ) ) {
			for( int i = 0; i < attrs.getLength( ); i++ )
				if( attrs.getQName( i ).equals( "idTarget" ) )
					newEffect=  new GenerateObjectEffect( attrs.getValue( i ) ) ;
		}

		// If it is a speak-char tag
		else if( qName.equals( "speak-char" ) ) {

			// Store the idTarget, to store the effect when the tag is closed
			currentCharIdTarget = null;

			for( int i = 0; i < attrs.getLength( ); i++ )
				if( attrs.getQName( i ).equals( "idTarget" ) )
					currentCharIdTarget = attrs.getValue( i );
		}

		// If it is a trigger-book tag
		else if( qName.equals( "trigger-book" ) ) {
			for( int i = 0; i < attrs.getLength( ); i++ )
				if( attrs.getQName( i ).equals( "idTarget" ) )
					newEffect= new TriggerBookEffect( attrs.getValue( i ) ) ;
		}
		
		// If it is a trigger-last-scene tag
		else if( qName.equals( "trigger-last-scene" ) ) {
			newEffect=  new TriggerLastSceneEffect( ) ;
		}

		// If it is a play-sound tag
		else if( qName.equals( "play-sound" ) ) {
			// Store the path and background
			String path = "";
			boolean background = true;
			for( int i = 0; i < attrs.getLength( ); i++ ) {
				if( attrs.getQName( i ).equals( "background" ) )
					background = attrs.getValue( i ).equals( "yes" );
				else if( attrs.getQName( i ).equals( "uri" ) )
					path = attrs.getValue( i );
			}

			// Add the new play sound effect
			newEffect=  new PlaySoundEffect( background, path );
		}

		// If it is a trigger-conversation tag
		else if( qName.equals( "trigger-conversation" ) ) {
			for( int i = 0; i < attrs.getLength( ); i++ )
				if( attrs.getQName( i ).equals( "idTarget" ) )
					newEffect=  new TriggerConversationEffect( attrs.getValue( i ) ) ;
		}

		// If it is a trigger-cutscene tag
		else if( qName.equals( "trigger-cutscene" ) ) {
			for( int i = 0; i < attrs.getLength( ); i++ )
				if( attrs.getQName( i ).equals( "idTarget" ) )
					newEffect=  new TriggerCutsceneEffect( attrs.getValue( i ) ) ;
		}

		// If it is a trigger-scene tag
		else if( qName.equals( "trigger-scene" ) ) {
			String scene = "";
			int x = 0;
			int y = 0;
			for( int i = 0; i < attrs.getLength( ); i++ )
				if( attrs.getQName( i ).equals( "idTarget" ) )
					scene = attrs.getValue( i );
				else if( attrs.getQName( i ).equals( "x" ) )
					x = Integer.parseInt( attrs.getValue( i ) );
				else if( attrs.getQName( i ).equals( "y" ) )
					y = Integer.parseInt( attrs.getValue( i ) );

			newEffect=  new TriggerSceneEffect( scene, x, y );
		}

		// If it is a play-animation tag
		else if( qName.equals( "play-animation" ) ) {
			String path = "";
			int x = 0;
			int y = 0;
			for( int i = 0; i < attrs.getLength( ); i++ ) {
				if( attrs.getQName( i ).equals( "uri" ) )
					path = attrs.getValue( i );
				else if( attrs.getQName( i ).equals( "x" ) )
					x = Integer.parseInt( attrs.getValue( i ) );
				else if( attrs.getQName( i ).equals( "y" ) )
					y = Integer.parseInt( attrs.getValue( i ) );
			}

			// Add the new play sound effect
			newEffect=  new PlayAnimationEffect( path, x, y );
		}

		// If it is a move-player tag
		else if( qName.equals( "move-player" ) ) {
			int x = 0;
			int y = 0;
			for( int i = 0; i < attrs.getLength( ); i++ ) {
				if( attrs.getQName( i ).equals( "x" ) )
					x = Integer.parseInt( attrs.getValue( i ) );
				else if( attrs.getQName( i ).equals( "y" ) )
					y = Integer.parseInt( attrs.getValue( i ) );
			}

			// Add the new move player effect
			newEffect=  new MovePlayerEffect( x, y );
		}

		// If it is a move-npc tag
		else if( qName.equals( "move-npc" ) ) {
			String npcTarget = "";
			int x = 0;
			int y = 0;
			for( int i = 0; i < attrs.getLength( ); i++ ) {
				if( attrs.getQName( i ).equals( "idTarget" ) )
					npcTarget = attrs.getValue( i );
				else if( attrs.getQName( i ).equals( "x" ) )
					x = Integer.parseInt( attrs.getValue( i ) );
				else if( attrs.getQName( i ).equals( "y" ) )
					y = Integer.parseInt( attrs.getValue( i ) );
			}

			// Add the new move NPC effect
			newEffect=  new MoveNPCEffect( npcTarget, x, y ) ;
		}
		
        // Random effect tag
        else if( qName.equals( "random-effect" ) ) {
            int probability = 0;
            for( int i = 0; i < attrs.getLength( ); i++ ) {
                if( attrs.getQName( i ).equals( "probability" ) )
                    probability = Integer.parseInt( attrs.getValue( i ) );
            }
            
            // Add the new random effect
            randomEffect =new RandomEffect( probability );
            newEffect = randomEffect;
            readingRandomEffect = true;
            positiveBlockRead = false;
        }

		// Not reading Random effect: Add the new FunctionalEffect if not null
		if (!readingRandomEffect && newEffect!=null){
			effects.add( newEffect );
		}

		// Reading random effect
		if (readingRandomEffect){
			// When we have just created the effect, add it
			if (newEffect!=null && newEffect == randomEffect){
				effects.add( newEffect );
			} 
			// Otherwise, determine if it is positive or negative effect 
			else if (newEffect!=null && !positiveBlockRead){
				randomEffect.setPositiveEffect( newEffect );
				positiveBlockRead = true;
			}
			// Negative effect 
			else if (newEffect!=null && positiveBlockRead){
				randomEffect.setNegativeEffect( newEffect );
				positiveBlockRead = false;
				readingRandomEffect = false;
				randomEffect = null;
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
		
		Effect newEffect = null;
		
		// If it is a speak-player
		if( qName.equals( "speak-player" ) ) {
			// Add the effect and clear the current string
			newEffect = new SpeakPlayerEffect( currentString.toString( ).trim( ) ) ;
		}

		// If it is a speak-char
		else if( qName.equals( "speak-char" ) ) {
			// Add the effect and clear the current string
			newEffect = new SpeakCharEffect( currentCharIdTarget, currentString.toString( ).trim( ) ) ;
		}
		
		// Not reading Random effect: Add the new FunctionalEffect if not null
		if (!readingRandomEffect && newEffect!=null){
			effects.add( newEffect );
		}

		// Reading random effect
		if (readingRandomEffect){
			// When we have just created the effect, add it
			if (newEffect!=null && newEffect == randomEffect){
				effects.add( newEffect );
			} 
			// Otherwise, determine if it is positive or negative effect 
			else if (newEffect!=null && !positiveBlockRead){
				randomEffect.setPositiveEffect( newEffect );
				positiveBlockRead = true;
			}
			// Negative effect 
			else if (newEffect!=null && positiveBlockRead){
				randomEffect.setNegativeEffect( newEffect );
				positiveBlockRead = false;
				readingRandomEffect = false;
				randomEffect = null;
			}

		}

		// Reset the current string
		currentString = new StringBuffer( );
	}
}