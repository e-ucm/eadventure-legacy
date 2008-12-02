package es.eucm.eadventure.common.loader.parsers;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import es.eucm.eadventure.common.data.animation.Animation;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.common.loader.subparsers.FrameSubParser;
import es.eucm.eadventure.common.loader.subparsers.TransitionSubParser;

public class AnimationHandler extends DefaultHandler {
	
    /**
     * String to store the current string in the XML file
     */
	StringBuffer currentString;
	
	/**
	 * Resources to store the current resources being read
	 */
	Resources currentResources;
	
    /**
     * Constant for reading nothing
     */
    private static final int READING_NONE = 0;

    /**
     * Constant for reading transition
     */
    private static final int READING_TRANSITION = 1;
    
    /**
     * Constant for reading frame
     */
    private static final int READING_FRAME = 2;

    /**
     * Stores the current element being read.
     */
    private int reading = READING_NONE;

    
    /**
     * Current subparser being used
     */
    private DefaultHandler subParser;
    
    
	/**
	 * Animation being read.
	 */
	private Animation animation;

	
	public AnimationHandler() {
	}
	
	@Override
	public void startElement( String namespaceURI, String sName, String qName, Attributes attrs ) throws SAXException {
		if (this.reading == READING_NONE) {

			if (qName.equals("animation")) {
				for (int i = 0; i < attrs.getLength(); i++) {
					if (attrs.getQName(i).equals("id")) {
						animation = new Animation(attrs.getValue(i));
						animation.getFrames().clear();
						animation.getTransitions().clear();
					}
				}
			}
			
			
			if ( qName.equals("documentation")) {
				currentString = new StringBuffer();
			}
			
			if (qName.equals("resources")) {
				currentResources = new Resources();
			}
			
			else if( qName.equals( "asset" ) ) {
				String type = "";
				String path = "";

				for( int i = 0; i < attrs.getLength( ); i++ ) {
					if( attrs.getQName( i ).equals( "type" ) )
						type = attrs.getValue( i );
					if( attrs.getQName( i ).equals( "uri" ) )
						path = attrs.getValue( i );
				}

				currentResources.addAsset( type, path );
			}

			
			if ( qName.equals("frame")) {
				subParser = new FrameSubParser(animation);
				reading = READING_FRAME;
			}
			
			if (qName.equals("transition")) {
				subParser = new TransitionSubParser(animation);
				reading = READING_TRANSITION;
			}
		}
		if (reading != READING_NONE) {
			subParser.startElement(namespaceURI, sName, qName, attrs);
		}
		
	}
	
	@Override
	public void endElement(String namespaceURI, String sName, String qName) {
		if( qName.equals( "documentation" ) ) {
			if( reading == READING_NONE )
				animation.setDocumentation( currentString.toString( ).trim( ) );
		}
		else if( qName.equals( "resources" ) ) {
			animation.addResources( currentResources );
		}

		
		if (reading != READING_NONE) {
			try {
				subParser.endElement(namespaceURI, sName, qName);
			} catch (SAXException e) {
				e.printStackTrace();
			}
			reading = READING_NONE;
		}
		
	}

	@Override
	public void error( SAXParseException exception ) throws SAXParseException {
		// On validation, propagate exception
		exception.printStackTrace( );
		throw exception;
	}
	
	@Override
	public void characters( char[] buf, int offset, int len ) throws SAXException {
		// Append the new characters
		currentString.append( new String( buf, offset, len ) );
	}

	public Animation getAnimation() {
		return animation;
	}
}
