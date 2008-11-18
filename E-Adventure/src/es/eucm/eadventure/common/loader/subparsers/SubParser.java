package es.eucm.eadventure.common.loader.subparsers;

import org.xml.sax.Attributes;

import es.eucm.eadventure.common.data.chapter.Chapter;

/**
 * Abstract class for subparsing elements of the script
 */
public abstract class SubParser {

	/* Attributes */

	/**
	 * String to store the current string in the XML file.
	 */
	protected StringBuffer currentString;

	/**
	 * Chapter in which the data will be stored.
	 */
	protected Chapter chapter;

	/* Methods */

	/**
	 * Constructor.
	 * 
	 * @param chapter
	 *            Chapter data to store the read data
	 */
	public SubParser( Chapter chapter ) {
		this.chapter = chapter;
		currentString = new StringBuffer( );
	}

	/**
	 * Receive notification of the start of an element.
	 * 
	 * @param namespaceURI
	 *            The Namespace URI, or the empty string if the element has no Namespace URI or if Namespace processing
	 *            is not being performed
	 * @param sName
	 *            The local name (without prefix), or the empty string if Namespace processing is not being performed
	 * @param qName
	 *            The qualified name (with prefix), or the empty string if qualified names are not available
	 * @param attrs
	 *            The attributes attached to the element. If there are no attributes, it shall be an empty Attributes
	 *            object
	 */
	public abstract void startElement( String namespaceURI, String sName, String qName, Attributes attrs );

	/**
	 * Receive notification of the end of an element.
	 * 
	 * @param namespaceURI
	 *            The Namespace URI, or the empty string if the element has no Namespace URI or if Namespace processing
	 *            is not being performed
	 * @param sName
	 *            The local name (without prefix), or the empty string if Namespace processing is not being performed
	 * @param qName
	 *            The qualified name (with prefix), or the empty string if qualified names are not available
	 */
	public abstract void endElement( String namespaceURI, String sName, String qName );

	/**
	 * Receive notification of character data inside an element.
	 * 
	 * @param buf
	 *            The characters
	 * @param offset
	 *            The start position in the character array
	 * @param len
	 *            The number of characters to use from the character array
	 */
	public void characters( char[] buf, int offset, int len ) {
		// Append the new characters
		currentString.append( new String( buf, offset, len ) );
	}
}
