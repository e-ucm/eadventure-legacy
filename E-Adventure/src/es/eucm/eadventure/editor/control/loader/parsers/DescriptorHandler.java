package es.eucm.eadventure.editor.control.loader.parsers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileInputStream;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import es.eucm.eadventure.common.data.adventure.DescriptorData;
import es.eucm.eadventure.editor.control.controllers.AdventureDataControl;

/**
 * This class is the handler to parse the e-Adventure descriptor file.
 * 
 * @author Javier Torrente
 */
public class DescriptorHandler extends DefaultHandler {

	/**
	 * Stores the zip path in which the chapters must be searched.
	 */
	private String zipFile;

	/**
	 * Adventure data being read.
	 */
	private DescriptorData descriptorData;

	/**
	 * String to store the current string in the XML file
	 */
	protected StringBuffer currentString;

	private int titleRead = -1;

	private int descriptionRead = -1;

	/**
	 * Constructor.
	 * 
	 * @param zipFile
	 *            Path to the zip file which helds the descriptor
	 */
	public DescriptorHandler( String zipFile ) {
		this.zipFile = zipFile;
		descriptorData = new DescriptorData( );
	}

	/**
	 * Returns the descriptor data read
	 * 
	 * @return The descriptor data from the XML descriptor
	 */
	public DescriptorData getAdventureData( ) {
		return descriptorData;
	}

	@Override
	public void startElement( String namespaceURI, String sName, String qName, Attributes attrs ) throws SAXException {

		// If reading a title, empty the current string
		if( titleRead == -1 && qName.equals( "title" ) ) {// || (!descriptionRead && qName.equals( "description" ) )) {
			currentString = new StringBuffer( );
			titleRead = 0;
		}
		if( descriptionRead == -1 && qName.equals( "description" ) ) {
			currentString = new StringBuffer( );
			descriptionRead = 0;
		}

		// If reading the GUI tag, store the settings
		else if( qName.equals( "gui" ) ) {
			for( int i = 0; i < attrs.getLength( ); i++ )
				if( attrs.getQName( i ).equals( "type" ) )
					if( attrs.getValue( i ).equals( "traditional" ) )
						descriptorData.setGUIType( AdventureDataControl.GUI_TRADITIONAL );
					else if( attrs.getValue( i ).equals( "contextual" ) )
						descriptorData.setGUIType( AdventureDataControl.GUI_CONTEXTUAL );
		}

		// If reading the mode tag:
		else if( qName.equals( "mode" ) ) {
			for( int i = 0; i < attrs.getLength( ); i++ )
				if( attrs.getQName( i ).equals( "playerTransparent" ) )
					if( attrs.getValue( i ).equals( "yes" ) )
						descriptorData.setPlayerMode( AdventureDataControl.PLAYER_1STPERSON );
					else if( attrs.getValue( i ).equals( "no" ) )
						descriptorData.setPlayerMode( AdventureDataControl.PLAYER_3RDPERSON );
		}

	}

	@Override
	public void endElement( String namespaceURI, String sName, String qName ) throws SAXException {

		// If the title is complete, store it
		if( qName.equals( "title" ) && titleRead == 0 ) {
			// Store it in the adventure data
			descriptorData.setTitle( currentString.toString( ).trim( ) );
			titleRead = 1;
		}

		// If the description is complete, store it
		else if( qName.equals( "description" ) && descriptionRead == 0 ) {
			// Store it in the adventure data
			descriptorData.setDescription( currentString.toString( ).trim( ) );
			descriptionRead = 1;
		}

	}

	@Override
	public void characters( char[] buf, int offset, int len ) throws SAXException {
		// Append the new characters
		currentString.append( new String( buf, offset, len ) );
	}

	@Override
	public void error( SAXParseException exception ) throws SAXParseException {
		// On validation, propagate exception
		//exception.printStackTrace( );
		throw exception;
	}

	/*@Override
	public InputSource resolveEntity( String publicId, String systemId ) throws FileNotFoundException {
		// Take the name of the file SAX is looking for
		int startFilename = systemId.lastIndexOf( "/" ) + 1;
		String filename = systemId.substring( startFilename, systemId.length( ) );

		// Create the input source to return
		InputSource inputSource = null;

		try {
			// If the file is descriptor.dtd, use the one in the editor's folder
			if( filename.toLowerCase( ).equals( "descriptor.dtd" ) )
				inputSource = new InputSource( new FileInputStream( filename ) );

			// If it is any other file, use the super's method
			else
				inputSource = super.resolveEntity( publicId, systemId );
		} catch( IOException e ) {
			//e.printStackTrace( );
		} catch( SAXException e ) {
			//e.printStackTrace( );
		}

		return inputSource;
	}*/
}
